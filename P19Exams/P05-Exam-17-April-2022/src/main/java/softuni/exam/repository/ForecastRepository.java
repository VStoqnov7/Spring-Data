package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ForecastExportDTO;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast,Long> {

    Optional<Forecast> findByDayOfWeekAndCityId(DayOfWeek dayOfWeek, Long cityId);

    @Query("SELECT NEW softuni.exam.models.dto.ForecastExportDTO(f.city.cityName, f.minTemperature, f.maxTemperature, f.sunrise, f.sunset) " +
            "FROM Forecast f " +
            "WHERE f.dayOfWeek = 'SUNDAY' AND " +
            "f.city.population < 150000 " +
            "ORDER BY f.maxTemperature DESC, f.id")
    List<ForecastExportDTO> findByCityNameMinAndMaxTemperatureSunriseSunset();
}
