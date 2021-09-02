package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.enums.RateDayEnum;
import com.pandatronik.utils.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DaysController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DaysControllerTest extends SecurityConfigBeans {

    @MockBean
    private DaysService daysService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findById(user.getId(), 1L)).thenReturn(day);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(String.valueOf(RateDayEnum.VERY_GOOD.getRateDay()))));
    }

    @Test
    public void findByIdNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findById(user.getId(), 1L)).thenThrow(ResourceNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void findByDate() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findByDate(user.getId(), 25, 5, 2025)).thenReturn(day);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/days/2025/05/25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.startDate[0]", equalTo(2025)))
                .andExpect(jsonPath("$.startDate[1]", equalTo(5)))
                .andExpect(jsonPath("$.startDate[2]", equalTo(25)))
                .andExpect(jsonPath("$.rateDay", equalTo(String.valueOf(RateDayEnum.VERY_GOOD.getRateDay()))));
    }

    @Test
    public void findByDateNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findByDate(user.getId(), 25, 5, 2025)).thenThrow(ResourceNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/days/2025/05/25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void save() throws Exception {

        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.save(any())).thenReturn(day);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(day);

        RequestBuilder request = post(AppConstants.BASE_URL + "/someuser/days")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(String.valueOf(RateDayEnum.VERY_GOOD.getRateDay()))));
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.update(anyLong(), any())).thenReturn(day);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(day);

        RequestBuilder request = put(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(String.valueOf(RateDayEnum.VERY_GOOD.getRateDay()))));
    }

    @Test
    public void deleteDay() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);

        mockMvc.perform(delete(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private DaysDTO getDaysDTO() {
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(2025, 5, 25));
        day.setPostedOn(LocalDateTime.now());
        day.setRateDay(RateDayEnum.VERY_GOOD);
        return day;
    }

}