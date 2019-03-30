package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import com.pandatronik.backend.persistence.domain.Role;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import com.pandatronik.utils.UserUtils;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PandatronikRestApplication.class)
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    // todo: delete
    RandomString randomString = new RandomString(6);


    @Rule
    public TestName testName = new TestName();

    @Test
    public void testCreateNewUser() throws Exception {
        UserEntity user = createUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
    }

}
