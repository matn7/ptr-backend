package com.pandatronik.backend.persistence.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtraordinaryDTO extends CommonDTO {

    @NotNull
    @NotBlank(message = "Title must not be blank")
    @Size(min = 1, max = 40, message = "Title size must be between 1 and 40")
    private String title;
}
