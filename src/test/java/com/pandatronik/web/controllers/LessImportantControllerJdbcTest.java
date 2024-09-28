package com.pandatronik.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.*;
import com.pandatronik.backend.persistence.mapper.*;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.*;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.*;
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
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
@Transactional
public class LessImportantControllerJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LessImportantRepository lessImportantRepository;

    @Autowired
    LessImportant2Repository lessImportant2Repository;

    @Autowired
    LessImportant3Repository lessImportant3Repository;

    @Autowired
    LessImportantService lessImportantService;

    @Autowired
    LessImportant2Service lessImportant2Service;

    @Autowired
    LessImportant3Service lessImportant3Service;

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

    // User Entity
    @Value("${sql.script.create.userEntity}")
    private String sqlCreateUserEntity;

    @Value("${sql.script.create.less.important1}")
    private String sqlCreateLessImportant;

    @Value("${sql.script.create.less.important2}")
    private String sqlCreateLessImportant2;

    @Value("${sql.script.create.less.important3}")
    private String sqlCreateLessImportant3;

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
        jdbc.execute(sqlCreateLessImportant);
        jdbc.execute(sqlCreateLessImportant2);
        jdbc.execute(sqlCreateLessImportant3);
    }

    @Test
    public void findById() throws Exception {
        String username = "user_123";
        long validId = 300L;
        int lessImportantId = 1;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportantEntity> important = lessImportantRepository.findById(userEntity.get(), validId);
        lessImportantService = new LessImportantService(userService, lessImportantRepository, new LessImportantMapperImpl());

        assertTrue(important.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Entry Title 1")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Entry 1")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(1)))
                .andExpect(jsonPath("$.startDate[2]", is(11)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        String username = "user_123";
        int lessImportantId = 2;
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportant2Entity> lessImportant2 = lessImportant2Repository.findById(userEntity.get(), invalidId);
        lessImportant2Service = new LessImportant2Service(userService, lessImportant2Repository, new LessImportant2MapperImpl());

        assertTrue(lessImportant2.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + invalidId)
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
        int lessImportantId = 3;
        int year = 2024;
        int month = 3;
        int day = 30;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportant3Entity> lessImportant3 = lessImportant3Repository.findByDate(userEntity.get(), day, month, year);
        lessImportant3Service = new LessImportant3Service(userService, lessImportant3Repository, new LessImportant3MapperImpl());

        assertTrue(lessImportant3.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + year + "/" + month + "/" + day)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Entry Title 3")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Entry 3")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.TWENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(3)))
                .andExpect(jsonPath("$.startDate[2]", is(30)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }

    @Test
    public void findByDateNotFound() throws Exception {
        String username = "user_123";
        int lessImportantId = 1;
        int year = 1924;
        int month = 11;
        int day = 21;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportantEntity> lessImportant = lessImportantRepository.findByDate(userEntity.get(), day, month, year);
        lessImportantService = new LessImportantService(userService, lessImportantRepository, new LessImportantMapperImpl());

        assertTrue(lessImportant.isEmpty());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(get(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + year + "/" + month + "/" + day)
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
        int lessImportantId = 2;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        TaskDTO task = new TaskDTO();
        task.setBody("Saved Task - Body");
        task.setTitle("Saved Task - Title");
        task.setPostedOn(LocalDateTime.now());
        task.setStartDate(LocalDate.of(2025, 9, 10));
        task.setMade(MadeEnum.HUNDRED);

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(post(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.body", equalTo("Saved Task - Body")))
                .andExpect(jsonPath("$.title", equalTo("Saved Task - Title")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.HUNDRED.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2025)))
                .andExpect(jsonPath("$.startDate[1]", is(9)))
                .andExpect(jsonPath("$.startDate[2]", is(10)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<List<LessImportant2Entity>> allImportant2Entries = lessImportant2Repository.findByUserId(userEntity.get());

        assertTrue(allImportant2Entries.isPresent());
        assertEquals(2, allImportant2Entries.get().size());
        assertEquals(2, userEntity.get().getLessImportant2Entity().size());
    }

    @Test
    public void testUpdate() throws Exception {
        String username = "user_123";
        int lessImportantId = 3;
        long validId = 1L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        assertTrue(userEntity.isPresent());

        TaskDTO important = new TaskDTO();
        important.setId(200L);
        important.setTitle("Less Important Task Title - Update Test");
        important.setBody("Less Important Task Body - Update Test");
        important.setMade(MadeEnum.FIFTY);
        important.setStartDate(LocalDate.of(2026, 11, 30));
        important.setPostedOn(LocalDateTime.now());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        lessImportant3Service.save(username, important);

        // in case run all tests, new valid id is 2L
        Optional<LessImportant3Entity> lessImportant3 = lessImportant3Repository.findById(validId);
        if (lessImportant3.isEmpty()) {
            validId = 2L;
        }
        lessImportant3 = lessImportant3Repository.findById(validId);
        assertTrue(lessImportant3.isPresent());
        assertEquals("Less Important Task Body - Update Test", lessImportant3.get().getBody());
        assertEquals("Less Important Task Title - Update Test", lessImportant3.get().getTitle());

        TaskDTO lessImportantUpdated = new TaskDTO();
        lessImportantUpdated.setId(validId);
        lessImportantUpdated.setTitle("Less Important Task Title -- EDITED");
        lessImportantUpdated.setBody("Less Important Task Body -- EDITED");
        lessImportantUpdated.setMade(MadeEnum.SEVENTY_FIVE);
        lessImportantUpdated.setStartDate(LocalDate.of(2026, 12, 30));
        lessImportantUpdated.setPostedOn(LocalDateTime.now());

        mockMvc.perform(put(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessImportantUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) validId)))
                .andExpect(jsonPath("$.body", equalTo("Less Important Task Body -- EDITED")))
                .andExpect(jsonPath("$.title", equalTo("Less Important Task Title -- EDITED")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2026)))
                .andExpect(jsonPath("$.startDate[1]", is(12)))
                .andExpect(jsonPath("$.startDate[2]", is(30)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<LessImportant3Entity> lessImportantAfterUpdate = lessImportant3Repository.findById(validId);
        assertTrue(lessImportantAfterUpdate.isPresent());
        assertEquals("Less Important Task Body -- EDITED", lessImportantAfterUpdate.get().getBody());
        assertEquals("Less Important Task Title -- EDITED", lessImportantAfterUpdate.get().getTitle());

        Optional<List<LessImportant3Entity>> allUserLessImportant3Entries = lessImportant3Repository.findByUserId(userEntity.get());

        assertTrue(allUserLessImportant3Entries.isPresent());
        assertEquals(2, allUserLessImportant3Entries.get().size());
        assertEquals(2, userEntity.get().getLessImportant3Entity().size());
    }

    @Test
    public void testDelete() throws Exception {
        String username = "user_123";
        int lessImportantId = 1;
        long validId = 300L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportantEntity> lessImportant = lessImportantRepository.findById(userEntity.get(), validId);
        lessImportantService = new LessImportantService(userService, lessImportantRepository, new LessImportantMapperImpl());

        assertTrue(lessImportant.isPresent());

        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + validId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(300)))
                .andExpect(jsonPath("$.title", equalTo("Less Important Entry Title 1")))
                .andExpect(jsonPath("$.body", equalTo("Less Important Entry 1")))
                .andExpect(jsonPath("$.made", equalTo(MadeEnum.SEVENTY_FIVE.getValue())))
                .andExpect(jsonPath("$.startDate[0]", is(2024)))
                .andExpect(jsonPath("$.startDate[1]", is(1)))
                .andExpect(jsonPath("$.startDate[2]", is(11)))
                .andExpect(jsonPath("$.postedOn", not(empty())))
                .andExpect(jsonPath("$.userId").doesNotExist());

        Optional<LessImportantEntity> lessImportantAfterDelete = lessImportantRepository.findById(userEntity.get(), validId);
        assertTrue(lessImportantAfterDelete.isEmpty());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        String username = "user_123";
        int lessImportantId = 2;
        long invalidId = 301L;

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        assertTrue(userEntity.isPresent());

        Optional<LessImportant2Entity> lessImportant2 = lessImportant2Repository.findById(userEntity.get(), invalidId);
        lessImportant2Service = new LessImportant2Service(userService, lessImportant2Repository, new LessImportant2MapperImpl());

        assertTrue(lessImportant2.isEmpty());
        when(userService.findByUserName(username)).thenReturn(userEntity.get());

        mockMvc.perform(delete(AppConstants.BASE_URL + "/" + username + "/lessimportant/" + lessImportantId + "/" + invalidId)
                        .header("Authorization", "Bearer ABC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.body", equalTo("Resource Not Found")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.value())));
    }


}
