import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class P03EmployeesWithASalaryOver50000 {

    private static final String SELECT_SALARY_OVER_50000 = "SELECT e FROM Employee AS e WHERE salary > 50000";
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<Employee> resultList = em.createQuery(SELECT_SALARY_OVER_50000, Employee.class)
                .getResultList();

        resultList.forEach(employee -> System.out.println(employee.getFirstName()));

        em.close();
        emf.close();
    }
}
