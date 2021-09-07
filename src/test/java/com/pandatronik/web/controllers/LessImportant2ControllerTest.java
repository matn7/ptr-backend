package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.LessImportant2Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@WebMvcTest(controllers = LessImportant2Controller.class)
@AutoConfigureMockMvc(addFilters = false)
public class LessImportant2ControllerTest extends AbstractTaskControllerTest<LessImportant2DTO> {

    @MockBean
    private LessImportant2Service importantService;

    @Override
    protected LessImportant2DTO getTask() {
        LessImportant2DTO importantDTO = new LessImportant2DTO();
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
        return 2;
    }

    @Override
    protected CrudService<LessImportant2DTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String task() {
        return "lessimportant";
    }

    @Override
    protected String body() {
        return "Less Important Task 2";
    }

    @Override
    protected String title() {
        return "Less Important Task 2 title";
    }

    @Override
    protected String getUser() {
        return "User2";
    }
}
