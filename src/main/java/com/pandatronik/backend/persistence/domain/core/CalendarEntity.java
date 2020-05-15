package com.pandatronik.backend.persistence.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "calendar_entity")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Cacheable(false)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CalendarEntity implements Serializable {

    private static final long serialVersionUID = -5645734198922663674L;

    @Id
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/M/dd")
    @Column(unique = true)
    private Calendar calendarDate;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private ExtraordinaryEntity extraordinary;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private ImportantEntity important;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private ImportantEntity2 important2;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private ImportantEntity3 important3;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private LessImportantEntity lessImportant;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private LessImportantEntity2 lessImportant2;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private LessImportantEntity3 lessImportant3;

    @OneToOne
    @JoinColumn(name = "calendarDate", referencedColumnName="startDate", insertable = false, updatable = false)
    private DaysEntity days;

}