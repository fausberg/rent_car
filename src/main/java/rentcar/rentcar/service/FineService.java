package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.FineException;
import rentcar.rentcar.repository.FineRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class FineService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final FineRepository fineRepository;

    private final UserService userService;

    @Autowired
    public FineService(FineRepository fineRepository, UserService userService) {
        this.fineRepository = fineRepository;
        this.userService = userService;
    }

    public Fine getFineById(int id) {
        try {
            Fine fine = fineRepository.findById(id).get();
            if(fine == null) {
                throw new NoSuchElementException();
            } else {
                return fine;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new Fine();
    }

    public ArrayList<Fine> getAllFine() {
        return (ArrayList<Fine>) fineRepository.findAll();
    }

    public void createFine(Fine fine) {
        fineRepository.save(fine);
    }

    public void updateFine(Fine fine) {
        fineRepository.saveAndFlush(fine);
    }

    public void deleteFine(int id) {
        try {
            User user = userService.getUserByLogin();
            ArrayList<Fine> fines = fineRepository.getFinesByUserId(user.getId());
            boolean hasFine = false;
            for (Fine fine : fines) {
                if (fine.getId() == id) {
                    fineRepository.deleteById(fine.getId());
                    hasFine = true;
                }
            }
            if (!hasFine) {
                throw new FineException("You do not have such a fine");
            }
        } catch (FineException e) {
            log.error(e.getMessage());
        }
    }
}
