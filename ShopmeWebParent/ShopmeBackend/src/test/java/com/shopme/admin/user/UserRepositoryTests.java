package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role rolaAdmin = entityManager.find(Role.class, 1);
        User userMcarhuas = new User("mikelbon.ct@gmail.com", "123456", "Mikel", "Carhuas");
        userMcarhuas.addRole(rolaAdmin);

        User savedUser = repo.save(userMcarhuas);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
        User userBencarhuas = new User("bencarhuas@gmail.com", "123456", "Benjamin", "Carhuas");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(4);
        userBencarhuas.addRole(roleEditor);
        userBencarhuas.addRole(roleAssistant);

        User savedUser = repo.save(userBencarhuas);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userMcarhuas = repo.findById(3).get();
        System.out.println(userMcarhuas);
        assertThat(userMcarhuas).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userMcarhuas = repo.findById(1).get();
        userMcarhuas.setEnabled(true);
        userMcarhuas.setEmail("mikel_ct@hotmail.com");
        repo.save(userMcarhuas);
    }
    @Test
    public void testUpdateUserRoles() {
        User userBencarhuas = repo.findById(3).get();
        Role roleShipper = new Role(3);
        Role roleEditor = new Role(2);

        userBencarhuas.getRoles().remove(roleShipper);
        userBencarhuas.addRole(roleEditor);
        repo.save(userBencarhuas);
    }
    @Test
    public void testDeleteUser() {
        Integer userId = 3;
        repo.deleteById(userId);


    }
}
