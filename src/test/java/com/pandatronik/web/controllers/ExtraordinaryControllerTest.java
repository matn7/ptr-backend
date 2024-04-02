package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
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
@Transactional
public class ExtraordinaryControllerTest {

    // todo: Spy object test

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    ExtraordinaryService extraordinaryService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();
        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setId(200L);
        extraordinary.setTitle("Extraordinary Title");
        extraordinary.setBody("Extraordinary Body");
        extraordinary.setStartDate(LocalDate.of(2024, 12, 24));
        extraordinary.setPostedOn(LocalDateTime.now());
        extraordinary.setUserId(user.getId());

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(extraordinaryService.findById(anyString(), anyLong())).thenReturn(extraordinary);
    }

    @Test
    public void findById() throws Exception {
        String username = "matek_1991";
        long validId = 200L;

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Extraordinary Title")))
                .andExpect(jsonPath("$.body", equalTo("Extraordinary Body")))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(12)))
                .andExpect(jsonPath("$.startDate[2]", is(24)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService, times(1)).findById(anyString(), anyLong());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "matek_1991";
        long invalidId = 201L;

        when(extraordinaryService.findById(anyString(), anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService).findById(anyString(), anyLong());
    }

    @Test
    public void findByDate() throws Exception {
        String username = "matek_1991";
        int year = 1945;
        int month = 2;
        int day = 19;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setId(200L);
        extraordinary.setTitle("Battle of Iwo Jima");
        extraordinary.setBody("D-Day");
        extraordinary.setStartDate(LocalDate.of(year, month, day));
        extraordinary.setPostedOn(LocalDateTime.now());
        extraordinary.setUserId(user.getId());

        when(extraordinaryService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(extraordinary);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Iwo Jima")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "matek_1991";
        int year = 1945;
        int month = 2;
        int day = 19;

        when(extraordinaryService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService, times(1)).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testSave() throws Exception {
        String username = "matek_1991";
        int year = 1945;
        int month = 4;
        int day = 1;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setId(200L);
        extraordinary.setTitle("Battle of Okinawa");
        extraordinary.setBody("D-Day");
        extraordinary.setStartDate(LocalDate.of(year, month, day));
        extraordinary.setPostedOn(LocalDateTime.now());
        extraordinary.setUserId(user.getId());

        when(extraordinaryService.save(anyString(), any())).thenReturn(extraordinary);

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/extraordinary")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraordinary)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Okinawa")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService, times(1)).save(anyString(), any());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "matek_1991";
        int year = 1944;
        int month = 6;
        int day = 16;

        UserEntity user = UserEntity.builder().id(20L).email("panda@pandatronik.com").build();

        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setId(200L);
        extraordinary.setTitle("Battle of Saipan");
        extraordinary.setBody("D-Day");
        extraordinary.setStartDate(LocalDate.of(year, month, day));
        extraordinary.setPostedOn(LocalDateTime.now());
        extraordinary.setUserId(user.getId());

        when(extraordinaryService.save(anyString(), any())).thenReturn(extraordinary);

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/extraordinary")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraordinary)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Saipan")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(extraordinaryService, times(1)).save(anyString(), any());
    }


}
