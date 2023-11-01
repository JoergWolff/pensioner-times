package de.wolffclan.backend.services.hobby;

import de.wolffclan.backend.models.hobby.HobbyCollector;
import de.wolffclan.backend.repositories.hobby.HobbyCollectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HobbyCollectorService {

    private final HobbyCollectorRepository hobbyCollectorRepository;

    public HobbyCollectorService(HobbyCollectorRepository hobbyCollectorRepository) {
        this.hobbyCollectorRepository = hobbyCollectorRepository;
    }

    public List<HobbyCollector> getAllHobbyCollectors(){return hobbyCollectorRepository.findAll();}

    public void saveHobbyCollector(String name){
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        long counter = 0;
        HobbyCollector hobbyCollector;
        if(!exitsHobbyCollector(name)){
            counter += 1;
            hobbyCollector = new HobbyCollector(UUID.randomUUID().toString(),name,counter);

        }else{
            HobbyCollector oldHobbyCollector = getHobbyCollectorByName(name);
            hobbyCollector = new HobbyCollector(oldHobbyCollector.id(), oldHobbyCollector.name(), oldHobbyCollector.counter()+1);
        }
        hobbyCollectorRepository.save(hobbyCollector);
    }

    private boolean exitsHobbyCollector(String name){
        return hobbyCollectorRepository.existsByName(name);
    }
    private HobbyCollector getHobbyCollectorByName(String name){
        return hobbyCollectorRepository.getByName(name);
    }
}
