package com.pandatronik.backend.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ImportantIndexListDTO {
    List<IndexDTO> importantIndexList;
}
