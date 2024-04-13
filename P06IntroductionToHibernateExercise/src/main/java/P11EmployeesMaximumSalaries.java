import entities.Department;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Comparator;
import java.util.List;

public class P11EmployeesMaximumSalaries {
    private static final String SELECT_DEPARTMENT_WITHOUT_30000_AND_70000 =
                    "SELECT d " +
                    "FROM Department AS d " +
                    "JOIN d.manager AS m " +
                    "GROUP BY d " +
                    "HAVING MAX(m.salary) NOT BETWEEN 30000 AND 70000";

    private static final String PRINT_FORMAT = "%s %.2f%n";

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<Department> resultDepartmentsQuery = em.createQuery(SELECT_DEPARTMENT_WITHOUT_30000_AND_70000, Department.class)
                .getResultList().stream().sorted(Comparator.comparing(department -> department.getManager().getSalary(), Comparator.reverseOrder())).toList();

        resultDepartmentsQuery.forEach(d -> System.out.printf(PRINT_FORMAT,d.getName(),d.getManager().getSalary()));

        em.close();
        emf.close();
    }
}
