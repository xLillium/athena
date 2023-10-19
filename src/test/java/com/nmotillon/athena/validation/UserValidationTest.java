package com.nmotillon.athena.validation;

import com.nmotillon.athena.model.User;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserValidationTest {
    @Autowired
    private LocalValidatorFactoryBean validator;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John", "Doe");
    }

    @Test
    public void whenValidData_thenNoViolations() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNullFirstName_thenViolation() {
        user.setFirstName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("First name is mandatory"); // Assuming this is the validation message
    }

    @Test
    public void whenNullLastName_thenViolation() {
        user.setLastName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Last name is mandatory"); // Assuming this is the validation message
    }

    // TODO: Validate lengths
}
