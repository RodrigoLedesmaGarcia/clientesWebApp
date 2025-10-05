package com.spring.www.ms_clientes.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "este campo no puede estar vacio")
    private String nombre;

    @NotBlank(message = "este campo no puede estar vacio")
    private String apm;

    @NotBlank(message = "este campo no puede estar vacio")
    private String app;

    @Email(message = "ingrese un correo valido")
    @NotBlank(message = "este campo no puede estar vacio")
    private String correo;

    private Date createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    // ---- PDF único por cliente ----
    private String pdfNombre;
    private String pdfTipo;
    private Long   pdfTamano;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] pdfDatos;


    private String fotoNombre;
    private String fotoTipo;
    private Long   fotoTamano;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] fotoDatos;

    public String getFotoNombre() {
        return fotoNombre;
    }

    public void setFotoNombre(String fotoNombre) {
        this.fotoNombre = fotoNombre;
    }

    public String getFotoTipo() {
        return fotoTipo;
    }

    public void setFotoTipo(String fotoTipo) {
        this.fotoTipo = fotoTipo;
    }

    public Long getFotoTamano() {
        return fotoTamano;
    }

    public void setFotoTamano(Long fotoTamano) {
        this.fotoTamano = fotoTamano;
    }

    public byte[] getFotoDatos() {
        return fotoDatos;
    }

    public void setFotoDatos(byte[] fotoDatos) {
        this.fotoDatos = fotoDatos;
    }

    /* ****************** LOS GETTER & SETTER ******************* */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPdfNombre() {
        return pdfNombre;
    }

    public void setPdfNombre(String pdfNombre) {
        this.pdfNombre = pdfNombre;
    }

    public String getPdfTipo() {
        return pdfTipo;
    }

    public void setPdfTipo(String pdfTipo) {
        this.pdfTipo = pdfTipo;
    }

    public Long getPdfTamano() {
        return pdfTamano;
    }

    public void setPdfTamano(Long pdfTamano) {
        this.pdfTamano = pdfTamano;
    }

    public byte[] getPdfDatos() {
        return pdfDatos;
    }

    public void setPdfDatos(byte[] pdfDatos) {
        this.pdfDatos = pdfDatos;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
