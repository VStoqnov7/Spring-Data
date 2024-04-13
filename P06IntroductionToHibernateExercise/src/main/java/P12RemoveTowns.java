import entities.Address;
import entities.Employee;
import entities.Town;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class P12RemoveTowns {
    private static final String SELECT_TOWN = "SELECT t FROM Town AS t WHERE t.name = :name";
    private static final String SELECT_ADDRESSES = "SELECT a FROM Address AS a WHERE a.town.id = :town_id";


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Scanner scanner = new Scanner(System.in);
        String inputTown = scanner.nextLine();
        transaction.begin();

        Town town = em.createQuery(SELECT_TOWN, Town.class)
                .setParameter("name", inputTown)
                .getSingleResult();

        List<Address> addresses = em.createQuery(SELECT_ADDRESSES, Address.class)
                .setParameter("town_id", town.getId())
                .getResultList();

        addresses.forEach(address -> {
            Set<Employee> employees = address.getEmployees();
            employees.forEach(employee -> {
                employee.setAddress(null);
                em.merge(employee);
            });
        });

        addresses.forEach(em::remove);
        em.remove(town);
        transaction.commit();
        System.out.printf("%d address in %s deleted", addresses.size(), inputTown);
        em.close();
        emf.close();
    }
}
