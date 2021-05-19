package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = PandatronikRestApplication.class)
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    // todo: delete
    RandomString randomString = new RandomString(6);


//    @Rule
//    public TestName testName = new TestName();
//
//    @Test
//    public void testCreateNewUser() throws Exception {
//        UserEntity user = createUser(testName);
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(user.getId());
//    }

}
