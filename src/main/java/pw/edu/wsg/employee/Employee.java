package pw.edu.wsg.employee;

import com.sun.istack.NotNull;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Employee implements Serializable {

    @Id
//    @SequenceGenerator(
//            name = "employee_sequence",
//            sequenceName = "employee_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            //generator = "employee_sequence")
    )
    private Long id;

    @NotNull
    private String name;

    @ElementCollection
    @Nullable
    private List<Integer> availability;

    @Nullable
    private Integer daysLeft = 0;

    @Nullable
    private Double volumeFactor = 0.0;

    public Employee() {
    }


//    public Employee(String name, List<Integer> availability, Integer daysLeft, Double volumeFactor) {
//        this.name = name;
//        this.availability = availability;
//        this.daysLeft = daysLeft;
//        this.volumeFactor = volumeFactor;
//    }

    public Employee(String name, List<Integer> availability) {
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Double getVolumeFactor() {
        return volumeFactor;
    }

    public void setVolumeFactor(Double volumeFactor) {
        this.volumeFactor = volumeFactor;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                ", id=" + id +
                ", daysLeft=" + daysLeft +
                ", volumeFactor=" + volumeFactor +
                '}';
    }
}
