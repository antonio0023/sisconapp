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
public class MateriaPrima {
    int idMP;
    String nombreMP,codigoMP;
    double monto;

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdMP() {
        return idMP;
    }

    public void setIdMP(int idMP) {
        this.idMP = idMP;
    }

    public String getNombreMP() {
        return nombreMP;
    }

    public void setNombreMP(String nombreMP) {
        this.nombreMP = nombreMP;
    }

    public String getCodigoMP() {
        return codigoMP;
    }
    
    public void setCodigoMP(String codigoMP) {
        this.codigoMP = codigoMP;
    }

    @Override
    public String toString() {
        return "MateriaPrima{" + "idMP=" + idMP + ", nombreMP=" + nombreMP + ", codigoMP=" + codigoMP + '}';
    }



    
}
