package com.pandatronik.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pandatronik.enums.MadeEnum;
import com.pandatronik.validator.Important3EntityUnique;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Important3EntityUnique(message = "{task.duplicate.entry.message}")
public class Important3DTO implements Comparable<Important3DTO> {

    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private MadeEnum made;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime postedOn;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @JsonIgnore
    private long userEntityId;

    @Override
    public int compareTo(Important3DTO important3DTO) {
        return this.startDate.compareTo(important3DTO.startDate);
    }
}
