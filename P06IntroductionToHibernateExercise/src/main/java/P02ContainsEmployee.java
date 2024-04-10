import jakarta.persistence.*;

import java.util.Scanner;

public class P02ContainsEmployee {

    private static final String SELECT_CONTAIN_EMPLOYEE = "SELECT COUNT(e) FROM Employee AS e WHERE e.firstName = :firstName AND e.lastName = :lastName";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        String firstName = input[0];
        String lastName = input[1];

        Long resultQuery = em.createQuery(SELECT_CONTAIN_EMPLOYEE, Long.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();

        if (resultQuery > 0){
            System.out.println("Yes");
        }else {
            System.out.println("No");
        }

        em.close();
        emf.close();
    }
}
