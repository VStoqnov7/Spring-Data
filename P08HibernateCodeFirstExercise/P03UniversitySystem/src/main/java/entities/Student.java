package entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends Person{

    @Column(name = "average_grade", nullable = false)
    private double averageGrade;

    private int attendance;

    @ManyToMany
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "students_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id",referencedColumnName = "id"))
    private Set<Course> courses;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String phoneNumber, float averageGrade, int attendance) {
        super(firstName, lastName, phoneNumber);

        this.averageGrade = averageGrade;
        this.attendance = attendance;
    }
}
