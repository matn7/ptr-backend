package com.pandatronik.validators;

import com.pandatronik.enums.MadeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.leftPad;

public class ImportantEntityProvider {

    public static ImportantEntityBuilder getValidImportantEntity() {
        ImportantEntityBuilder importantEntityBuilder = ImportantEntityBuilder.defaultImportantEntity()
                .withTitle(leftPad("a", 40, "a"))
                .withBody("Super")
                .withMade(MadeEnum.SEVENTY_FIVE)
                .withPostedOn(LocalDateTime.now())
                .withStartDate(LocalDate.now());

        return importantEntityBuilder;
    }
}
