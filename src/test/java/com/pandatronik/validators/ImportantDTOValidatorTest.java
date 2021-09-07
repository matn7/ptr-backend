package com.pandatronik.validators;

import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class ImportantDTOValidatorTest extends AbstractTaskValidatorTest<ImportantDTO> {

    @MockBean
    private ImportantService importantService;

    @Override
    protected ImportantDTO getTask() {
        ImportantDTO importantDTO = new ImportantDTO();
        importantDTO.setId(1L);
        importantDTO.setTitle(title());
        importantDTO.setBody(body());
        importantDTO.setMade(MadeEnum.HUNDRED);
        importantDTO.setPostedOn(LocalDateTime.now());
        importantDTO.setStartDate(LocalDate.of(2025,5,25));
        return importantDTO;
    }

    @Override
    protected CrudService<ImportantDTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String getUser() {
        return "User1";
    }

    @Override
    protected String body() {
        return "Important Task 1";
    }

    @Override
    protected String title() {
        return "Important Task 1 title";
    }

}
