import entities.Project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class P08FindTheLatest10Projects {

    private static final String SELECT_LAST_10_STARTED_PROJECTS = "SELECT p FROM Project AS p ORDER BY p.startDate DESC";
    private static final String PRINT_FORMAT_PROJECT_NAME_DESCRIPTION_DEVELOPMENT_OF_START_END_DATE =
            "Project name: %s%n      Project Description: %s%n      Project Start Date: %s%n      Project End Date: %s%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();


        List<Project> resultQueryProjects = em.createQuery(SELECT_LAST_10_STARTED_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream().
                sorted(Comparator.comparing(Project::getName)).toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        resultQueryProjects.forEach(p -> {
            String startDate = p.getStartDate().format(formatter);
            String endDate = p.getEndDate() != null ? p.getEndDate().format(formatter) : "null";

            System.out.printf(PRINT_FORMAT_PROJECT_NAME_DESCRIPTION_DEVELOPMENT_OF_START_END_DATE,
                    p.getName(),
                    p.getDescription(),
                    startDate,
                    endDate);
        });
        em.close();
        emf.close();
    }
}
