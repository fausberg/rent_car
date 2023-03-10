package rent_car.rent_car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rent_car.rent_car.domain.Fine;
import rent_car.rent_car.repository.FineRepository;

import java.util.ArrayList;

@Service
public class FineService {

    public FineRepository fineRepository;

    @Autowired
    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public Fine getFineById(int id) {
        return fineRepository.findById(id).get();
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

    public void deleteFine(Fine fine) {
        fineRepository.delete(fine);
    }
}
