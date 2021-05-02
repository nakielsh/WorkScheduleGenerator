package pw.edu.wsg.employee;

import com.sun.istack.NotNull;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table
public class Employee implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @NotNull
    private String name;

    @ElementCollection
    @Nullable
    private List<Integer> availability;

    @Nullable
    private Long app_user_id;

    @Nullable
    private Integer daysLeft = 0;

    @Nullable
    private Double volumeFactor = 0.0;

    @Nullable
    private int numOfWorkingDays = 0;

    public Employee() {
    }


    public Employee(String name, @Nullable List<Integer> availability, Long app_user_id) {
        this.name = name;
        this.availability = availability;
        this.app_user_id = app_user_id;
    }

    public Employee(String name, @Nullable List<Integer> availability) {
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(@Nullable List<Integer> availability) {
        if (availability != null) {
            Collections.sort(availability);
        }
        this.availability = availability;
    }

    public Long getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(Long app_user_id) {
        this.app_user_id = app_user_id;
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

    public void setNumOfWorkingDays(int numOfWorkingDays) {
        this.numOfWorkingDays = numOfWorkingDays;
    }

    public int getNumOfWorkingDays() {
        return numOfWorkingDays;
    }

    public void decrementDaysLeft() {
        this.daysLeft -= 1;
    }

    public void incrementDaysLeft() {
        this.daysLeft += 1;
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
