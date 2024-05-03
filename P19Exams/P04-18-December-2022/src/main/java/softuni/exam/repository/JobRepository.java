package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.JobExportDTO;
import softuni.exam.models.entity.Job;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    @Query("SELECT NEW softuni.exam.models.dto.JobExportDTO(j.title, ROUND(j.salary, 2), ROUND(j.hoursAWeek, 2)) " +
            "FROM Job j " +
            "WHERE j.salary >= 5000.00 AND " +
            "j.hoursAWeek <= 30.00 " +
            "ORDER BY j.salary DESC")

    List<JobExportDTO> findJobTitleSalaryAndHoursOrderBySalaryDesc();

}
