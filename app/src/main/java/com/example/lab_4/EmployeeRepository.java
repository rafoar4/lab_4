package com.example.lab_4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeRepository {
    @GET("/lab4/listaSinPresi")
    Call<List<Employee>> listarEmployees();
    @GET("/lab4/informacionEmpleado")
    Call<Employee> empleado(@Query("id") int id);
    /*@POST("/actualizar")
    Call<Employee>*/

}
