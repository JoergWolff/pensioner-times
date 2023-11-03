package de.wolffclan.backend.controllers.place;

import de.wolffclan.backend.models.place.Place;
import de.wolffclan.backend.repositories.place.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlaceControllerTest {

    String apiString = "/api/places";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PlaceRepository placeRepository;

    @Test
    void getAllPlaces_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get(apiString))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                []
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getAllPlaces_ReturnsArrayOfPlaces() throws Exception {
        Place place = createTestPlace();
        placeRepository.save(place);

        mockMvc.perform(MockMvcRequestBuilders.get(apiString))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{
                                "id": "123",
                                "town": "Test",
                                "postalCode": "12345",
                                "street": "TestStreet 123",
                                "description": "Very testy"
                                }]
                                """
                ));
    }

    @Test
    void postPlace_ReturnsPlace() throws Exception {
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                        "town": "Test",
                                        "postalCode": "12345",
                                        "street": "TestStreet 123",
                                        "description": "Very testy"
                                        }
                                        """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "town": "Test",
                                    "postalCode": "12345",
                                    "street": "TestStreet 123",
                                    "description": "Very testy"
                                    }
                                """
                ))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void postPlace_ReturnsTooFewInputs() throws Exception {
        // GIVEN
        Place place = createTestPlace();
        placeRepository.save(place);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                                        "tow*****n": "Test",
                                                        "postalCode": "12345",
                                                        "street": "TestStreet 123",
                                                        "description": "Very testy"
                                                        }
                                        """
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Too few inputs..."));
    }

    @Test
    @DirtiesContext
    void getPlaceById_ReturnsOnePlace() throws Exception {
        // GIVEN
        Place place = createTestPlace();
        placeRepository.save(place);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + place.id()))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "id": "123",
                                "town": "Test",
                                "postalCode": "12345",
                                "street": "TestStreet 123",
                                "description": "Very testy"
                                }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getPlaceById_ReturnsNotFounded() throws Exception {
        // GIVEN
        Place place = createTestPlace();
        placeRepository.save(place);
        String falseId = "1234";
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + falseId))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Place with id: " + falseId + " not found..."));
    }

    @Test
    @DirtiesContext
    void putPlace_ReturnsUpdatedPlace() throws Exception {
        // GIVEN
        Place place = createTestPlace();
        placeRepository.save(place);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.put(apiString + "/update/" + place.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                                "town": "TestUpdate",
                                                "postalCode": "12345",
                                                "street": "TestStreet 123",
                                                "description": "Very testy"
                                                }
                                        """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "town": "TestUpdate",
                                "postalCode": "12345",
                                "street": "TestStreet 123",
                                "description": "Very testy"
                                }
                                """
                ))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void putPlace_ReturnsNoPlace() throws Exception {
        // GIVEN
        Place place = createTestPlace();
        placeRepository.save(place);
        String falseId = "123456";
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.put(apiString + "/update/" + falseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                              "town": "TestUpdate",
                                              "postalCode": "12345",
                                              "street": "TestStreet 123",
                                              "description": "Very testy"
                                              }
                                        """
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Place with id: " + falseId + " not found..."));
    }

    @Test
    @DirtiesContext
    void deletePlace_ReturnsDeletedUser() throws Exception {
        // GIVEN
        Place deletingPlace = createTestPlace();
        placeRepository.save(deletingPlace);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete(apiString + "/delete/" + deletingPlace.id())
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deletingPlace.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.town").value(deletingPlace.town()));
    }

    @Test
    void deletePlace_ReturnsNoUser() throws Exception {
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete(apiString + "/delete/***")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Place with id: *** not found..."));
    }

    private Place createTestPlace() {
        return new Place(
                "123",
                "Test",
                "12345",
                "TestStreet 123",
                "Very testy"

        );
    }
}