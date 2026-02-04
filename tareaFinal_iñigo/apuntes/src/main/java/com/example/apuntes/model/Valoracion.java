package com.example.apuntes.model;

import jakarta.persistence.*;

@Entity
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer puntuacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "apunte_id", nullable = false)
    private Apunte apunte;

    public Valoracion() {
    }

    public Long getId() {
        return id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Apunte getApunte() {
        return apunte;
    }

    public void setApunte(Apunte apunte) {
        this.apunte = apunte;
    }
}
