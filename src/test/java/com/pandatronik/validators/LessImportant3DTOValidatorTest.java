package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportant3Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class LessImportant3DTOValidatorTest extends AbstractTaskValidatorTest<LessImportant3DTO> {

    @MockBean
    private LessImportant3Service lessImportantService;

    @Override
    protected LessImportant3DTO getTask() {
        LessImportant3DTO lessImportantDTO = new LessImportant3DTO();
        lessImportantDTO.setId(1L);
        lessImportantDTO.setTitle(title());
        lessImportantDTO.setBody(body());
        lessImportantDTO.setMade(MadeEnum.HUNDRED);
        lessImportantDTO.setPostedOn(LocalDateTime.now());
        lessImportantDTO.setStartDate(LocalDate.of(2025,5,25));
        return lessImportantDTO;
    }

    @Override
    protected CrudService<LessImportant3DTO, Long> getService() {
        return this.lessImportantService;
    }

    @Override
    protected String getUser() {
        return "User3";
    }

    @Override
    protected String body() {
        return "Less Important Task 3";
    }

    @Override
    protected String title() {
        return "Less Important Task 3 title";
    }

}
