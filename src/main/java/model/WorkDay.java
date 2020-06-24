package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Registers past work days of all employees, for time tracking purposes.
 */
@Entity
public class WorkDay {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    private Employee employee;

    /**
     * Sets an employee who worked a work day
     * @param employee employee
     */
    public void setEmployee(Employee employee) {
        if (this.employee == employee) return;
        if (this.employee != null) {
            this.employee.removeWorkDay(this);
        }
        this.employee = employee;
        if (employee != null) {
            employee.addWorkDay(this);
        }
    }

    /**
     * Returns an employee who worked this work day.
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns work day date.
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets work day date.
     * @param date date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns start time of work day.
     * @return time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets start time of work day.
     * @param startTime time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns end time of work day.
     * @return time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time for work day.
     * @param endTime time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
