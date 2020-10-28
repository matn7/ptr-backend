package com.pandatronik.backend.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LessImportantIndexDTO {
    private List<ExtraordinaryDTO> extraordinaryDTO = new ArrayList<>();
    private List<LessImportantDTO> lessImportantDTO = new ArrayList<>();
    private List<LessImportant2DTO> lessImportant2DTO = new ArrayList<>();
    private List<LessImportant3DTO> lessImportant3DTO = new ArrayList<>();
    private List<DaysDTO> daysDTO = new ArrayList<>();
}
