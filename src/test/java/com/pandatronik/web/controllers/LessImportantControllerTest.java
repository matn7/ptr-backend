package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.service.*;
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@Transactional
public class LessImportantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    LessImportantService lessImportantService;

    @MockBean
    LessImportant2Service lessImportant2Service;

    @MockBean
    LessImportant3Service lessImportant3Service;
    
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();
        TaskDTO lessImportant = new TaskDTO();
        lessImportant.setId(200L);
        lessImportant.setTitle("Less Important Task Title");
        lessImportant.setBody("Less Important Task Body");
        lessImportant.setMade(MadeEnum.TWENTY_FIVE);
        lessImportant.setStartDate(LocalDate.of(2028, 6, 15));
        lessImportant.setPostedOn(LocalDateTime.now());
        lessImportant.setUserId(user.getId());

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(lessImportantService.findById(anyString(), anyLong())).thenReturn(lessImportant);
        when(lessImportant2Service.findById(anyString(), anyLong())).thenReturn(lessImportant);
        when(lessImportant3Service.findById(anyString(), anyLong())).thenReturn(lessImportant);
    }

    @Test
    public void findById() throws Exception {
        String username = "user_123";
        int lessImportantId = 1;
        long validId = 200L;

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Task Title")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Task Body")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2028)))
                .andExpect(jsonPath("$.startDate[1]", is(6)))
                .andExpect(jsonPath("$.startDate[2]", is(15)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportantService, times(1)).findById(anyString(), anyLong());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "user_123";
        int importantId = 2;
        long invalidId = 200L;

        when(lessImportant2Service.findById(anyString(), anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + importantId + "/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportant2Service).findById(anyString(), anyLong());
    }

    @Test
    public void findByDate() throws Exception {
        String username = "user_123";
        int lessImportantId = 3;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO lessImportant = new TaskDTO();
        lessImportant.setId(200L);
        lessImportant.setTitle("Less Important Task Title - find by date");
        lessImportant.setBody("Less Important Task Body - find by date");
        lessImportant.setMade(MadeEnum.FIFTY);
        lessImportant.setStartDate(LocalDate.of(2027, 7, 13));
        lessImportant.setPostedOn(LocalDateTime.now());
        lessImportant.setUserId(user.getId());

        when(lessImportant3Service.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(lessImportant);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/2027/7/13")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Task Title - find by date")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Task Body - find by date")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2027)))
                .andExpect(jsonPath("$.startDate[1]", is(7)))
                .andExpect(jsonPath("$.startDate[2]", is(13)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportant3Service).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "user_123";
        int lessImportantId = 1;

        when(lessImportantService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/2024/5/17")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportantService, times(1)).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testSave() throws Exception {
        String username = "user_123";
        int lessImportantId = 2;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO lessImportant = new TaskDTO();
        lessImportant.setId(200L);
        lessImportant.setTitle("Less Important Task Title - save");
        lessImportant.setBody("Less Important Task Body - save");
        lessImportant.setMade(MadeEnum.SEVENTY_FIVE);
        lessImportant.setStartDate(LocalDate.of(2024, 10, 15));
        lessImportant.setPostedOn(LocalDateTime.now());
        lessImportant.setUserId(user.getId());

        when(lessImportant2Service.save(anyString(), any())).thenReturn(lessImportant);

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessImportant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Task Title - save")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Task Body - save")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(10)))
                .andExpect(jsonPath("$.startDate[2]", is(15)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportant2Service, times(1)).save(anyString(), any());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "user_123";
        int lessImportantId = 3;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        TaskDTO lessImportant = new TaskDTO();
        lessImportant.setId(200L);
        lessImportant.setTitle("Less Important Task Title - update");
        lessImportant.setBody("Less Important Task Body - update");
        lessImportant.setMade(MadeEnum.HUNDRED);
        lessImportant.setStartDate(LocalDate.of(2026, 11, 30));
        lessImportant.setPostedOn(LocalDateTime.now());
        lessImportant.setUserId(user.getId());

        when(lessImportant3Service.save(anyString(), any())).thenReturn(lessImportant);

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessImportant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Task Title - update")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Task Body - update")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.HUNDRED.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2026)))
                .andExpect(jsonPath("$.startDate[1]", is(11)))
                .andExpect(jsonPath("$.startDate[2]", is(30)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(lessImportant3Service, times(1)).save(anyString(), any());
    }


}
