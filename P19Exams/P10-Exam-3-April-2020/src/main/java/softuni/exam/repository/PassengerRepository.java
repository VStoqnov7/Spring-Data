package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Passenger;
import softuni.exam.models.dto.PassengerExportDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    Optional<Passenger> findByEmail(String email);

    @Query("SELECT NEW softuni.exam.models.dto.PassengerExportDTO(p.firstName,p.lastName, p.email, p.phoneNumber, p.tickets.size) " +
            "FROM Passenger p " +
            "ORDER BY p.tickets.size DESC, p.email")
    List<PassengerExportDTO> findPassengerByFirstNameAndLastNameAndEmailAndPhoneNumber();
}
