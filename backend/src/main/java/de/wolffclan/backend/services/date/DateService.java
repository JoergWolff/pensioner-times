package de.wolffclan.backend.services.date;

import de.wolffclan.backend.models.date.Date;
import de.wolffclan.backend.models.date.NewDate;
import de.wolffclan.backend.repositories.date.DateRepository;
import de.wolffclan.backend.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DateService {
    private final DateRepository dateRepository;
    private final UserRepository userRepository;


    public DateService(DateRepository dateRepository, UserRepository userRepository) {
        this.dateRepository = dateRepository;
        this.userRepository = userRepository;
    }

    public List<Date> getAllDates(){return dateRepository.findAll();}

    public Date saveDate(NewDate newDate) {
        if (newDate.isValid()) {
            String userId = newDate.userIds().get(0);
            if (existsUserById(userId)) {
                Date savedDate = new Date(
                        UUID.randomUUID().toString(),
                        newDate.meetingDay(),
                        newDate.meetingTime(),
                        newDate.description(),
                        1,
                        1,
                        newDate.userIds(),
                        newDate.placeId()
                );
                return dateRepository.save(savedDate);

            }
            throw new NoSuchElementException("User with id: " + userId + " not found...");
        }
        throw new NoSuchElementException("Too few inputs...");
    }

    public Date getDateById(String id) {
        return dateRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Date with id: " + id + " not found..."));
    }

    public Date updateDate(String id, Date date) {
        if (existDateById(id)) {
            Date existDate = getDateById(id);
            List<String> userIds = date.userIds().stream()
                    .filter(this::existsUserById)
                    .toList();
            int counter = userIds.size();
            if (counter <= date.maxUser()) {
                Date savingDate = new Date(
                        existDate.id(),
                        date.meetingDay(),
                        date.meetingTime(),
                        date.description(),
                        date.maxUser(),
                        counter,
                        userIds,
                        date.placeId()
                );
                dateRepository.save(savingDate);
                return savingDate;
            }
            throw new NoSuchElementException("Too much Users. Maximize the Users to " + counter + ".");

        }
        throw new NoSuchElementException("Date id dosen't exists...");
    }

    public Date deleteDate(String id) {
        if (existDateById(id)) {
            Date deletedDate = getDateById(id);
            dateRepository.deleteById(id);
            return deletedDate;
        }
        throw new NoSuchElementException("Date id dosen't exists...");
    }

    private boolean existDateById(String id) {
        return dateRepository.existsById(id);
    }

    private boolean existsUserById(String id) {
        return userRepository.existsById(id);
    }
}
