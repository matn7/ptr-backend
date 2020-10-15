package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.CustomResponseEntityExceptionHandler;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
//@TestPropertySource(locations="classpath:application.properties")
@SpringBootTest
public class DaysResourceTestDTO {

    @Value("${api.version}")
    private String api;

    WebTestClient webTestClient;

    @Mock
    DaysService daysService;

    @Mock
    UserService userService;

    @InjectMocks
    DaysResource daysResource;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(daysResource)
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();
    }
//
//    @Test
//    public void findById() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//
//        DaysEntityDTO day = DaysEntityDTO.builder().id(1L).body("Some Day").rateDay(MadeEnum.HUNDRED).build();
//
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.findById(user, 1L)).thenReturn(day);
//
//        mockMvc.perform(get("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
//                .andExpect(jsonPath("$.body", equalTo("Some Day")))
//                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).findById(user, 1L);
//    }
//
//    @Test
//    public void findByIdNotFound() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.findById(user, 1L)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(get("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.body", equalTo("Resource Not Fount")));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).findById(user, 1L);
//    }
//
//    @Test
//    public void findByDate() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        DaysEntityDTO day = DaysEntityDTO.builder().id(1L).body("Some Day")
//                .startDate(LocalDate.of(1991, 10, 20)).rateDay(MadeEnum.HUNDRED).build();
//
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.findByDate(user, 20, 10, 1991)).thenReturn(day);
//
////        int[] res = {1991, 10, 20};
////        Expected :[<1991>, <10>, <20>]
////        Actual   :<[1991,10,20]>
//
//        mockMvc.perform(get("/api/v1/users/someuser/days/1991/10/20")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
//                .andExpect(jsonPath("$.body", equalTo("Some Day")))
////                .andExpect(jsonPath("$.startDate", equalTo(res)))
//                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).findByDate(user, 20, 10, 1991);
//    }
//
//    @Test
//    public void findByDateNotFound() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.findByDate(user, 20, 10, 1991)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(get("/api/v1/users/someuser/days/1991/10/20")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.body", equalTo("Resource Not Fount")));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).findByDate(user, 20, 10, 1991);
//    }
//
//    @Test
//    public void testSave() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        DaysEntityDTO day = DaysEntityDTO.builder().id(1L).body("Some Day")
//                .postedOn(LocalDateTime.now())
//                .startDate(LocalDate.of(1991, 10, 20)).rateDay(MadeEnum.HUNDRED).build();
//
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.save(any())).thenReturn(day);
//
//        String jsonRequest = new JSONObject()
//                .put("body", "Some Day")
//                .put("rateDay", "100")
//                .put("postedOn", "2020-10-12T22:24:06")
//                .put("startDate", "2020-10-12")
//                .toString();
//
//        mockMvc.perform(post("/api/v1/users/someuser/days")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", equalTo(1)))
//                .andExpect(jsonPath("$.body", equalTo("Some Day")))
//                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).save(any());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        DaysEntityDTO day = DaysEntityDTO.builder().id(1L).body("Some Day")
//                .postedOn(LocalDateTime.now())
//                .startDate(LocalDate.of(1991, 10, 20)).rateDay(MadeEnum.HUNDRED).build();
//
//        when(userService.findByUserName(anyString())).thenReturn(user);
//        when(daysService.save(any())).thenReturn(day);
//
//        String jsonRequest = new JSONObject()
//                .put("id", "1")
//                .put("body", "Some Day")
//                .put("rateDay", "100")
//                .put("postedOn", "2020-10-12T22:24:06")
//                .put("startDate", "2020-10-12")
//                .toString();
//
//        mockMvc.perform(put("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id", equalTo(1)))
//                .andExpect(jsonPath("$.body", equalTo("Some Day")))
//                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));
//
//        verify(userService).findByUserName(anyString());
//        verify(daysService).update(anyLong(), any());
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        when(userService.findByUserName(anyString())).thenReturn(user);
//
//        mockMvc.perform(delete("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(daysService).delete(user, 1L);
//    }
//
//    @Test
//    public void testDeleteNotFound() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        when(userService.findByUserName(anyString())).thenReturn(user);
////        when(daysService.delete(user, 1L)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(delete("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(daysService).delete(user, 1L);
//    }
//
//    @Test
//    public void testUserNotFOund() throws Exception {
//        UserEntity user = UserEntity.builder().build();
//        when(userService.findByUserName(anyString())).thenThrow(UsernameNotFoundException.class);
////        when(daysService.delete(user, 1L)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(delete("/api/v1/users/someuser/days/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        verify(daysService).delete(user, 1L);
//    }


}