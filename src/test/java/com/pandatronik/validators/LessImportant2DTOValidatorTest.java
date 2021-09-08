package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportant2Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class LessImportant2DTOValidatorTest extends AbstractTaskValidatorTest<LessImportant2DTO> {

    @MockBean
    private LessImportant2Service lessImportantService;

    @Override
    protected LessImportant2DTO getTask() {
        LessImportant2DTO lessImportantDTO = new LessImportant2DTO();
        lessImportantDTO.setId(1L);
        lessImportantDTO.setTitle(title());
        lessImportantDTO.setBody(body());
        lessImportantDTO.setMade(MadeEnum.HUNDRED);
        lessImportantDTO.setPostedOn(LocalDateTime.now());
        lessImportantDTO.setStartDate(LocalDate.of(2025,5,25));
        return lessImportantDTO;
    }

    @Override
    protected CrudService<LessImportant2DTO, Long> getService() {
        return this.lessImportantService;
    }

    @Override
    protected String getUser() {
        return "User2";
    }

    @Override
    protected String body() {
        return "Less Important Task 2";
    }

    @Override
    protected String title() {
        return "Less Important Task 2 title";
    }

}
