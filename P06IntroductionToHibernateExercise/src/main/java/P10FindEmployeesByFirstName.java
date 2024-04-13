import entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P10FindEmployeesByFirstName {
    private static final String SELECT_ALL_EMPLOYEES_WHOSE_START_WITH = "SELECT e FROM Employee AS e WHERE e.firstName LIKE (:firstLetters)";
    private static final String PRINT_FORMAT = "%s %s - %s - ($%.2f)%n";
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Scanner scanner = new Scanner(System.in);
        String inputLetters = scanner.nextLine();

        List<Employee> resultEmployees = em.createQuery(SELECT_ALL_EMPLOYEES_WHOSE_START_WITH, Employee.class)
                .setParameter("firstLetters", inputLetters + "%")
                .getResultList();

        resultEmployees.forEach(e -> System.out.printf(PRINT_FORMAT,e.getFirstName(),e.getLastName(),e.getJobTitle(),e.getSalary()));

        em.close();
        emf.close();
    }
}
