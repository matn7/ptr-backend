package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessImportantIndexService implements IndexService<IndexDTO> {

    private final ExtraordinaryService extraordinaryService;
    private final DaysService daysService;
    private final LessImportantService lessImportantService;
    private final LessImportant2Service lessImportant2Service;
    private final LessImportant3Service lessImportant3Service;

    @Override
    public IndexDTO getData(String username, int year, int month) {
        IndexDTO lessImportantIndexDTO = new IndexDTO();
        final List<ExtraordinaryDTO> extraordinaries = extraordinaryService.findByDate(username, year, month);
        final List<DaysDTO> days = daysService.findByDate(username, year, month);
        final List<TaskDTO> lessImportant = lessImportantService.findByDate(username, year, month);
        final List<TaskDTO> lessImportant2 = lessImportant2Service.findByDate(username, year, month);
        final List<TaskDTO> lessImportant3 = lessImportant3Service.findByDate(username, year, month);

        lessImportantIndexDTO.getExtraordinaryDTO().addAll(extraordinaries);
        lessImportantIndexDTO.getDaysDTO().addAll(days);
        lessImportantIndexDTO.getTaskDTO().addAll(lessImportant);
        lessImportantIndexDTO.getTask2DTO().addAll(lessImportant2);
        lessImportantIndexDTO.getTask3DTO().addAll(lessImportant3);

        return lessImportantIndexDTO;
    }
}
