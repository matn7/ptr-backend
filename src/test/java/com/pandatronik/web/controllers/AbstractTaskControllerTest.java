package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.utils.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractTaskControllerTest<T> extends SecurityConfigBeans {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById() throws Exception {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().findById(user, 1L)).thenReturn(task);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo(title())))
                .andExpect(jsonPath("$.body", equalTo(body())))
                .andExpect(jsonPath("$.startDate[0]", equalTo(2025)))
                .andExpect(jsonPath("$.startDate[1]", equalTo(5)))
                .andExpect(jsonPath("$.startDate[2]", equalTo(25)))
                .andExpect(jsonPath("$.made", equalTo(String.valueOf(MadeEnum.HUNDRED.getValue()))));
    }

    @Test
    public void findByIdNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().findById(user, 1L)).thenThrow(ResourceNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void findByDate() throws Exception {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().findByDate(user, 2025, 5, 25)).thenReturn(task);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/2025/5/25")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo(title())))
                .andExpect(jsonPath("$.body", equalTo(body())))
                .andExpect(jsonPath("$.startDate[0]", equalTo(2025)))
                .andExpect(jsonPath("$.startDate[1]", equalTo(5)))
                .andExpect(jsonPath("$.startDate[2]", equalTo(25)))
                .andExpect(jsonPath("$.made", equalTo(String.valueOf(MadeEnum.HUNDRED.getValue()))));
    }

    @Test
    public void findByDateNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().findByDate(user, 2025, 5, 25)).thenThrow(ResourceNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/2025/5/25")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void save() throws Exception {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().save(any())).thenReturn(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(task);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo(title())))
                .andExpect(jsonPath("$.body", equalTo(body())))
                .andExpect(jsonPath("$.made", equalTo(String.valueOf(MadeEnum.HUNDRED.getValue()))));
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().update(anyLong(), any())).thenReturn(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(task);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo(title())))
                .andExpect(jsonPath("$.body", equalTo(body())))
                .andExpect(jsonPath("$.made", equalTo(String.valueOf(MadeEnum.HUNDRED.getValue()))));
    }

    @Test
    public void deleteTask() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(AppConstants.BASE_URL + "/someuser/" + task() + "/" + testCase() + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    protected abstract T getTask();

    protected abstract int testCase();

    protected abstract CrudService<T, Long> getService();

    protected abstract String task();

    protected abstract String body();

    protected abstract String title();

}
