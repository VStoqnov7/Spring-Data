import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("soft_uni_relations_exercise5");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            BankAccount bankAccount = new BankAccount();
            bankAccount.setBankName("Bank of America");
            bankAccount.setSwiftCode("BOFAUS3N");
            bankAccount.setBillingType(CardType.Bank_account);
            em.persist(bankAccount);

            CreditCard creditCard = new CreditCard();
            creditCard.setExpirationMonth(12);
            creditCard.setExpirationYear(2025);
            creditCard.setBillingType(CardType.Credit_Card);
            em.persist(creditCard);

            BillingDetail billingDetail = new BillingDetail();
            billingDetail.setCreditCard(creditCard);
            billingDetail.setBankAccount(bankAccount);
            billingDetail.setNumber(2222);
            em.persist(billingDetail);

            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("john@example.com");
            user.setPassword("password123");
            user.getBillingDetails().add(billingDetail);
            em.persist(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
//            em.close();
//            emf.close();
        }
    }
}