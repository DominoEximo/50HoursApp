package hu.inf.unideb.thesis.unit.role;

import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.service.RoleService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
public class RoleServiceUnitTest {

    @Autowired
    RoleService roleService;

    @BeforeEach
    public void setUp(){
        roleService.setUpMockedData();
    }

    @Test
    public void testGetRoles(){

        Assertions.assertEquals(2,roleService.findAll().size());

    }
    @Test
    public void testDeleteRole(){

        roleService.save(new Role("test"));

        Assertions.assertEquals(3,roleService.findAll().size());

        roleService.delete(roleService.findByName("test"));

        Assertions.assertEquals(2,roleService.findAll().size());

    }

    @Test
    public void testUpdateRole(){

        Role temp = roleService.findByName("USER");

        temp.setName("NEWUSER");

        roleService.update(2L,temp);

        Assertions.assertEquals("NEWUSER",roleService.findById(2L).getName());

    }

    @Test
    public void testSettingUpMockedData(){

        roleService.setUpMockedData();

        Assertions.assertEquals(2,roleService.findAll().size());
    }
}
