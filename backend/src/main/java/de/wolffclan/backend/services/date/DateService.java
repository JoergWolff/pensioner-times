package de.wolffclan.backend.services.date;

import de.wolffclan.backend.models.date.Date;
import de.wolffclan.backend.repositories.date.DateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateService {
    private final DateRepository dateRepository;


    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public List<Date> getAllDates(){return dateRepository.findAll();}
}
