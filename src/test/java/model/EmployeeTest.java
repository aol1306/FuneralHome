package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void employeeWorkDayAssociation() {
        Employee employee = new Employee();
        WorkDay workDay = new WorkDay();

        employee.addWorkDay(workDay);

        assertEquals(workDay.getEmployee(), employee);

        WorkDay workDay1 = new WorkDay();
        workDay1.setEmployee(employee);

        assertTrue(employee.getWorkDays().contains(workDay1));

        employee.removeWorkDay(workDay1);
        assertFalse(employee.getWorkDays().contains(workDay1));

        // change employee
        Employee employee1 = new Employee();
        employee1.addWorkDay(workDay);

        assertEquals(workDay.getEmployee(), employee1);
        assertFalse(employee.getWorkDays().contains(workDay));
    }
}