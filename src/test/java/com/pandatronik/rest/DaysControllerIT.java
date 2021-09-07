package com.pandatronik.rest;

import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.configuration.ValidatorConfiguration;
import com.pandatronik.enums.RateDayEnum;
import com.pandatronik.utils.AppConstants;
import com.pandatronik.web.controllers.SecurityConfigBeans;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ValidatorConfiguration.class})
public class DaysControllerIT extends SecurityConfigBeans {

    @MockBean
    private DaysService daysService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws JSONException {
        // given
        given(daysService.findById(anyLong(), anyLong())).willReturn(getDaysDTO());

        final ResponseEntity<DaysDTO> forEntity =
                this.restTemplate.getForEntity(AppConstants.BASE_URL + "/someuser/days/1", DaysDTO.class);

//        JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003}]", response, false);
        System.out.println();
    }

    private DaysDTO getDaysDTO() {
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(2025, 5, 25));
        day.setPostedOn(LocalDateTime.now());
        day.setRateDay(RateDayEnum.VERY_GOOD);
        return day;
    }

}
