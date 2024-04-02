package com.pandatronik.backend.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class IndexDTO {
    // TODO: might be ExtraordinaryListDTO the same for rest field ?
    private List<ExtraordinaryDTO> extraordinaryDTO = new ArrayList<>();
    private List<TaskDTO> taskDTO = new ArrayList<>();
    private List<TaskDTO> task2DTO = new ArrayList<>();
    private List<TaskDTO> task3DTO = new ArrayList<>();
    private List<DaysDTO> daysDTO = new ArrayList<>();
}
