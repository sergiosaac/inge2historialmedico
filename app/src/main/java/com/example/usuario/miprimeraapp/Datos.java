package com.example.usuario.miprimeraapp;

/**
 * Created by Usuario on 19/03/2017.
 */

public class Datos {
    private int id;
    private String nombre;
    private String apellido;
    private String fecha;
    private String sexo;

    // Constructor de un objeto Contactos
    public Datos(int id, String nombre, String apellido, String fecha, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.sexo = sexo;
    }

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacimiento() {
        return fecha ;
    }
    public void setNacimiento(String nacimiento) {
        this.fecha = fecha;
    }
}
