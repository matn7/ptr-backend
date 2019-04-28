package com.pandatronik.backend.persistence.domain.core;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pandatronik.backend.persistence.domain.UserEntity;
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
@Table(name = "Extraordinary")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExtraordinaryEntity implements Serializable {

    private static final long serialVersionUID = 6121746520492498519L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @Size(min = 1, max = 255)
    private String body;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity userEntity;

    public ExtraordinaryEntity() {
    }

    public ExtraordinaryEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("body") String body,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public ExtraordinaryEntity(
            @JsonProperty("title") String title,
            @JsonProperty("body") String body,
            @JsonProperty("postedOn") LocalDateTime postedOn,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("userProfileId") String userProfileId) {
        this.title = title;
        this.body = body;
        this.postedOn = postedOn;
        this.startDate = startDate;
        this.userProfileId = userProfileId;
    }

    public static ExtraordinaryEntity newExtraordinary(ExtraordinaryEntity extraordinaryEntity) {
        return new ExtraordinaryEntity(extraordinaryEntity.getTitle(),
                extraordinaryEntity.getBody(), extraordinaryEntity.getPostedOn(), extraordinaryEntity.getStartDate(),
                extraordinaryEntity.getUserProfileId());
    }

    public static ExtraordinaryEntity newExtraordinary(String userProfileId, ExtraordinaryEntity extraordinaryEntity) {
        return new ExtraordinaryEntity(extraordinaryEntity.getTitle(),
                extraordinaryEntity.getBody(), extraordinaryEntity.getPostedOn(), extraordinaryEntity.getStartDate(), userProfileId);
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}