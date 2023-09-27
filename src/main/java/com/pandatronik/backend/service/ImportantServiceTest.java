package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.mapper.ImportantMapperTest;
import com.pandatronik.backend.persistence.model.ImportantDTOTest;
import com.pandatronik.backend.persistence.repositories.ImportantRepositoryTest;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

//@Service
public class ImportantServiceTest extends ResourceService<ImportantRepositoryTest, ImportantMapperTest, ImportantDTOTest> {

    public ImportantServiceTest(UserService userService, ImportantRepositoryTest importantRepository, ImportantMapperTest mapper) {
//        super(userService, importantRepository, mapper);
    }

}
