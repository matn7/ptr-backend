package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Important2DTOValidatorTest extends AbstractTaskValidatorTest<Important2DTO> {

    @MockBean
    private Important2Service importantService;

    @Override
    protected Important2DTO getTask() {
        Important2DTO importantDTO = new Important2DTO();
        importantDTO.setId(1L);
        importantDTO.setTitle(title());
        importantDTO.setBody(body());
        importantDTO.setMade(MadeEnum.HUNDRED);
        importantDTO.setPostedOn(LocalDateTime.now());
        importantDTO.setStartDate(LocalDate.of(2025,5,25));
        return importantDTO;
    }

    @Override
    protected CrudService<Important2DTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String getUser() {
        return "User2";
    }

    @Override
    protected String body() {
        return "Important Task 2";
    }

    @Override
    protected String title() {
        return "Important Task 2 title";
    }

}
