package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.IndexDTO;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.LessImportantIndexService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@Transactional
public class LessImportantIndexControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LessImportantIndexService lessImportantIndexService;

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

    @Value("${sql.script.create.less.important1.index}")
    private String sqlCreateLessImportant;

    @Value("${sql.script.create.less.important2.index}")
    private String sqlCreateLessImportant2;

    @Value("${sql.script.create.less.important2.index.2}")
    private String sqlCreateLessImportant2Second;

    @Value("${sql.script.create.less.important3.index}")
    private String sqlCreateLessImportant3;

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
        jdbc.execute(sqlCreateLessImportant);
        jdbc.execute(sqlCreateLessImportant2);
        jdbc.execute(sqlCreateLessImportant2Second);
        jdbc.execute(sqlCreateLessImportant3);
        jdbc.execute(sqlCreateDays);
    }

    @Test
    public void findByDate() throws Exception {
        String username = "user_123";
        int year = 2024;
        int month = 3;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        IndexDTO lessImportantIndexDTO = lessImportantIndexService.getData(username, year, month);

        assertNotNull(lessImportantIndexDTO.getDaysDTO());
        assertNotNull(lessImportantIndexDTO.getTaskDTO());
        assertNotNull(lessImportantIndexDTO.getTask2DTO());
        assertNotNull(lessImportantIndexDTO.getTask3DTO());
        assertNotNull(lessImportantIndexDTO.getExtraordinaryDTO());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + year + "/" + month)
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
                .andExpect(jsonPath("$.taskDTO[0].title", is("Less Important Entry Title Index 1")))
                .andExpect(jsonPath("$.taskDTO[0].body", is("Less Index Test Body 1")))
                .andExpect(jsonPath("$.taskDTO[0].made", is(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.taskDTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.taskDTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.taskDTO[0].startDate[2]", is(22)))
                .andExpect(jsonPath("$.taskDTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.task2DTO", hasSize(2)))
                .andExpect(jsonPath("$.task2DTO[0].id", is(300)))
                .andExpect(jsonPath("$.task2DTO[0].title", is("Less Important Entry Title Index 2")))
                .andExpect(jsonPath("$.task2DTO[0].body", is("Less Index Test Body 2")))
                .andExpect(jsonPath("$.task2DTO[0].made", is(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.task2DTO[0].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.task2DTO[0].startDate[1]", is(3)))
                .andExpect(jsonPath("$.task2DTO[0].startDate[2]", is(18)))
                .andExpect(jsonPath("$.task2DTO[0].postedOn", not(empty())))
                .andExpect(jsonPath("$.task2DTO[1].id", is(301)))
                .andExpect(jsonPath("$.task2DTO[1].title", is("Less Entry Title Index 2 second")))
                .andExpect(jsonPath("$.task2DTO[1].body", is("Less Index Test Body 2 second")))
                .andExpect(jsonPath("$.task2DTO[1].made", is(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.task2DTO[1].startDate[0]", is(2024)))
                .andExpect(jsonPath("$.task2DTO[1].startDate[1]", is(3)))
                .andExpect(jsonPath("$.task2DTO[1].startDate[2]", is(19)))
                .andExpect(jsonPath("$.task2DTO[1].postedOn", not(empty())))
                .andExpect(jsonPath("$.task3DTO", hasSize(1)))
                .andExpect(jsonPath("$.task3DTO[0].id", is(300)))
                .andExpect(jsonPath("$.task3DTO[0].title", is("Less Important Entry Title Index 3")))
                .andExpect(jsonPath("$.task3DTO[0].body", is("Less Index Test Body 3")))
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
