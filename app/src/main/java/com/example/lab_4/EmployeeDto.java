package com.example.lab_4;

import java.io.Serializable;

public class EmployeeDto implements Serializable {
    private ListaEmployees _embedded;

    public ListaEmployees get_embedded() {
        return _embedded;
    }

    public void set_embedded(ListaEmployees _embedded) {
        this._embedded = _embedded;
    }
}
