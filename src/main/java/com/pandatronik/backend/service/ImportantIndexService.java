package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportantIndexService implements IndexService<IndexDTO> {

    private final ExtraordinaryService extraordinaryService;
    private final DaysService daysService;
    private final ImportantService importantService;
    private final Important2Service important2Service;
    private final Important3Service important3Service;

    @Override
    public IndexDTO getData(String username, int year, int month) {
        IndexDTO importantIndexDTO = new IndexDTO();
        final List<ExtraordinaryDTO> extraordinaries = extraordinaryService.findByDate(username, year, month);
        final List<DaysDTO> days = daysService.findByDate(username, year, month);
        final List<TaskDTO> important = importantService.findByDate(username, year, month);
        final List<TaskDTO> important2 = important2Service.findByDate(username, year, month);
        final List<TaskDTO> important3 = important3Service.findByDate(username, year, month);

        importantIndexDTO.getExtraordinaryDTO().addAll(extraordinaries);
        importantIndexDTO.getDaysDTO().addAll(days);
        importantIndexDTO.getTaskDTO().addAll(important);
        importantIndexDTO.getTask2DTO().addAll(important2);
        importantIndexDTO.getTask3DTO().addAll(important3);

        return importantIndexDTO;
    }
}
