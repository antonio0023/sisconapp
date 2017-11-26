/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

/**
 *
 * @author Cristian
 */
public class OrdenFabricacion {
    private int idorden,noorden,codigo,abierta;
    String fecha,nombre;
    private int porcentajeMOD, porcentajeMP;

    public int getPorcentajeMOD() {
        return porcentajeMOD;
    }

    public void setPorcentajeMOD(int porcentajeMOD) {
        this.porcentajeMOD = porcentajeMOD;
    }

    public int getPorcentajeMP() {
        return porcentajeMP;
    }

    public void setPorcentajeMP(int porcentajeMP) {
        this.porcentajeMP = porcentajeMP;
    }
    private int cantidad;
    private Double precio;
    //String abierta;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getIdorden() {
        return idorden;
    }

    public void setIdorden(int idorden) {
        this.idorden = idorden;
    }

    public int getNoorden() {
        return noorden;
    }

    public void setNoorden(int noorden) {
        this.noorden = noorden;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAbierta() {
        return abierta;
    }

    public void setAbierta(int abierta) {
        this.abierta = abierta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSaldoactual() {
        return saldoactual;
    }

    public void setSaldoactual(Double saldoactual) {
        this.saldoactual = saldoactual;
    }

    public Double getMateriaprima() {
        return materiaprima;
    }

    public void setMateriaprima(Double materiaprima) {
        this.materiaprima = materiaprima;
    }

    public Double getManodeobra() {
        return manodeobra;
    }

    public void setManodeobra(Double manodeobra) {
        this.manodeobra = manodeobra;
    }

    public Double getCif() {
        return cif;
    }

    public void setCif(Double cif) {
        this.cif = cif;
    }
    private Double saldoactual,materiaprima,manodeobra,cif;
}
