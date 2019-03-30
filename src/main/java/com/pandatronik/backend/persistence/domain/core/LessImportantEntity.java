package com.pandatronik.backend.persistence.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "LessImportant")
@Getter
@Setter
@ToString
@Cacheable(false)
@EqualsAndHashCode
public class LessImportantEntity implements Serializable {

    private static final long serialVersionUID = 3076488284617061523L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @Size(min = 1, max = 255)
    private String body;

    @NotNull
    private Integer made;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime postedOn;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "user_profile_id")
    private String userProfileId;

    public LessImportantEntity() {
    }

    public LessImportantEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("body") String body,
            @JsonProperty("made") Integer made,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.made = made;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public LessImportantEntity(
            @JsonProperty("title") String title,
            @JsonProperty("body") String body,
            @JsonProperty("made") Integer made,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.title = title;
        this.body = body;
        this.made = made;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public static LessImportantEntity newLessImportantRecord(LessImportantEntity lessImportantEntity) {
        return new LessImportantEntity(lessImportantEntity.getId(), lessImportantEntity.getTitle(),
                lessImportantEntity.getBody(), lessImportantEntity.getMade(),
                lessImportantEntity.getPostedOn(), lessImportantEntity.getStartDate(), lessImportantEntity.getUserProfileId());
    }

    public static LessImportantEntity newLessImportantRecord(String userProfileId, LessImportantEntity lessImportantEntity) {
        return new LessImportantEntity(lessImportantEntity.getTitle(), lessImportantEntity.getBody(), lessImportantEntity.getMade(),
                lessImportantEntity.getPostedOn(), lessImportantEntity.getStartDate(), userProfileId);
    }

    public Long getId() {
        return id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public String getBody() {
        return body;
    }

    @NotNull
    public Integer getMade() {
        return made;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @NotNull
    public String getUserProfileId() {
        return userProfileId;
    }

    public void setPostedOn(LocalDateTime postedOn) {
        this.postedOn = postedOn;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
