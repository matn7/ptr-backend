package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.exceptions.CustomResponseEntityExceptionHandler;
import com.pandatronik.utils.AppConstants;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
//@TestPropertySource(locations="classpath:application.properties")
@SpringBootTest
public class DaysControllerTest {

    @Value("${api.version}")
    private String api;

    WebTestClient webTestClient;

    @Mock
    DaysService daysService;

    @Mock
    UserService userService;

    @InjectMocks
    DaysController daysController;

    MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(daysController)
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void findById() throws Exception {
        UserEntity user = UserEntity.builder().build();

        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setRateDay(MadeEnum.HUNDRED);

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findById(any(), 1L)).thenReturn(day);

        mockMvc.perform(get(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(userService).findByUserName(anyString());
        verify(daysService).findById(any(), 1L);
    }

    @Test
    public void findByIdNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findById(any(), 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Fount")));

        verify(userService).findByUserName(anyString());
        verify(daysService).findById(any(), 1L);
    }

    @Test
    public void findByDate() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.HUNDRED);

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findByDate(any(), 20, 10, 1991)).thenReturn(day);

//        int[] res = {1991, 10, 20};
//        Expected :[<1991>, <10>, <20>]
//        Actual   :<[1991,10,20]>

        mockMvc.perform(get(AppConstants.BASE_URL + "/someuser/days/1991/10/20")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
//                .andExpect(jsonPath("$.startDate", equalTo(res)))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(userService).findByUserName(anyString());
        verify(daysService).findByDate(any(), 20, 10, 1991);
    }

    @Test
    public void findByDateNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.findByDate(any(), 20, 10, 1991)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/someuser/days/1991/10/20")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Fount")));

        verify(userService).findByUserName(anyString());
        verify(daysService).findByDate(any(), 20, 10, 1991);
    }

    @Test
    public void testSave() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.HUNDRED);

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.save(any(), any())).thenReturn(day);

        String jsonRequest = new JSONObject()
                .put("body", "Some Day")
                .put("rateDay", "100")
                .put("postedOn", "2020-10-12T22:24:06")
                .put("startDate", "2020-10-12")
                .toString();

        mockMvc.perform(post(AppConstants.BASE_URL + "/someuser/days")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(userService).findByUserName(anyString());
        verify(daysService).save(any(), any());
    }

    @Test
    public void testUpdate() throws Exception {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.HUNDRED);

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(daysService.save(any(), any())).thenReturn(day);

        String jsonRequest = new JSONObject()
                .put("id", "1")
                .put("body", "Some Day")
                .put("rateDay", "100")
                .put("postedOn", "2020-10-12T22:24:06")
                .put("startDate", "2020-10-12")
                .toString();

        mockMvc.perform(put(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(userService).findByUserName(anyString());
//        verify(daysService).update(1L, day);
    }

    @Test
    public void testDelete() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);

        mockMvc.perform(delete(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(daysService).delete(any(), 1L);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        UserEntity user = UserEntity.builder().build();
        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.delete(user, 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete(AppConstants.BASE_URL + "/someuser/days/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(daysService).delete(any(), 1L);
    }

//    @Test
//    public void testUserNotFound() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        when(userService.findByUserName(anyString())).thenThrow(UsernameNotFoundException.class);
////        when(daysService.delete(user, 1L)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(delete(AppConstants.BASE_URL + "/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        verify(daysService).delete(user, 1L);
//    }
//

}