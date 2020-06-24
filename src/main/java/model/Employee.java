package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee holds data of funeral home's employee.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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
     * @param employee an employee
     */
    @Transient
    private static void addEmployee(Employee employee) {
        extent.add(employee);
    }

    /**
     * Removes an employee from the extent.
     * @param employee an employee
     */
    private static void removeEmployee(Employee employee) {
        extent.remove(employee);
    }

    /**
     * Creates new employee object and adds it to the extent
     */
    public Employee() {
        addEmployee(this);
    }

    /**
     * Adds a work day to this employee.
     * @param workDay work day
     */
    public void addWorkDay(WorkDay workDay) {
        if (!workDays.contains(workDay)) {
            workDays.add(workDay);
            workDay.setEmployee(this);
        }
    }

    /**
     * Removes a work day from this employee.
     * @param workDay work day
     */
    public void removeWorkDay(WorkDay workDay) {
        if(workDays.contains(workDay)) {
            workDays.remove(workDay);
            workDay.setEmployee(null);
        }
    }

    /**
     * Returns all work days associated with an employee.
     * @return list of work days
     */
    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    /**
     * Returns employee's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets employee's name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns employee's surname.
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets employee's surname.
     * @param surname surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the date, on which the employee started working for the funeral home.
     * @return employment date
     */
    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * Sets the date, on which the employee started working for the funeral home.
     * @param dateOfEmployment employment date
     */
    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}
