package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.IndexDTO;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.ImportantIndexService;
import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@Transactional
public class ImportantIndexControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImportantIndexService importantIndexService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Value("${sql.script.disable.fk.check}")
    private String sqlDisableFkCheck;

    @Value("${sql.script.create.plan}")
    private String sqlCreatePlan;

    @Value("${sql.script.create.userEntity}")
    private String sqlCreateUserEntity;

    @Value("${sql.script.create.extraordinary.index}")
    private String sqlCreateExtraordinary;

    @Value("${sql.script.create.important1.index}")
    private String sqlCreateImportant;

    @Value("${sql.script.create.important2.index}")
    private String sqlCreateImportant2;

    @Value("${sql.script.create.important2.index.2}")
    private String sqlCreateImportant2Second;

    @Value("${sql.script.create.important3.index}")
    private String sqlCreateImportant3;

    @Value("${sql.script.create.days.index}")
    private String sqlCreateDays;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @BeforeEach
    public void setUpDays() {
        UserEntity user = UserEntity.builder().email("panda@pandatronik.com").build();

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));

        jdbc.execute(sqlDisableFkCheck);
        jdbc.execute(sqlCreatePlan);
        jdbc.execute(sqlCreateUserEntity);

        jdbc.execute(sqlCreateExtraordinary);
        jdbc.execute(sqlCreateImportant);
        jdbc.execute(sqlCreateImportant2);
        jdbc.execute(sqlCreateImportant2Second);
        jdbc.execute(sqlCreateImportant3);
        jdbc.execute(sqlCreateDays);
    }

    @Test
    public void findByDate() throws Exception {
        String username = "matek_1991";
        int year = 2024;
        int month = 3;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        IndexDTO importantIndexDTO = importantIndexService.getData(username, year, month);

        assertNotNull(importantIndexDTO.getDaysDTO());
        assertNotNull(importantIndexDTO.getTaskDTO());
        assertNotNull(importantIndexDTO.getTask2DTO());
        assertNotNull(importantIndexDTO.getTask3DTO());
        assertNotNull(importantIndexDTO.getExtraordinaryDTO());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + year + "/" + month)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.extraordinaryDTO", hasSize(1)))
                .andExpect(jsonPath("$.extraordinaryDTO[0].id", is(300)))
                .andExpect(jsonPath("$.extraordinaryDTO[0].title", is("Entry for Index test")))
                .andExpect(jsonPath("$.extraordinaryDTO[0].body", is("Extraordinary Index Test")))
                .andExpect(jsonPath("$.extraordinaryDTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.extraordinaryDTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.extraordinaryDTO[0].startDate[2]", is(22)))
                .andExpect(jsonPath("$.extraordinaryDTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.taskDTO", hasSize(1)))
                .andExpect(jsonPath("$.taskDTO[0].id", is(300)))
                .andExpect(jsonPath("$.taskDTO[0].title", is("Important Entry Title Index 1")))
                .andExpect(jsonPath("$.taskDTO[0].body", is("Index Test Body 1")))
                .andExpect(jsonPath("$.taskDTO[0].made", is(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.taskDTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.taskDTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.taskDTO[0].startDate[2]", is(22)))
                .andExpect(jsonPath("$.taskDTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.task2DTO", hasSize(2)))
                .andExpect(jsonPath("$.task2DTO[0].id", is(300)))
                .andExpect(jsonPath("$.task2DTO[0].title", is("Important Entry Title Index 2")))
                .andExpect(jsonPath("$.task2DTO[0].body", is("Index Test Body 2")))
                .andExpect(jsonPath("$.task2DTO[0].made", is(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.task2DTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.task2DTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.task2DTO[0].startDate[2]", is(18)))
                .andExpect(jsonPath("$.task2DTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.task2DTO[1].id", is(301)))
                .andExpect(jsonPath("$.task2DTO[1].title", is("Important Entry Title Index 2 second")))
                .andExpect(jsonPath("$.task2DTO[1].body", is("Index Test Body 2 second")))
                .andExpect(jsonPath("$.task2DTO[1].made", is(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.task2DTO[1].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.task2DTO[1].startDate[1]", is(3)))
                .andExpect(jsonPath("$.task2DTO[1].startDate[2]", is(19)))
                .andExpect(jsonPath("$.task2DTO[1].postedOn", not(empty())))
                .andExpect(jsonPath("$.task3DTO", hasSize(1)))
                .andExpect(jsonPath("$.task3DTO[0].id", is(300)))
                .andExpect(jsonPath("$.task3DTO[0].title", is("Important Entry Title Index 3")))
                .andExpect(jsonPath("$.task3DTO[0].body", is("Index Test Body 3")))
                .andExpect(jsonPath("$.task3DTO[0].made", is(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.task3DTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.task3DTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.task3DTO[0].startDate[2]", is(30)))
                .andExpect(jsonPath("$.task3DTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.daysDTO", hasSize(1)))
                .andExpect(jsonPath("$.daysDTO[0].id", is(300)))
                .andExpect(jsonPath("$.daysDTO[0].body", is("Days Entry Index")))
                .andExpect(jsonPath("$.daysDTO[0].rateDay", is(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.daysDTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.daysDTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.daysDTO[0].startDate[2]", is(22)))
                .andExpect(jsonPath("$.daysDTO[0].postedOn", not(empty())))
        ;
    }

}
