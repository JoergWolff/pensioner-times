package de.wolffclan.backend.services.place;

import de.wolffclan.backend.models.place.NewPlace;
import de.wolffclan.backend.models.place.Place;
import de.wolffclan.backend.repositories.place.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Place savePlace(NewPlace newPlace) {
        if(newPlace.isValid()){
            Place savePlace = new Place(
                    UUID.randomUUID().toString(),
                    newPlace.town(),
                    newPlace.postalCode(),
                    newPlace.street(),
                    newPlace.description()
            );
            return placeRepository.save(savePlace);
        }
        throw new NoSuchElementException("Too few inputs...");
    }

    public Place getPlaceById(String id){
        return placeRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException(idNotFound(id)));
    }

    public Place updatePlace(String id, Place place){
        if(existPlaceById(id)){
            Place updatedPlace = new Place(
                    id,
                    place.town(),
                    place.postalCode(),
                    place.street(),
                    place.description()
            );
            placeRepository.save(updatedPlace);
            return updatedPlace;
        }
        throw new NoSuchElementException(idNotFound(id));
    }

    public Place deletePlace(String id){
        if(existPlaceById(id)){
            Place deletedPlace = getPlaceById(id);
            placeRepository.deleteById(id);
            return deletedPlace;
        }
        throw new NoSuchElementException(idNotFound(id));
    }

    private String idNotFound(String id){
        return "Place with id: " + id + " not found...";
    }
    private boolean existPlaceById(String id){return  placeRepository.existsById(id);}
}
