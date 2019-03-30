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
@Table(name = "Days")
@Getter
@Setter
@Cacheable(false)
@ToString
@EqualsAndHashCode
public class DaysEntity implements Serializable {

    private static final long serialVersionUID = -7331168481516668701L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String body;

    @NotNull
    private Integer rateDay;

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

    public DaysEntity() {
    }

    public DaysEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("body") String body,
            @JsonProperty("rateDay") Integer rateDay,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.id = id;
        this.body = body;
        this.rateDay = rateDay;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public DaysEntity(
            @JsonProperty("body") String body,
            @JsonProperty("rateDay") Integer rateDay,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.body = body;
        this.rateDay = rateDay;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public static DaysEntity newDay(DaysEntity daysEntity) {
        return new DaysEntity(daysEntity.getBody(), daysEntity.getRateDay(), daysEntity.getPostedOn(), daysEntity.getStartDate(),
                daysEntity.getUserProfileId());
    }

    public static DaysEntity newDay(String userProfileId, DaysEntity daysEntity) {
        return new DaysEntity(daysEntity.getBody(), daysEntity.getRateDay(), daysEntity.getPostedOn(), daysEntity.getStartDate(), userProfileId);
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Integer getRateDay() {
        return rateDay;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

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
