package com.bankets.bankets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class FormPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("login", "321")
                        .param("password", "321"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/banquet-halls"));
    }

    @Test
    public void testRegForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/reg")
                        .param("login", "John Doe")
                        .param("password", "123456")
                        .param("fullName", "Жмышенко Валерий Альбертович")
                        .param("phone", "+375 (29) 333-44-44")
                        .param("email", "email@mail.ru"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/reg"));
    }
}
