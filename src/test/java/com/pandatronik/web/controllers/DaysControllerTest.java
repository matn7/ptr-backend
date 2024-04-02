package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.security.JwtAuthenticationFilter;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.utils.AppConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@Transactional
public class DaysControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${api.version}")
    private String api;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DaysRepository daysRepository;

    @MockBean
    DaysService daysService;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    @InjectMocks
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.addParameter("name", "panda");
        request.addHeader("Authorization", "Bearer ABCD");
    }

    @BeforeEach
    public void setUp() {
        UserEntity user = UserEntity.builder().email("panda@pandatronik.com").build();
        DaysDTO day = new DaysDTO();
        day.setId(200L);
        day.setBody("Some Day");
        day.setRateDay(MadeEnum.HUNDRED);

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(daysService.findById(anyString(), anyLong())).thenReturn(day);
    }

    @Test
    public void findById() throws Exception {
        String username = "matek_1991";
        long validId = 200L;

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + validId)
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService, times(1)).findById(anyString(), anyLong());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "matek_1991";
        long invalidId = 200L;

        when(daysService.findById(anyString(), anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + invalidId)
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService).findById(anyString(), anyLong());
    }

    @Test
    public void findByDate() throws Exception {
        String username = "matek_1991";
        long validId = 200L;

        DaysDTO day = new DaysDTO();
        day.setId(validId);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.HUNDRED);

        when(daysService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(day);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/1991/10/20")
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.startDate[0]", is(1991)))
                .andExpect(jsonPath("$.startDate[1]", is(10)))
                .andExpect(jsonPath("$.startDate[2]", is(20)))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "matek_1991";
        when(daysService.findByDate(anyString(), anyInt(), anyInt(), anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/1991/10/20")
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService, times(1)).findByDate(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testSave() throws Exception {
        String username = "matek_1991";

        DaysDTO day = new DaysDTO();
        day.setId(200L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.SEVENTY_FIVE);
        day.setPostedOn(LocalDateTime.now());

        when(daysService.save(anyString(), any())).thenReturn(day);

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/days")
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(day)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(1991)))
                .andExpect(jsonPath("$.startDate[1]", is(10)))
                .andExpect(jsonPath("$.startDate[2]", is(20)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService, times(1)).save(anyString(), any());
    }

    // test save (error)
    @Test
    public void testUpdate() throws Exception {
        String username = "matek_1991";

        DaysDTO day = new DaysDTO();
        day.setId(200L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(1991, 10, 20));
        day.setRateDay(MadeEnum.HUNDRED);
        day.setPostedOn(LocalDateTime.now());

        when(daysService.save(anyString(), any())).thenReturn(day);

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/days")
                .header("Authorization", "Bearer ABC")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(day)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.body", equalTo("Some Day")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.HUNDRED.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(1991)))
                .andExpect(jsonPath("$.startDate[1]", is(10)))
                .andExpect(jsonPath("$.startDate[2]", is(20)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(customUserDetailsService, times(1)).loadUserById(anyLong());
        verify(daysService, times(1)).save(anyString(), any());
    }

}