package com.pandatronik.rest;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.validators.ImportantEntityProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

//import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportantEntityRestIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImportantService importantService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void shouldGetImportantById() throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin"
                )
        );

        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
//        given(importantService.findById("admin", 7L))
//                .willReturn(java.util.Optional.ofNullable(importantEntity));

        String token = jwtTokenProvider.generateToken(authentication);

//        mvc.perform(get(API_VERSION + "admin/important/1/7").header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk());

    }

    @Test
    public void shouldGetImportantByDate() throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin"
                )
        );

        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
//        given(importantService.findByDate("admin", 2018, 12, 30))
//                .willReturn(java.util.Optional.ofNullable(importantEntity));

        String token = jwtTokenProvider.generateToken(authentication);

//        mvc.perform(get(API_VERSION + "admin/important/1/2018/12/30").header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void shouldCreateImportant() throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin"
                )
        );

        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
//        given(importantService.save("admin", importantEntity))
//                .willReturn(importantEntity);

        String token = jwtTokenProvider.generateToken(authentication);

        String body = "{\n" +
                "    \"title\": \"Spring docs majka\",\n" +
                "    \"body\": \"Na 100%\",\n" +
                "    \"made\": \"100\",\n" +
                "    \"postedOn\": \"2018-12-30T21:11:52\",\n" +
                "    \"startDate\": \"2018-12-30\",\n" +
                "    \"userProfileId\": \"admin\"\n" +
                "}";

//        mvc.perform(post(API_VERSION + "admin/important/1")
//                .header("Authorization", "Bearer " + token)
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Origin", "http://localhost:4200"))
//                .andExpect(status().isOk());

//        mvc.perform(post(API_VERSION + "admin/important/1")
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON)
////                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .header("Origin", "http://localhost:4200"))
//                .andExpect(status().isCreated());
    }
}
