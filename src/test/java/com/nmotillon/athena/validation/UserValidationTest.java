package com.nmotillon.athena.validation;

import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserValidationTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void savingUserWithNullFirstNameShouldThrowValidationException() {
        User user = new User(null, "Doe");
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }

    @Test
    public void savingUserWithNullLastNameShouldThrowValidationException() {
        User user = new User("John", null);
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }
}
