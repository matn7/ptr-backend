package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.enums.RateDayEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DaysServiceTest {

    @Mock
    DaysRepository daysRepository;

    @Mock
    DaysMapper daysMapper;

    @InjectMocks
    DaysService daysService;

    @Test
    public void findById() {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();
        DaysEntity daysEntity = new DaysEntity();

        when(daysRepository.findById(any(), anyLong()))
                .thenReturn(Optional.of(daysEntity));

        when(daysMapper.daysToDaysDTO(daysEntity)).thenReturn(day);

        final DaysDTO daysDTO = daysService.findById(user.getId(), 1L);

        assertEquals(1L, daysDTO.getId());
        assertEquals("Some Day", daysDTO.getBody());
        assertEquals(LocalDate.of(2025, 5, 25), daysDTO.getStartDate());
        assertEquals(RateDayEnum.VERY_GOOD, daysDTO.getRateDay());
    }

    @Test
    public void findByPartDate() {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();
        DaysEntity daysEntity1 = new DaysEntity();
        DaysEntity daysEntity2 = new DaysEntity();
        List<DaysEntity> daysEntityList = new ArrayList<>();
        daysEntityList.add(daysEntity1);
        daysEntityList.add(daysEntity2);

        when(daysRepository.findByPartDate(any(), anyInt(), anyInt()))
                .thenReturn(daysEntityList);

        when(daysMapper.daysToDaysDTO(daysEntity1)).thenReturn(day);

        final List<DaysDTO> daysDTO = daysService.findByDate(user.getId(), 2025, 5);

        assertEquals(1L, daysDTO.get(0).getId());
        assertEquals("Some Day", daysDTO.get(0).getBody());
        assertEquals(LocalDate.of(2025, 5, 25), daysDTO.get(0).getStartDate());
        assertEquals(RateDayEnum.VERY_GOOD, daysDTO.get(0).getRateDay());
    }

    @Test
    public void findByDate() {
        UserEntity user = UserEntity.builder().build();
        DaysDTO day = getDaysDTO();
        DaysEntity daysEntity = getDaysEntity();

        when(daysRepository.save(daysEntity))
                .thenReturn(daysEntity);
        when(daysMapper.daysToDaysDTO(daysEntity)).thenReturn(day);

        final DaysDTO daysDTO = daysService.save(day);

        assertEquals(1L, daysDTO.getId());
        assertEquals("Some Day", daysDTO.getBody());
        assertEquals(LocalDate.of(2025, 5, 25), daysDTO.getStartDate());
        assertEquals(RateDayEnum.VERY_GOOD, daysDTO.getRateDay());
    }

    @Test
    public void save() {
        UserEntity user = UserEntity.builder().build();
    }


    private DaysDTO getDaysDTO() {
        DaysDTO day = new DaysDTO();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(2025, 5, 25));
        day.setPostedOn(LocalDateTime.now());
        day.setRateDay(RateDayEnum.VERY_GOOD);
        return day;
    }

    private DaysEntity getDaysEntity() {
        DaysEntity day = new DaysEntity();
        day.setId(1L);
        day.setBody("Some Day");
        day.setStartDate(LocalDate.of(2025, 5, 25));
        day.setPostedOn(LocalDateTime.now());
        day.setRateDay(RateDayEnum.VERY_GOOD);
        return day;
    }
}
