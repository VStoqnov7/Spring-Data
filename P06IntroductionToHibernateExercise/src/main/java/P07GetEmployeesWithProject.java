import entities.Employee;

import entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class P07GetEmployeesWithProject {
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT e FROM Employee AS e WHERE e.id = :id";
    private static final String PRINT_FORMAT_NAME_LAST_NAME_JOB_TITLE = "%s %s - %s%n";
    private static final String PRINT_FORMAT_PROJECT = "      %s%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Scanner scanner = new Scanner(System.in);
        int inputId = Integer.parseInt(scanner.nextLine());

        Employee resultQueryEmployee = em.createQuery(SELECT_EMPLOYEE_BY_ID, Employee.class)
                .setParameter("id", inputId)
                .getSingleResult();

        System.out.printf(PRINT_FORMAT_NAME_LAST_NAME_JOB_TITLE,resultQueryEmployee.getFirstName(),resultQueryEmployee.getLastName(),resultQueryEmployee.getJobTitle());
        resultQueryEmployee.getProjects().stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(project -> System.out.printf(PRINT_FORMAT_PROJECT,project.getName()));
        em.close();
        emf.close();
    }
}
