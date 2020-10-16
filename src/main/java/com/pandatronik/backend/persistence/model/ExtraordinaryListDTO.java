package com.pandatronik.backend.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraordinaryListDTO {
    List<ExtraordinaryDTO> extraordinaryList;
}
