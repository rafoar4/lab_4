package com.example.lab_4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmployeeRepository {
    @GET("/listaSinPresi")
    Call<EmployeeDto> listarEmployees();
    @GET("/informacionEmpleado")
    Call<Employee> empleado(int id);
    /*@POST("/actualizar")
    Call<Employee>*/

}
