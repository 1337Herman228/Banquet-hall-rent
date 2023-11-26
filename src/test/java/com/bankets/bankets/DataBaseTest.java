package com.bankets.bankets;
import com.bankets.bankets.controllers.MainController;
import com.bankets.bankets.repo.PersonRepository;
import com.bankets.bankets.repo.RolesRepository;
import com.bankets.bankets.repo.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.aspectj.bridge.MessageUtil.fail;


public class DataBaseTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testMainPageStatus() throws Exception {
        try{
            //БД
        }
        catch (Exception e){
            e.printStackTrace();
            fail("Тест провален");
        }
    }

}
