package com.pandatronik.rest;

import com.pandatronik.utils.AppConstants;
import com.pandatronik.web.controllers.SecurityConfigBeans;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DaysControllerIT extends SecurityConfigBeans {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws JSONException {
        final String response = this.restTemplate.getForObject(AppConstants.BASE_URL + "/someuser/extraordinary/all",
                String.class);

        JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003}]", response, false);
    }


//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private ImportantService importantService;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Test
//    public void shouldGetImportantById() throws Exception {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        "admin",
//                        "admin"
//                )
//        );
//
//        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
////        given(importantService.findById("admin", 7L))
////                .willReturn(java.util.Optional.ofNullable(importantEntity));
//
//        String token = jwtTokenProvider.generateToken(authentication);
//
////        mvc.perform(get(API_VERSION + "admin/important/1/7").header("Authorization", "Bearer " + token))
////                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void shouldGetImportantByDate() throws Exception {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        "admin",
//                        "admin"
//                )
//        );
//
//        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
////        given(importantService.findByDate("admin", 2018, 12, 30))
////                .willReturn(java.util.Optional.ofNullable(importantEntity));
//
//        String token = jwtTokenProvider.generateToken(authentication);
//
////        mvc.perform(get(API_VERSION + "admin/important/1/2018/12/30").header("Authorization", "Bearer " + token))
////                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Ignore
//    public void shouldCreateImportant() throws Exception {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        "admin",
//                        "admin"
//                )
//        );
//
//        ImportantEntity importantEntity = ImportantEntityProvider.getValidImportantEntity().build();
////        given(importantService.save("admin", importantEntity))
////                .willReturn(importantEntity);
//
//        String token = jwtTokenProvider.generateToken(authentication);
//
//        String body = "{\n" +
//                "    \"title\": \"Spring docs majka\",\n" +
//                "    \"body\": \"Na 100%\",\n" +
//                "    \"made\": \"100\",\n" +
//                "    \"postedOn\": \"2018-12-30T21:11:52\",\n" +
//                "    \"startDate\": \"2018-12-30\",\n" +
//                "    \"userProfileId\": \"admin\"\n" +
//                "}";
//
////        mvc.perform(post(API_VERSION + "admin/important/1")
////                .header("Authorization", "Bearer " + token)
////                .content(body)
////                .contentType(MediaType.APPLICATION_JSON)
////                .header("Origin", "http://localhost:4200"))
////                .andExpect(status().isOk());
//
////        mvc.perform(post(API_VERSION + "admin/important/1")
////                .content(body)
////                .contentType(MediaType.APPLICATION_JSON)
//////                .header("Content-Type", "application/json")
////                .header("Authorization", "Bearer " + token)
////                .header("Origin", "http://localhost:4200"))
////                .andExpect(status().isCreated());
//    }
}
