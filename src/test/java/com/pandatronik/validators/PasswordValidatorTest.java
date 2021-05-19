package com.pandatronik.validators;

import com.pandatronik.configuration.ValidatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.validation.Validator;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {ValidatorConfiguration.class})
public class PasswordValidatorTest {

    @Autowired
    private Validator validator;

//    @Test
//    public void testValidPassword() {
//        UserEntity userEntity = getUserEntity(VALID_PASSWORD);
//
//        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
//
//        assertEquals(0, violations.size());
//    }
//
//    @Test
//    public void testInvalidPasswordNoNumbers() {
//        UserEntity userEntity = getUserEntity("InvalidP@ssword");
//
//        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
//
//        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//        assertThat(collect).hasSize(1);
//        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
//    }
//
//    @Test
//    public void testInvalidPasswordNoSpecialChars() {
//        UserEntity userEntity = getUserEntity("Inva1idPassw0rd");
//
//        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
//
//        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//        assertThat(collect).hasSize(1);
//        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
//    }
//
//    @Test
//    public void testInvalidPasswordNoUppercase() {
//        UserEntity userEntity = getUserEntity("inva1idp@ssw0rd");
//
//        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
//
//        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//        assertThat(collect).hasSize(1);
//        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
//    }
//
//
//    @Test
//    public void testInvalidPasswordNoLowercase() {
//        UserEntity userEntity = getUserEntity("INVALIDP@SSW0RD");
//
//        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
//
//        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//        assertThat(collect).hasSize(1);
//        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
//    }
//
//    private UserEntity getUserEntity(String password) {
//
//        Set<UserRole> roles = new HashSet<>();
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(VALID_USERNAME)
//                .password(password)
//                .email(VALID_EMAIL)
//                .firstName("Panda")
//                .lastName("Panda")
//                .plan(new Plan(PlansEnum.BASIC))
//                .build();
//
//        userEntity.setUserRoles(roles);
//
//        return userEntity;
//    }

}
