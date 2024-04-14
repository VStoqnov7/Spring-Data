import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("soft_uni_relations_exercise3");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
    }
}