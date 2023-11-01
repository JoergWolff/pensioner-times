package de.wolffclan.backend.services.hobby;

import de.wolffclan.backend.models.hobby.HobbyCollector;
import de.wolffclan.backend.repositories.hobby.HobbyCollectorRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HobbyCollectorServiceTest {
    HobbyCollectorRepository hobbyCollectorRepository = mock(HobbyCollectorRepository.class);
    HobbyCollectorService hobbyCollectorService = new HobbyCollectorService(hobbyCollectorRepository);

    @Test
    void getAllHobbyCollectors() {
        // GIVEN
        HobbyCollector hobbyCollector = new HobbyCollector("1", "Test", 1);
        List<HobbyCollector> expected = new ArrayList<>(List.of(hobbyCollector));
        // WHEN
        when(hobbyCollectorRepository.findAll()).thenReturn(expected);
        List<HobbyCollector> actual = hobbyCollectorService.getAllHobbyCollectors();
        // THEN
        verify(hobbyCollectorRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void saveHobbyCollector_ReturnsNothing() {
        // GIVEN
        HobbyCollector hobbyCollector = new HobbyCollector("1", "Test", 1);
        when(hobbyCollectorRepository.save(any(HobbyCollector.class))).thenReturn(
                new HobbyCollector(
                        hobbyCollector.id(),
                        hobbyCollector.name(),
                        hobbyCollector.counter()
                )
        );
        // WHEN
        hobbyCollectorService.saveHobbyCollector("Test");
        // THEN
        verify(hobbyCollectorRepository, times(1)).save(any(HobbyCollector.class));
    }
}