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
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainPageStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/banquet-halls"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFavoritePageStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/favorite"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testMyOrdersPageStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/my-orders"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAboutPageStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
