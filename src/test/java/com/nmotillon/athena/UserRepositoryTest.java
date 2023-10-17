package com.nmotillon.athena;

import com.nmotillon.athena.model.User;
import com.nmotillon.athena.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        userRepository.save(user);
        User retrievedUser = userRepository.findById(user.getId()).orElse(null);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getFirstName()).isEqualTo("John");
        assertThat(retrievedUser.getLastName()).isEqualTo("Doe");
    }
}
