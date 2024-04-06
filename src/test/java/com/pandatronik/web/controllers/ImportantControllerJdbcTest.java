package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.Important2MapperImpl;
import com.pandatronik.backend.persistence.mapper.Important3MapperImpl;
import com.pandatronik.backend.persistence.mapper.ImportantMapperImpl;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.Important2Repository;
import com.pandatronik.backend.persistence.repositories.Important3Repository;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
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

@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test-mysql.properties")
@SpringBootTest
@Transactional
public class ImportantControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImportantRepository importantRepository;

    @Autowired
    Important2Repository important2Repository;

    @Autowired
    Important3Repository important3Repository;

    @Autowired
    ImportantService importantService;

    @Autowired
    Important2Service important2Service;

    @Autowired
    Important3Service important3Service;

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

    // Scripts
    @Value("${sql.script.disable.fk.check}")
    private String sqlDisableFkCheck;

    @Value("${sql.script.create.plan}")
    private String sqlCreatePlan;

    @Value("${sql.script.create.userEntity}")
    private String sqlCreateUserEntity;

    @Value("${sql.script.delete.userEntity}")
    private String sqlDeleteUserEntity;

    @Value("${sql.script.userEntity.reset.auto.increment}")
    private String sqlUserEntityResetAutoIncrement;

    @Value("${sql.script.create.important1}")
    private String sqlCreateImportant;

    @Value("${sql.script.create.important2}")
    private String sqlCreateImportant2;

    @Value("${sql.script.create.important3}")
    private String sqlCreateImportant3;

    @Value("${sql.script.important.reset.auto.increment}")
    private String sqlImportantResetAutoIncrement;

    @Value("${sql.script.important.2.reset.auto.increment}")
    private String sqlImportant2ResetAutoIncrement;

    @Value("${sql.script.important.3.reset.auto.increment}")
    private String sqlImportant3ResetAutoIncrement;

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
        jdbc.execute(sqlImportantResetAutoIncrement);
        jdbc.execute(sqlImportant2ResetAutoIncrement);
        jdbc.execute(sqlImportant3ResetAutoIncrement);

        // Create new test user for every test case
        jdbc.execute(sqlDeleteUserEntity);
        jdbc.execute(sqlCreateUserEntity);

        // Create test data
        jdbc.execute(sqlCreateImportant);
        jdbc.execute(sqlCreateImportant2);
        jdbc.execute(sqlCreateImportant3);
    }

    @Test
    public void findById() throws Exception {
        String username = "matek_1991";
        long validId = 300L;
        int importantId = 1;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ImportantEntity> important = importantRepository.findById(userEntity.get(), validId);
        importantService = new ImportantService(userService, importantRepository, new ImportantMapperImpl());

        assertTrue(important.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Important Entry Title 1")))
                .andExpect(jsonPath("$.body", equalTo("Important Entry 1")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(1)))
                .andExpect(jsonPath("$.startDate[2]", is(11)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "matek_1991";
        int importantId = 2;
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<Important2Entity> important2 = important2Repository.findById(userEntity.get(), invalidId);
        important2Service = new Important2Service(userService, important2Repository, new Important2MapperImpl());

        assertTrue(important2.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + invalidId)
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
        String username = "matek_1991";
        int importantId = 3;
        int year = 2024;
        int month = 3;
        int day = 30;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<Important3Entity> important3 = important3Repository.findByDate(userEntity.get(), day, month, year);
        important3Service = new Important3Service(userService, important3Repository, new Important3MapperImpl());

        assertTrue(important3.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Important Entry Title 3")))
                .andExpect(jsonPath("$.body", equalTo("Important Entry 3")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(30)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "matek_1991";
        int importantId = 1;
        int year = 1924;
        int month = 11;
        int day = 21;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ImportantEntity> important = importantRepository.findByDate(userEntity.get(), day, month, year);
        importantService = new ImportantService(userService, importantRepository, new ImportantMapperImpl());

        assertTrue(important.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + year + "/" + month + "/" + day)
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
        int importantId = 2;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        TaskDTO task = new TaskDTO();
        task.setBody("Saved Task - Body");
        task.setTitle("Saved Task - Title");
        task.setPostedOn(LocalDateTime.now());
        task.setStartDate(LocalDate.of(2025, 9, 10));
        task.setMade(MadeEnum.HUNDRED);

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/important/" + importantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(301)))
                .andExpect(jsonPath("$.body", equalTo("Saved Task - Body")))
                .andExpect(jsonPath("$.title", equalTo("Saved Task - Title")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.HUNDRED.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2025)))
                .andExpect(jsonPath("$.startDate[1]", is(9)))
                .andExpect(jsonPath("$.startDate[2]", is(10)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<List<Important2Entity>> allImportant2Entries = important2Repository.findByUserId(userEntity.get());

        assertTrue(allImportant2Entries.isPresent());
        assertEquals(2, allImportant2Entries.get().size());
        assertEquals(2, userEntity.get().getImportant2Entity().size());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "matek_1991";
        int importantId = 3;
        long validId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        TaskDTO important = new TaskDTO();
//        important.setId(200L);
        important.setTitle("Important Task Title - Update Test");
        important.setBody("Important Task Body - Update Test");
        important.setMade(MadeEnum.FIFTY);
        important.setStartDate(LocalDate.of(2026, 11, 30));
        important.setPostedOn(LocalDateTime.now());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        important3Service.save(username, important);

        // in case run all tests, new valid id is 2L
        Optional<Important3Entity> important3 = important3Repository.findById(validId);
        if (important3.isEmpty()) {
            validId = 2L;
        }
        important3 = important3Repository.findById(validId);
        assertTrue(important3.isPresent());
        assertEquals("Important Task Body - Update Test", important3.get().getBody());
        assertEquals("Important Task Title - Update Test", important3.get().getTitle());

        TaskDTO importantUpdated = new TaskDTO();
        importantUpdated.setId(validId);
        importantUpdated.setTitle("Important Task Title -- EDITED");
        importantUpdated.setBody("Important Task Body -- EDITED");
        importantUpdated.setMade(MadeEnum.SEVENTY_FIVE);
        importantUpdated.setStartDate(LocalDate.of(2026, 12, 30));
        importantUpdated.setPostedOn(LocalDateTime.now());

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/important/" + importantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(importantUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) validId)))
                .andExpect(jsonPath("$.body", equalTo("Important Task Body -- EDITED")))
                .andExpect(jsonPath("$.title", equalTo("Important Task Title -- EDITED")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2026)))
                .andExpect(jsonPath("$.startDate[1]", is(12)))
                .andExpect(jsonPath("$.startDate[2]", is(30)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<Important3Entity> importantAfterUpdate = important3Repository.findById(validId);
        assertTrue(importantAfterUpdate.isPresent());
        assertEquals("Important Task Body -- EDITED", importantAfterUpdate.get().getBody());
        assertEquals("Important Task Title -- EDITED", importantAfterUpdate.get().getTitle());

        Optional<List<Important3Entity>> allUserImportant3Entries = important3Repository.findByUserId(userEntity.get());

        assertTrue(allUserImportant3Entries.isPresent());
        assertEquals(2, allUserImportant3Entries.get().size());
        assertEquals(2, userEntity.get().getImportant3Entity().size());
    }

    @Test
    public void testDelete() throws Exception {
        String username = "matek_1991";
        int importantId = 1;
        long validId = 300L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<ImportantEntity> important = importantRepository.findById(userEntity.get(), validId);
        importantService = new ImportantService(userService, importantRepository, new ImportantMapperImpl());

        assertTrue(important.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Important Entry Title 1")))
                .andExpect(jsonPath("$.body", equalTo("Important Entry 1")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(1)))
                .andExpect(jsonPath("$.startDate[2]", is(11)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<ImportantEntity> importantAfterDelete = importantRepository.findById(userEntity.get(), validId);
        assertTrue(importantAfterDelete.isEmpty());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        String username = "matek_1991";
        int importantId = 2;
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<Important2Entity> important = important2Repository.findById(userEntity.get(), invalidId);
        important2Service = new Important2Service(userService, important2Repository, new Important2MapperImpl());

        assertTrue(important.isEmpty());
        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/important/" + importantId + "/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }

}
