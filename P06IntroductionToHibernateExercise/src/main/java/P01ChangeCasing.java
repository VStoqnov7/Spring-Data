import entities.Town;

import jakarta.persistence.*;

import java.util.List;

public class P01ChangeCasing {
    private static final String SELECT_TOWNS = "SELECT t FROM Town AS t";
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_hibernate");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        TypedQuery<Town> query = em.createQuery(SELECT_TOWNS, Town.class);
        List<Town> towns = query.getResultList();

        for (Town town : towns){
            if (town.getName().length() < 5){
                town.setName(town.getName().toUpperCase());
            }
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
