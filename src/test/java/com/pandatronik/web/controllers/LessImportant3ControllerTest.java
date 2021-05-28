package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportant3Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@WebMvcTest(controllers = LessImportant3Controller.class)
@AutoConfigureMockMvc(addFilters = false)
public class LessImportant3ControllerTest extends AbstractTaskControllerTest<LessImportant3DTO> {

    @MockBean
    private LessImportant3Service importantService;

    @Override
    protected LessImportant3DTO getTask() {
        LessImportant3DTO importantDTO = new LessImportant3DTO();
        importantDTO.setId(1L);
        importantDTO.setTitle(title());
        importantDTO.setBody(body());
        importantDTO.setMade(MadeEnum.HUNDRED);
        importantDTO.setPostedOn(LocalDateTime.now());
        importantDTO.setStartDate(LocalDate.of(2025,5,25));
        return importantDTO;
    }

    @Override
    protected int testCase() {
        return 3;
    }

    @Override
    protected CrudService<LessImportant3DTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String task() {
        return "lessimportant";
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
