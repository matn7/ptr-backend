package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportantService;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class LessImportantDTOValidatorTest extends AbstractTaskValidatorTest<LessImportantDTO> {

    @MockBean
    private LessImportantService lessImportantService;

    @Override
    protected LessImportantDTO getTask() {
        LessImportantDTO lessImportantDTO = new LessImportantDTO();
        lessImportantDTO.setId(1L);
        lessImportantDTO.setTitle(title());
        lessImportantDTO.setBody(body());
        lessImportantDTO.setMade(MadeEnum.HUNDRED);
        lessImportantDTO.setPostedOn(LocalDateTime.now());
        lessImportantDTO.setStartDate(LocalDate.of(2025,5,25));
        return lessImportantDTO;
    }

    @Override
    protected CrudService<LessImportantDTO, Long> getService() {
        return this.lessImportantService;
    }

    @Override
    protected String getUser() {
        return "User1";
    }

    @Override
    protected String body() {
        return "Less Important Task 1";
    }

    @Override
    protected String title() {
        return "Less Important Task 1 title";
    }

}
