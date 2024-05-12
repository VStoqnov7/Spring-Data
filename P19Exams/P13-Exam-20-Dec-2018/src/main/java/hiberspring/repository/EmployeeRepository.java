package hiberspring.repository;

import hiberspring.domain.dtos.EmployeeExportDTO;
import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT NEW hiberspring.domain.dtos.EmployeeExportDTO(e.firstName, e.lastName, e.position, e.card.number) " +
            "FROM Employee e " +
            "WHERE e.branch.products IS NOT EMPTY " +
            "ORDER BY CONCAT(e.firstName,' ',e.lastName), LENGTH(e.position) DESC")
    List<EmployeeExportDTO> findEmployeeByNamePositionCardNumber();

}
