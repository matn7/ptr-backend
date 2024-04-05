package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapperImpl;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.DaysService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test-mysql.properties")
@SpringBootTest
@Transactional
public class DaysControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    DaysRepository daysRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DaysService daysService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Value("${sql.script.create.days}")
    private String sqlCreateDays;

    @Value("${sql.script.delete.days}")
    private String sqlDeleteDays;

    @Value("${sql.script.create.userEntity}")
    private String sqlCreateUserEntity;

    @Value("${sql.script.create.userEntity}")
    private String sqlDeleteUserEntity;

    @Value("${sql.script.create.plan}")
    private String sqlCreatePlan;

    @Value("${sql.script.disable.fk.check}")
    private String sqlDisableFkCheck;

    @Value("${sql.script.reset.auto.increment}")
    private String sqlResetAutoIncrement;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUpDays() {
        UserEntity user = UserEntity.builder().email("panda@pandatronik.com").build();

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));

        jdbc.execute(sqlDisableFkCheck);
        // todo, create plan in script
//        jdbc.execute(sqlCreatePlan);

        jdbc.execute(sqlResetAutoIncrement);
        jdbc.execute(sqlCreateUserEntity);
        jdbc.execute(sqlCreateDays);
    }

    @Test
    public void findById() throws Exception {
        String username = "matek_1991";
        long validId = 300L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findById(userEntity.get(), validId);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.body", equalTo("Days Entry")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "matek_1991";
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findById(userEntity.get(), invalidId);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")));
    }

    @Test
    public void findByDate() throws Exception {
        String username = "matek_1991";
        int year = 2024;
        int month = 3;
        int day = 1;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findByDate(userEntity.get(), day, month, year);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.body", equalTo("Days Entry")))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(1)))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "matek_1991";
        int year = 1924;
        int month = 11;
        int day = 21;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findByDate(userEntity.get(), day, month, year);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/days/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    public void testSave() throws Exception {
        String username = "matek_1991";
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        // Since we are using constraints from DaysEntity
        // @GeneratedValue(strategy = GenerationType.IDENTITY)
        // we cannot use is 1 in
        // sql.script.create.days=insert into days(id,body,rate_day,posted_on,start_date,user_id_id) values(2,'Days Entry',1,'2024-03-24 18:27:41.000000','2024-03-01',1)
        // for save use some crazy id like 300 to distinguish from SQL query
        DaysDTO day = new DaysDTO();
        day.setBody("Saved day in JDBC Template Test");
        day.setPostedOn(LocalDateTime.now());
        day.setStartDate(LocalDate.of(2024, 3, 2));
        day.setRateDay(MadeEnum.FIFTY);
        // userId is set in controller method
        // day.setUserId(userEntity.get().getId());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/days")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(day)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(301)))
                .andExpect(jsonPath("$.body", equalTo("Saved day in JDBC Template Test")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.FIFTY.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(2)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<List<DaysEntity>> allUserDaysEntries = daysRepository.findByUserId(userEntity.get());

        assertTrue(allUserDaysEntries.isPresent());
        assertEquals(2, allUserDaysEntries.get().size());
        assertEquals(2, userEntity.get().getDaysEntity().size());
    }

    // todo: Test save error constraint violation

    @Test
    public void testUpdate() throws Exception {
        String username = "matek_1991";
        long validId = 301L;
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        DaysDTO day = new DaysDTO();
        day.setBody("Saved day in JDBC Template Update Test");
        day.setPostedOn(LocalDateTime.now());
        day.setStartDate(LocalDate.of(2024, 3, 2));
        day.setRateDay(MadeEnum.FIFTY);

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        daysService.save(username, day);

        Optional<DaysEntity> days = daysRepository.findById(validId);
        if (days.isEmpty()) {
            validId = 302L;
        }
        days = daysRepository.findById(validId);
        assertTrue(days.isPresent());
        assertEquals("Saved day in JDBC Template Update Test", days.get().getBody());

        DaysDTO dayUpdated = new DaysDTO();
        dayUpdated.setId(validId);
        dayUpdated.setBody("Saved day in JDBC Template Update Test -- EDITED");
        dayUpdated.setPostedOn(LocalDateTime.now());
        dayUpdated.setStartDate(LocalDate.of(2024, 3, 2));
        dayUpdated.setRateDay(MadeEnum.TWENTY_FIVE);

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/days")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dayUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) validId)))
                .andExpect(jsonPath("$.body", equalTo("Saved day in JDBC Template Update Test -- EDITED")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(2)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<DaysEntity> daysAfterUpdate = daysRepository.findById(validId);
        assertTrue(daysAfterUpdate.isPresent());
        assertEquals("Saved day in JDBC Template Update Test -- EDITED", daysAfterUpdate.get().getBody());

        Optional<List<DaysEntity>> allUserDaysEntries = daysRepository.findByUserId(userEntity.get());

        assertTrue(allUserDaysEntries.isPresent());
        assertEquals(2, allUserDaysEntries.get().size());
        assertEquals(2, userEntity.get().getDaysEntity().size());
    }

    @Test
    public void testDelete() throws Exception {
        String username = "matek_1991";
        long validId = 300L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findById(userEntity.get(), validId);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/days/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.body", equalTo("Days Entry")))
                .andExpect(jsonPath("$.rateDay", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(1)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<DaysEntity> daysAfterDelete = daysRepository.findById(userEntity.get(), validId);
        assertTrue(daysAfterDelete.isEmpty());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        String username = "matek_1991";
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<DaysEntity> days = daysRepository.findById(userEntity.get(), invalidId);
        daysService = new DaysService(userService, daysRepository, new DaysMapperImpl());

        assertTrue(days.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/days/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }

}
