package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class ImportantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    ImportantService importantService;

    @MockBean
    Important2Service important2Service;

    @MockBean
    Important3Service important3Service;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();
        TaskDTO important = new TaskDTO();
        important.setId(200L);
        important.setTitle("Important Task Title");
        important.setBody("Important Task Body");
        important.setMade(MadeEnum.HUNDRED);
        important.setStartDate(LocalDate.of(2024, 4, 28));
        important.setPostedOn(LocalDateTime.now());
        important.setUserId(user.getId());

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(importantService.findById(anyString(), anyLong())).thenReturn(important);
        when(important2Service.findById(anyString(), anyLong())).thenReturn(important);
        when(important3Service.findById(anyString(), anyLong())).thenReturn(important);
    }

    @Test
    public void findById() throws Exception {
        String username = "user_123";
        int importantId = 1;
        long validId = 200L;

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Important Task Title")))
                .andExpect(jsonPath("$.body", equalTo("Important Task Body")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.HUNDRED.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(4)))
                .andExpect(jsonPath("$.startDate[2]", is(28)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(importantService, times(1)).findById(anyString(), anyLong());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "user_123";
        int importantId = 2;
        long invalidId = 200L;

        when(important2Service.findById(anyString(), anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(important2Service).findById(anyString(), anyLong());
    }

    @Test
    public void findByDate() throws Exception {
        String username = "user_123";
        int importantId = 3;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO important = new TaskDTO();
        important.setId(200L);
        important.setTitle("Important Task Title - find by date");
        important.setBody("Important Task Body - find by date");
        important.setMade(MadeEnum.SEVENTY_FIVE);
        important.setStartDate(LocalDate.of(2024, 5, 17));
        important.setPostedOn(LocalDateTime.now());
        important.setUserId(user.getId());

        when(important3Service.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(important);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/2024/5/17")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Important Task Title - find by date")))
                .andExpect(jsonPath("$.body", equalTo("Important Task Body - find by date")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(5)))
                .andExpect(jsonPath("$.startDate[2]", is(17)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(important3Service).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "user_123";
        int importantId = 1;

        when(importantService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/2024/5/17")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(importantService, times(1)).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testSave() throws Exception {
        String username = "user_123";
        int importantId = 2;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO important = new TaskDTO();
        important.setId(200L);
        important.setTitle("Important Task Title - save");
        important.setBody("Important Task Body - save");
        important.setMade(MadeEnum.TWENTY_FIVE);
        important.setStartDate(LocalDate.of(2025, 6, 22));
        important.setPostedOn(LocalDateTime.now());
        important.setUserId(user.getId());

        when(important2Service.save(anyString(), any())).thenReturn(important);

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/important/" + importantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(important)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Important Task Title - save")))
                .andExpect(jsonPath("$.body", equalTo("Important Task Body - save")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2025)))
                .andExpect(jsonPath("$.startDate[1]", is(6)))
                .andExpect(jsonPath("$.startDate[2]", is(22)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(important2Service, times(1)).save(anyString(), any());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "user_123";
        int importantId = 3;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO important = new TaskDTO();
        important.setId(200L);
        important.setTitle("Important Task Title - update");
        important.setBody("Important Task Body - update");
        important.setMade(MadeEnum.FIFTY);
        important.setStartDate(LocalDate.of(2026, 11, 30));
        important.setPostedOn(LocalDateTime.now());
        important.setUserId(user.getId());

        when(important3Service.save(anyString(), any())).thenReturn(important);

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/important/" + importantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(important)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Important Task Title - update")))
                .andExpect(jsonPath("$.body", equalTo("Important Task Body - update")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2026)))
                .andExpect(jsonPath("$.startDate[1]", is(11)))
                .andExpect(jsonPath("$.startDate[2]", is(30)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(important3Service, times(1)).save(anyString(), any());
    }


}
