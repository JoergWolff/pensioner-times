package de.wolffclan.backend.controllers.hobby;

import de.wolffclan.backend.models.hobby.HobbyCollector;
import de.wolffclan.backend.repositories.hobby.HobbyCollectorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HobbyCollectorControllerTest {

    String apiString = "/api/hobby-collectors";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    HobbyCollectorRepository hobbyCollectorRepository;
    @Test
    void getAllHobbyCollectors_ReturnsEmptyArray() throws Exception {

        mockMvc.perform(get(apiString))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                []
                                """
                ));
    }

    @Test
    void getAllHobbyCollectors_ReturnsArrayOfUser() throws Exception {

        HobbyCollector hobbyCollector = new HobbyCollector("1", "Test", 1);
        hobbyCollectorRepository.save(hobbyCollector);

        mockMvc.perform(get(apiString))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{
                                "id": "1",
                                "name": "Test",
                                "counter": 1
                                }]
                                """
                ));
    }
}