package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Important3DTOValidatorTest extends AbstractTaskValidatorTest<Important3DTO> {
    
    @MockBean
    private Important3Service importantService;

    @Override
    protected Important3DTO getTask() {
        Important3DTO importantDTO = new Important3DTO();
        importantDTO.setId(1L);
        importantDTO.setTitle(title());
        importantDTO.setBody(body());
        importantDTO.setMade(MadeEnum.HUNDRED);
        importantDTO.setPostedOn(LocalDateTime.now());
        importantDTO.setStartDate(LocalDate.of(2025,5,25));
        return importantDTO;
    }

    @Override
    protected CrudService<Important3DTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String getUser() {
        return "User3";
    }

    @Override
    protected String body() {
        return "Important Task 3";
    }

    @Override
    protected String title() {
        return "Important Task 3 title";
    }

}
