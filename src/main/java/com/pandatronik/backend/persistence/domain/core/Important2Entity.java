package com.pandatronik.backend.persistence.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.enums.MadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Important2")
@Cacheable(false)
@NoArgsConstructor
@Data
public class Important2Entity implements Serializable {

    private static final long serialVersionUID = -4757677094121428220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String body;

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

    @ManyToOne
    @JsonIgnore
    private UserEntity userId;

}
