package com.pandatronik.backend.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ImportantIndexDTO {
    // TODO: might be ExtraordinaryListDTO the same for rest field ?
    private List<ExtraordinaryDTO> extraordinaryDTO = new ArrayList<>();
    private List<ImportantDTO> importantDTO = new ArrayList<>();
    private List<Important2DTO> important2DTO = new ArrayList<>();
    private List<Important3DTO> important3DTO = new ArrayList<>();
    private List<DaysDTO> daysDTO = new ArrayList<>();
}

//HandleErrorsService
//
//AppInternalMessagesService
//
//do wywalenia ?
