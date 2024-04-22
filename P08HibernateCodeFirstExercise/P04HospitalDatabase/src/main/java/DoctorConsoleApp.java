import entities.Patient;
import entities.Visitation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class DoctorConsoleApp {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni_relations_exercise4");
    private static final EntityManager entityManager = emf.createEntityManager();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Добави нов пациент");
            System.out.println("2. Виж всички пациенти");
            System.out.println("3. Добави посещение");
            System.out.println("4. Виж посещения за пациент");
            System.out.println("5. Изход");

            System.out.print("Изберете опция:");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNewPatient();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    addNewVisitation();
                    break;
                case 4:
                    viewPatientVisitations();
                    break;
                case 5:
                    System.out.println("Излизане от програмата.");
                    entityManager.close();
                    emf.close();
                    return;
                default:
                    System.out.println("Невалидна опция. Моля, изберете отново.");
            }
        }
    }

    private static void addNewPatient() {
        entityManager.getTransaction().begin();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете име на пациента:");
        String firstName = scanner.nextLine();
        System.out.println("Въведете фамилия на пациента:");
        String lastName = scanner.nextLine();
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        entityManager.persist(patient);
        entityManager.getTransaction().commit();
        System.out.println("Пациентът е добавен успешно.");
    }

    private static void viewAllPatients() {
        entityManager.getTransaction().begin();
        List<Patient> patients = entityManager.createQuery("SELECT p FROM Patient AS p", Patient.class).getResultList();
        entityManager.getTransaction().commit();
        System.out.println("Всички пациенти:");
        int index = 1;
        for (Patient patient : patients) {
            System.out.printf("%d. %s%n",index,patient);
        }
    }

    private static void addNewVisitation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Изберете ID на пациента, за когото добавяте посещение:");
        long patientId = scanner.nextLong();
        Patient patient = entityManager.find(Patient.class, patientId);
        if (patient == null) {
            System.out.println("Пациент с ID " + patientId + " не съществува.");
            return;
        }
        scanner.nextLine();
        System.out.println("Въведете дата на посещението (yyyy-MM-dd):");
        String dateString = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (Exception e) {
            System.out.println("Невалиден формат за дата. Използвайте формат yyyy-MM-dd.");
            return;
        }
        System.out.println("Въведете коментар за посещението:");
        String comments = scanner.nextLine();
        entityManager.getTransaction().begin();
        Visitation visitation = new Visitation();
        visitation.setPatient(patient);
        visitation.setDate(date);
        visitation.setComments(comments);
        entityManager.persist(visitation);
        entityManager.getTransaction().commit();
        System.out.println("Посещението е добавено успешно.");
    }

    private static void viewPatientVisitations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Изберете ID на пациента, за когото желаете да видите посещенията:");
        long patientId = scanner.nextLong();
        Patient patient = entityManager.find(Patient.class, patientId);
        if (patient == null) {
            System.out.println("Пациент с ID " + patientId + " не съществува.");
            return;
        }
        entityManager.getTransaction().begin();
        List<Visitation> visitations = entityManager.createQuery("SELECT v FROM Visitation v WHERE v.patient = :patient", Visitation.class)
                .setParameter("patient", patient)
                .getResultList();
        entityManager.getTransaction().commit();
        if (visitations.isEmpty()) {
            System.out.println("Няма посещения за този пациент.");
        } else {
            System.out.println("Посещения на пациент " + patient.getFirstName() + " " + patient.getLastName() + ":");
            for (Visitation visitation : visitations) {
                System.out.println(visitation);
            }
        }
    }
}