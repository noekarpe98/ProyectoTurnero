/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.modelo;

import java.time.LocalDateTime;

public class Mensaje {

    private int id;
    private int usuarioId;
    private String texto;
    private String origen;
    private LocalDateTime fechaHora;

    // Constructor vacío
    public Mensaje() {
    }

    // Constructor con todos los campos
    public Mensaje(int id, int usuarioId, String texto, String origen, LocalDateTime fechaHora) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.texto = texto;
        this.origen = origen;
        this.fechaHora = fechaHora;
    }

    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}

