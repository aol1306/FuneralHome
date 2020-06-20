package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private LocalDate dateOfEmployment;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkDay> workDays = new ArrayList<>();

    /**
     * class extent
     */
    @Transient
    private static List<Employee> extent = new ArrayList<>();

    /**
     * Adds an employee to the extent.
     *
     * @param employee an employee
     */
    @Transient
    private static void addEmployee(Employee employee) {
        extent.add(employee);
    }

    /**
     * Removes an employee from the extent.
     *
     * @param employee an employee
     */
    private static void removeEmployee(Employee employee) {
        extent.remove(employee);
    }

    public Employee() {
        addEmployee(this);
    }

    public void addWorkDay(WorkDay workDay) {
        if (!workDays.contains(workDay)) {
            workDays.add(workDay);
            workDay.setEmployee(this);
        }
    }

    public void removeWorkDay(WorkDay workDay) {
        if(workDays.contains(workDay)) {
            workDays.remove(workDay);
            workDay.setEmployee(null);
        }
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }
}
