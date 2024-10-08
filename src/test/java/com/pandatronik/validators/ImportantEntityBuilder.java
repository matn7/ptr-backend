package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.enums.MadeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

// TODO: to delete change with lombock builder
public class ImportantEntityBuilder {
    ImportantEntity importantEntity = new ImportantEntity();

    public static ImportantEntityBuilder defaultImportantEntity() {
        return new ImportantEntityBuilder();
    }

    public ImportantEntityBuilder withTitle(String title) {
        importantEntity.setTitle(title);
        return this;
    }

    public ImportantEntityBuilder withBody(String body) {
        importantEntity.setBody(body);
        return this;
    }

    public ImportantEntityBuilder withMade(MadeEnum made) {
        importantEntity.setMade(made);
        return this;
    }

    public ImportantEntityBuilder withPostedOn(LocalDateTime postedOn) {
        importantEntity.setPostedOn(postedOn);
        return this;
    }

    public ImportantEntityBuilder withStartDate(LocalDate startDate) {
        importantEntity.setStartDate(startDate);
        return this;
    }


    public ImportantEntity build() {
        return importantEntity;
    }

}
