package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class WorkDay {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }
}
