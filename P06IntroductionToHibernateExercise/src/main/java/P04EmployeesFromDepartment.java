import entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P04EmployeesFromDepartment {
    private static final String SELECT_EMPLOYEE_WITH_DEPARTMENT_NAME =
            "SELECT e FROM Employee AS e JOIN e.department AS d WHERE d.name = :departmentName ORDER BY e.salary, e.id";
    private static final String PRINT_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        String departmentName = "Research and Development";
        //Research and Development

        List<Employee> resultDepartmentNameQuery = em.createQuery(SELECT_EMPLOYEE_WITH_DEPARTMENT_NAME, Employee.class)
                .setParameter("departmentName", departmentName)
                .getResultList();

        resultDepartmentNameQuery.forEach(e -> System.out.printf(PRINT_FORMAT, e.getFirstName(),e.getLastName(),e.getDepartment().getName(),e.getSalary()));

        em.close();
        emf.close();
    }
}
