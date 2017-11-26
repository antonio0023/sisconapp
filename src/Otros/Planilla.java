/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

import java.util.Date;

/**
 *
 * @author Ilich Daniel Morales
 */
public class Planilla {
    private int idplanilla;
    private Date periodoPlanilla;
    private String descripcion;
    private boolean estatus;

    public int getIdplanilla() {
        return idplanilla;
    }

    public void setIdplanilla(int idplanilla) {
        this.idplanilla = idplanilla;
    }

    public Date getPeriodoPlanilla() {
        return periodoPlanilla;
    }

    public void setPeriodoPlanilla(Date periodoPlanilla) {
        this.periodoPlanilla = periodoPlanilla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
}
