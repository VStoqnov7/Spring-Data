import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hibernate-jpa");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = new Student("TeoNew", 10, "School 1");
        em.persist(student);

        student.setName("Lio");
        em.persist(student);

//        Student findStudent = em.find(Student.class, 5);
//        em.remove(findStudent);
//        System.out.println(findStudent.getId());

        List<Student> fromStudent = em
                .createQuery("FROM Student", Student.class)
                .getResultList();

        System.out.println("Output:");
        fromStudent.forEach(System.out::println);

        em.getTransaction().commit();
    }
}