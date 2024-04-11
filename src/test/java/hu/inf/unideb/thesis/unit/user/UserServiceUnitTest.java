package hu.inf.unideb.thesis.unit.user;

import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("unit")
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp(){
        roleService.setUpMockedData();
        userService.setUpMockedData();
    }

    @Test
    public void testGetUsersByRoleWithPagination() {

        Assertions.assertEquals(userService.findByName("admin").getId(),userService.findByRole("BACKOFFICE", Pageable.ofSize(1)).stream().findFirst().get().getId());

    }

    @Test
    public void testDeleteUser(){
        User test = new User();

        test.setUsername("testUser");

        userService.save(test);

        Assertions.assertEquals(3,userService.findAll().size());

        userService.delete(userService.findByName("testUser"));

        Assertions.assertEquals(2,userService.findAll().size());
    }
    @Test
    public void testUpdateUser(){

        User temp = userService.findByName("user");

        temp.setUsername("newUsername");

        userService.update(1L,temp);

        Assertions.assertEquals(temp.getId().longValue(),userService.findByName("newUsername").getId().longValue());
        Assertions.assertNull(userService.findByName("user"));

    }

    @Test
    public void testSettingUpMockedData(){

        userService.setUpMockedData();

        Assertions.assertEquals(2, userService.findAll().size());
    }

}
