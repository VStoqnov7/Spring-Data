package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Car;
import softuni.exam.models.dto.CarExportDTO;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT NEW softuni.exam.models.dto.CarExportDTO(c.make, c.model, c.kilometers, c.registeredOn, c.pictures.size) " +
            "FROM Car c " +
            "ORDER BY c.pictures.size DESC, c.make")
    List<CarExportDTO> findCarByMakeModelKilometersRegisteredOnCountPictures();

}
