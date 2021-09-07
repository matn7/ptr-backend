package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportantService;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@WebMvcTest(controllers = LessImportantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LessImportantControllerTest extends AbstractTaskControllerTest<LessImportantDTO> {

    @MockBean
    private LessImportantService importantService;

    @Override
    protected LessImportantDTO getTask() {
        LessImportantDTO importantDTO = new LessImportantDTO();
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
        return 1;
    }

    @Override
    protected CrudService<LessImportantDTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String task() {
        return "lessimportant";
    }

    @Override
    protected String body() {
        return "Less Important Task 1";
    }

    @Override
    protected String title() {
        return "Less Important Task 1 title";
    }

    @Override
    protected String getUser() {
        return "User1";
    }
}
