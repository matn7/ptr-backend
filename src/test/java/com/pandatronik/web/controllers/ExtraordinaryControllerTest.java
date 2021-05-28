package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryListDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

@WebMvcTest(controllers = ExtraordinaryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExtraordinaryControllerTest extends SecurityConfigBeans {

    @MockBean
    private ExtraordinaryService extraordinaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAll() throws Exception {
        UserEntity user = UserEntity.builder().build();
        List<ExtraordinaryDTO> list = getExtraordinaryDTOList();
        ExtraordinaryListDTO extraordinaryListDTO = new ExtraordinaryListDTO();
        extraordinaryListDTO.setExtraordinaryList(list);

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.findAll(user)).thenReturn(list);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/extraordinary/all")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.extraordinaryList[0].id", equalTo(1)))
                .andExpect(jsonPath("$.extraordinaryList[0].title", equalTo("Extra Day Title")))
                .andExpect(jsonPath("$.extraordinaryList[0].body", equalTo("Extra Day Body")))
                .andExpect(jsonPath("$.extraordinaryList[1].id", equalTo(2)))
                .andExpect(jsonPath("$.extraordinaryList[1].title", equalTo("Extra Day Title 2")))
                .andExpect(jsonPath("$.extraordinaryList[1].body", equalTo("Extra Day Body 2")));
    }

    @Test
    public void findById() throws Exception {
        UserEntity user = UserEntity.builder().build();
        ExtraordinaryDTO extraordinary = getExtraordinary();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.findById(user, 1L)).thenReturn(extraordinary);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/extraordinary/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Extra Day Title")))
                .andExpect(jsonPath("$.body", equalTo("Extra Day Body")));
    }


    @Test
    public void findByIdNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.findById(user, 1L)).thenThrow(ResourceNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/extraordinary/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void findByDate() throws Exception {
        UserEntity user = UserEntity.builder().build();
        ExtraordinaryDTO extraordinary = getExtraordinary();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.findByDate(user, 25, 5, 2025)).thenReturn(extraordinary);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/extraordinary/2025/05/25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Extra Day Title")))
                .andExpect(jsonPath("$.body", equalTo("Extra Day Body")))
                .andExpect(jsonPath("$.startDate[0]", equalTo(2025)))
                .andExpect(jsonPath("$.startDate[1]", equalTo(5)))
                .andExpect(jsonPath("$.startDate[2]", equalTo(25)));
    }

    @Test
    public void findByDateNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.findByDate(user, 25, 5, 2025)).thenThrow(ResourceNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .get(AppConstants.BASE_URL + "/someuser/extraordinary/2025/05/25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void save() throws Exception {

        UserEntity user = UserEntity.builder().build();
        ExtraordinaryDTO extraordinary = getExtraordinary();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.save(any())).thenReturn(extraordinary);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(extraordinary);

        RequestBuilder request = post(AppConstants.BASE_URL + "/someuser/extraordinary")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Extra Day Title")))
                .andExpect(jsonPath("$.body", equalTo("Extra Day Body")));
    }

    @Test
    public void update() throws Exception {
        UserEntity user = UserEntity.builder().build();
        ExtraordinaryDTO extraordinary = getExtraordinary();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(extraordinaryService.update(anyLong(), any())).thenReturn(extraordinary);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final String json = objectMapper.writeValueAsString(extraordinary);

        RequestBuilder request = put(AppConstants.BASE_URL + "/someuser/extraordinary/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Extra Day Title")))
                .andExpect(jsonPath("$.body", equalTo("Extra Day Body")));
    }

    @Test
    public void deleteExtraordinaryDay() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);

        mockMvc.perform(delete(AppConstants.BASE_URL + "/someuser/extraordinary/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private List<ExtraordinaryDTO> getExtraordinaryDTOList() {
        List<ExtraordinaryDTO> extraordinaryDTOList = new ArrayList<>();
        ExtraordinaryDTO extra1 = new ExtraordinaryDTO();
        extra1.setId(1L);
        extra1.setTitle("Extra Day Title");
        extra1.setBody("Extra Day Body");
        extra1.setStartDate(LocalDate.of(2025,5,25));
        extra1.setPostedOn(LocalDateTime.now());

        ExtraordinaryDTO extra2 = new ExtraordinaryDTO();
        extra2.setId(2L);
        extra2.setTitle("Extra Day Title 2");
        extra2.setBody("Extra Day Body 2");
        extra2.setStartDate(LocalDate.of(2025,5,25));
        extra2.setPostedOn(LocalDateTime.now());

        extraordinaryDTOList.add(extra1);
        extraordinaryDTOList.add(extra2);

        return extraordinaryDTOList;
    }

    private ExtraordinaryDTO getExtraordinary() {
        ExtraordinaryDTO extra1 = new ExtraordinaryDTO();
        extra1.setId(1L);
        extra1.setTitle("Extra Day Title");
        extra1.setBody("Extra Day Body");
        extra1.setStartDate(LocalDate.of(2025,5,25));
        extra1.setPostedOn(LocalDateTime.now());

        return extra1;
    }
}

