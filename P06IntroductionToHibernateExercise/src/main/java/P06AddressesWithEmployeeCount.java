import entities.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class P06AddressesWithEmployeeCount {
    private static final String SELECT_ADDRESSES_ORDER_BY_EMPLOYEE_SIZE = "SELECT a FROM Address AS a ORDER BY SIZE(a.employees) DESC";
    private static final String PRINT_FORMAT = "%s, %s - %d employees%n";
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<Address> resultQuery = em.createQuery(SELECT_ADDRESSES_ORDER_BY_EMPLOYEE_SIZE, Address.class)
                .setMaxResults(10)
                .getResultList();

        resultQuery.forEach(a -> System.out.printf(PRINT_FORMAT,a.getText(),a.getTown().getName(),a.getEmployees().size()));

        em.close();
        emf.close();
    }
}
