package com.pandatronik.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.MadeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ImportantController.class,
excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ImportantControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    ImportantService importantService;

    ImportantDTO importantDTO;

    @BeforeEach
    public void setup() {
        importantDTO = new ImportantDTO();
        importantDTO.setTitle("Title");
        importantDTO.setBody("Test");
        importantDTO.setStartDate(LocalDate.now());
        importantDTO.setPostedOn(LocalDateTime.now());
        importantDTO.setMade(MadeEnum.HUNDRED);
    }

    @Test
    public void createEntry() throws Exception {
        // Arrange
        when(importantService.save(any(), any())).thenReturn(importantDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/test_user/important/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(importantDTO));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(responseBodyAsString);

    }


}
