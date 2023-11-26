package com.bankets.bankets;

import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.PersonRepository;
import com.bankets.bankets.repo.RolesRepository;
import com.bankets.bankets.repo.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DataBaseTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void addToDatabase() throws Exception {
        try{
            Person person = new Person("+375 (29) 123-45-67", "email@mail.ru ", "Ivan Ivanov");
            personRepository.save(person);

            Long role_id = rolesRepository.findByPosition("Пользователь").orElseThrow().getRole_id();

            Users users = new Users(role_id,person.getPerson_id(), "IvanIvanov", "123qwerty123");
            usersRepository.save(users);
        }
        catch (Exception e){
            e.printStackTrace();
            fail("Тест провален");
        }
    }


    @Test
    public void changeUserRecord() throws Exception {
        Users users = usersRepository.findByLogin("IvanIvanov").orElseThrow();
        users.setLogin("IvanIvanov");
        users.setPassword("newPassword");
        users.setRole_id(2L);

        Person person = personRepository.findById(users.getPerson_id()).orElseThrow();
        person.setName("new Name");
        person.setEmail("new Email");
        person.setPhone("new Phone");
        personRepository.save(person);

        usersRepository.save(users);
    }

    @Test
    public void readFromDatabase() throws Exception {
        Users user = usersRepository.findByLogin("IvanIvanov").orElseThrow();
        assertEquals("IvanIvanov", user.getLogin());
        assertEquals("newPassword", user.getPassword());

        Person person = personRepository.findById(user.getPerson_id()).orElseThrow();
        assertEquals("new Name", person.getName());
        assertEquals("new Email", person.getEmail());
        assertEquals("new Phone", person.getPhone());

    }

    @Test
    public void deleteFromDatabase() throws Exception {
        try{
            Users user = usersRepository.findByLogin("IvanIvanov").orElseThrow();
            long person_id = user.getPerson_id();

            usersRepository.delete(user);

            Person person = personRepository.findById(person_id).orElseThrow();
            personRepository.delete(person);
        }
        catch (Exception e){
            e.printStackTrace();
            fail("Тест провален");
        }
    }
}
