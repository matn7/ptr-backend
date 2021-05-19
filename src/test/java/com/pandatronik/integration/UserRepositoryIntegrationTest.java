package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = PandatronikRestApplication.class)
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {



//    @Rule public TestName testName = new TestName();
//
//
//    @Before
//    public void init() {
//        Assert.assertNotNull(planRepository);
//        Assert.assertNotNull(roleRepository);
//        Assert.assertNotNull(userRepository);
//    }
//
//    @Test
//    public void testCreateNewPlan() throws Exception {
//        Plan basicPlan = createPlan(PlansEnum.BASIC);
//        planRepository.save(basicPlan);
//        Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).get();
//        Assert.assertNotNull(retrievedPlan);
//    }
//
//    @Test
//    public void testCreateNewRole() throws Exception {
//
//        Role userRole  = createRole(RolesEnum.BASIC);
//        roleRepository.save(userRole);
//
//        Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).get();
//        Assert.assertNotNull(retrievedRole);
//    }
//
//    @Test
//    public void createNewUser() throws Exception {
//
//        String username = testName.getMethodName();
//        String email = testName.getMethodName() + "@devopsbuddy.com";
//
//        UserEntity basicUser = createUser(username, email);
//
//        UserEntity newlyCreatedUser = userRepository.findById(basicUser.getId()).get();
//        Assert.assertNotNull(newlyCreatedUser);
//        Assert.assertTrue(newlyCreatedUser.getId() != 0);
//        Assert.assertNotNull(newlyCreatedUser.getPlan());
//        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
//        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
//        for (UserRole ur : newlyCreatedUserUserRoles) {
//            Assert.assertNotNull(ur.getRole());
//            Assert.assertNotNull(ur.getRole().getId());
//        }
//
//    }
//
//    @Test
//    public void testDeleteUser() throws Exception {
//
//        String username = testName.getMethodName();
//        String email = testName.getMethodName() + "@devopsbuddy.com";
//
//        UserEntity basicUser = createUser(username, email);
//        userRepository.deleteById(basicUser.getId());
//    }
//
//    @Test
//    public void testGetUserByEmail() throws Exception {
//        UserEntity user = createUser(testName);
//
//        UserEntity newlyFoundUser = userRepository.findByEmail(user.getEmail());
//        Assert.assertNotNull(newlyFoundUser);
//        Assert.assertNotNull(newlyFoundUser.getId());
//    }
//
//    @Test
//    public void testUpdateUserPassword() throws Exception {
//        UserEntity user = createUser(testName);
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(user.getId());
//
//        String newPassword = UUID.randomUUID().toString();
//
//        userRepository.updateUserPassword(user.getId(), newPassword);
//
//        user = userRepository.findById(user.getId()).get();
//        Assert.assertEquals(newPassword, user.getPassword());
//
//    }


}
