package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.ExtraordinaryMapperImpl;
import com.pandatronik.backend.persistence.mapper.Important2MapperImpl;
import com.pandatronik.backend.persistence.mapper.Important3MapperImpl;
import com.pandatronik.backend.persistence.mapper.ImportantMapperImpl;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.backend.service.ImportantService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test-mysql.properties")
@SpringBootTest
@Transactional
public class ExtraordinaryControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExtraordinaryRepository extraordinaryRepository;

    @Autowired
    ExtraordinaryService extraordinaryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

    @Value("${sql.script.disable.fk.check}")
    private String sqlDisableFkCheck;

    @Value("${sql.script.create.plan}")
    private String sqlCreatePlan;

    @Value("${sql.script.create.userEntity}")
    private String sqlCreateUserEntity;

    @Value("${sql.script.create.extraordinary}")
    private String sqlCreateExtraordinary;

    @Value("${sql.script.extraordinary.reset.auto.increment}")
    private String sqlExtraordinaryResetAutoIncrement;

    @Value("${sql.script.userEntity.reset.auto.increment}")
    private String sqlUserEntityResetAutoIncrement;

    @Value("${sql.script.delete.userEntity}")
    private String sqlDeleteUserEntity;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @BeforeEach
    public void setUpDays() {
        UserEntity user = UserEntity.builder().email("panda@pandatronik.com").build();

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserById(anyLong())).thenReturn(user);
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));

        // Disable ref-integrity check, reset auto-increment
        jdbc.execute(sqlDisableFkCheck);
        jdbc.execute(sqlUserEntityResetAutoIncrement);
        jdbc.execute(sqlExtraordinaryResetAutoIncrement);

//        jdbc.execute(sqlCreatePlan);

        // Create new test user for every test case
        jdbc.execute(sqlDeleteUserEntity);
        jdbc.execute(sqlCreateUserEntity);

        // Create test data
        jdbc.execute(sqlCreateExtraordinary);
    }

    @Test
    public void findById() throws Exception {
        String username = "user_123";
        long validId = 300L;
        int year = 1944;
        int month = 6;
        int day = 6;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findById(userEntity.get(), validId);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Normandy")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "user_123";
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findById(userEntity.get(), invalidId);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    public void findByDate() throws Exception {
        String username = "user_123";
        int year = 1944;
        int month = 6;
        int day = 6;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findByDate(userEntity.get(), day, month, year);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" +  year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Normandy")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "user_123";
        int year = 1944;
        int month = 6;
        int day = 7;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findByDate(userEntity.get(), day, month, year);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + year + "/" + month + "/" + day)
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
        String username = "user_123";
        int year = 1944;
        int month = 9;
        int day = 17;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setTitle("Operation Market Garden");
        extraordinary.setBody("Paratroopers landing in Holland");
        extraordinary.setStartDate(LocalDate.of(year, month, day));
        extraordinary.setPostedOn(LocalDateTime.now());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/extraordinary")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraordinary)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(301)))
                .andExpect(jsonPath("$.body", equalTo("Paratroopers landing in Holland")))
                .andExpect(jsonPath("$.title", equalTo("Operation Market Garden")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<List<ExtraordinaryEntity>> allExtraordinaryEntries = extraordinaryRepository.findByUserId(userEntity.get());

        assertTrue(allExtraordinaryEntries.isPresent());
        assertEquals(2, allExtraordinaryEntries.get().size());
        assertEquals(2, userEntity.get().getExtraordinaryEntity().size());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "user_123";
        int year = 1944;
        int month = 9;
        int day = 17;
        long validId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        ExtraordinaryDTO extraordinary = new ExtraordinaryDTO();
        extraordinary.setTitle("Operation Market Garden - Update Test");
        extraordinary.setBody("Paratroopers landing in Holland - Update Test");
        extraordinary.setStartDate(LocalDate.of(year, month, day));
        extraordinary.setPostedOn(LocalDateTime.now());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        extraordinaryService.save(username, extraordinary);

        // in case run all tests, new valid id is 2L
        Optional<ExtraordinaryEntity> extraordinaryEntity = extraordinaryRepository.findById(validId);
        if (extraordinaryEntity.isEmpty()) {
            validId = 302L;
        }
        extraordinaryEntity = extraordinaryRepository.findById(validId);
        assertTrue(extraordinaryEntity.isPresent());
        assertEquals("Operation Market Garden - Update Test", extraordinaryEntity.get().getTitle());
        assertEquals("Paratroopers landing in Holland - Update Test", extraordinaryEntity.get().getBody());

        ExtraordinaryDTO extraordinaryUpdated = new ExtraordinaryDTO();
        extraordinaryUpdated.setId(validId);
        extraordinaryUpdated.setTitle("Operation Market Garden -- EDITED");
        extraordinaryUpdated.setBody("Paratroopers landing in Holland -- EDITED");
        extraordinaryUpdated.setStartDate(LocalDate.of(year, month, day));
        extraordinaryUpdated.setPostedOn(LocalDateTime.now());

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/extraordinary")
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraordinaryUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) validId)))
                .andExpect(jsonPath("$.title", equalTo("Operation Market Garden -- EDITED")))
                .andExpect(jsonPath("$.body", equalTo("Paratroopers landing in Holland -- EDITED")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<ExtraordinaryEntity> extraordinaryAfterUpdate = extraordinaryRepository.findById(validId);
        assertTrue(extraordinaryAfterUpdate.isPresent());
        assertEquals("Operation Market Garden -- EDITED", extraordinaryAfterUpdate.get().getTitle());
        assertEquals("Paratroopers landing in Holland -- EDITED", extraordinaryAfterUpdate.get().getBody());

        Optional<List<ExtraordinaryEntity>> allExtraordinaryEntries = extraordinaryRepository.findByUserId(userEntity.get());

        assertTrue(allExtraordinaryEntries.isPresent());
        assertEquals(2, allExtraordinaryEntries.get().size());
        assertEquals(2, userEntity.get().getExtraordinaryEntity().size());
    }

    @Test
    public void testDelete() throws Exception {
        String username = "user_123";
        long validId = 300L;
        int year = 1944;
        int month = 6;
        int day = 6;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findById(userEntity.get(), validId);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Battle of Normandy")))
                .andExpect(jsonPath("$.body", equalTo("D-Day")))
                .andExpect(jsonPath("$.startDate[0]", is(year)))
                .andExpect(jsonPath("$.startDate[1]", is(month)))
                .andExpect(jsonPath("$.startDate[2]", is(day)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<ExtraordinaryEntity> extraordinaryAfterDelete = extraordinaryRepository.findById(userEntity.get(), validId);
        assertTrue(extraordinaryAfterDelete.isEmpty());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        String username = "user_123";
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ExtraordinaryEntity> extraordinary = extraordinaryRepository.findById(userEntity.get(), invalidId);
        extraordinaryService = new ExtraordinaryService(userService, extraordinaryRepository, new ExtraordinaryMapperImpl());

        assertTrue(extraordinary.isEmpty());
        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/extraordinary/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }

}
