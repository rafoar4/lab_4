package com.example.lab_4;

import java.io.Serializable;

public class ListaEmployees implements Serializable {
    Employee[] employees;

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }
}
