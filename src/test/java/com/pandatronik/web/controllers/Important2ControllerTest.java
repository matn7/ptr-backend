package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.enums.MadeEnum;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@WebMvcTest(controllers = Important2Controller.class)
@AutoConfigureMockMvc(addFilters = false)
public class Important2ControllerTest extends AbstractTaskControllerTest<Important2DTO> {

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
    protected int testCase() {
        return 2;
    }

    @Override
    protected CrudService<Important2DTO, Long> getService() {
        return this.importantService;
    }

    @Override
    protected String task() {
        return "important";
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
