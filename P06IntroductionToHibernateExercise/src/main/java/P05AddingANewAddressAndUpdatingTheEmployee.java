import entities.Address;
import entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Scanner;

public class P05AddingANewAddressAndUpdatingTheEmployee {
    private static final String CREATE_NEW_ADDRESS_AND_ADD_TO_THE_EMPLOYEE_WITH_INPUT_LAST_NAME =
            "SELECT e FROM Employee AS e WHERE e.lastName = :name";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Scanner scanner = new Scanner(System.in);
        String inputLastName = scanner.nextLine();

        Employee employee = em.createQuery(CREATE_NEW_ADDRESS_AND_ADD_TO_THE_EMPLOYEE_WITH_INPUT_LAST_NAME, Employee.class)
                .setParameter("name", inputLastName).getSingleResult();

        Address address = new Address();
        address.setText("Vitoshka 15");
        em.persist(address);

        employee.setAddress(address);
        em.getTransaction().commit();

        System.out.println("Address set successfully.");
        em.close();
        emf.close();
    }
}
