import entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class P09IncreaseSalaries {
    private static final String SELECT_ALL_EMPLOYEES_WHO_ARE_IN =
            "SELECT e FROM Employee AS e JOIN e.department AS d WHERE d.name IN (:departments)";

    private static final String PRINT_FORMAT_NAME_SALARY = "%s %s ($%.2f)%n";
    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<String> departmentNames = Arrays.asList("Engineering", "Tool Design", "Marketing", "Information Services");

        List<Employee> resultEmployeeQuery = em.createQuery(SELECT_ALL_EMPLOYEES_WHO_ARE_IN, Employee.class)
                .setParameter("departments", departmentNames)
                .getResultList();

        resultEmployeeQuery.forEach(e -> e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12))));
        transaction.commit();

        resultEmployeeQuery.forEach(e -> System.out.printf(PRINT_FORMAT_NAME_SALARY,e.getFirstName(),e.getLastName(),e.getSalary()));

        em.close();
        emf.close();

    }
}
