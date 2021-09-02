package com.pandatronik.backend.persistence.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pandatronik.enums.RateDayEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Days")
@Cacheable(false)
//@Builder
@NoArgsConstructor
//@AllArgsConstructor
@Data
public class DaysEntity implements Serializable {

    private static final long serialVersionUID = -7331168481516668701L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String body;

    @NotNull
    private RateDayEnum rateDay;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime postedOn;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    private long userEntityId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private UserEntity userEntity;

}
