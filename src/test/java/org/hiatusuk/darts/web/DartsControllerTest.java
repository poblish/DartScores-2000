package org.hiatusuk.darts.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DartsController.class)
class DartsControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void showsResults() throws Exception {
        mvc.perform(get("/").param("total", "170").param("darts_left", "3").param("exactly_n", "Yes"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("You can reach 170 with 3 darts")));
    }

    @Test
    void impossibleScore() throws Exception {
        mvc.perform(get("/").param("total", "999"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Sorry, you cannot score 999")));
    }
}
