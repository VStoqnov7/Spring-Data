package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.OfferExportDTO;
import softuni.exam.models.entity.Offer;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("SELECT NEW softuni.exam.models.dto.OfferExportDTO(o.agent.firstName, o.agent.lastName, o.id, o.apartment.area, o.apartment.town.townName, o.price) " +
            "FROM Offer o " +
            "WHERE o.apartment.apartmentType = 'three_rooms' " +
            "ORDER BY o.apartment.area DESC, o.price")
    List<OfferExportDTO> findOfferByFirstNameLastNameIdAreaTownNamePrice();

}
