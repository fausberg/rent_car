package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.DTO.FineDTO;
import rentcar.rentcar.domain.Fine;

@Component
public class FineMapper {

   public Fine mapFineDTOtoFine(FineDTO fineDTO) {
       Fine fine = new Fine();
       fine.setDescription(fineDTO.getDescription());
       fine.setFee(fineDTO.getFee());
       fine.setDate(fineDTO.getDate());
       fine.setUserId(fineDTO.getUserId());
       fine.setCarId(fineDTO.getCarId());
       return fine;
   }

    public FineDTO mapFineToFineDTO(Fine fine) {
        FineDTO fineDTO = new FineDTO();
        fineDTO.setDescription(fine.getDescription());
        fineDTO.setFee(fine.getFee());
        fineDTO.setDate(fine.getDate());
        fineDTO.setUserId(fine.getUserId());
        fineDTO.setCarId(fine.getCarId());
        return fineDTO;
    }
}
