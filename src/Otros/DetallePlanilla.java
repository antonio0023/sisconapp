/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

import Otros.Empleado;

/**
 *
 * @author Ilich Daniel Morales
 */
public class DetallePlanilla {
    private int idDetallePlanilla;
    private Planilla planilla;
    private Empleado empleado;
    private Double aguinaldo;
    private Double vacacion;
    private Double ISSS;
    private Double AFP;
    private Double Devengado;
    private Double Total;
    private Double factorRecargo;
    private Double factorRecargoInef;
    private Double gastoPorIneficiencia;

    public int getIdDetallePlanilla() {
        return idDetallePlanilla;
    }

    public void setIdDetallePlanilla(int idDetallePlanilla) {
        this.idDetallePlanilla = idDetallePlanilla;
    }

    public Planilla getPlanilla() {
        return planilla;
    }

    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Double getVacacion() {
        return vacacion;
    }

    public void setVacacion(Double vacacion) {
        this.vacacion = vacacion;
    }

    public Double getISSS() {
        return ISSS;
    }

    public void setISSS(Double ISSS) {
        this.ISSS = ISSS;
    }

    public Double getAFP() {
        return AFP;
    }

    public void setAFP(Double AFP) {
        this.AFP = AFP;
    }

    public Double getDevengado() {
        return Devengado;
    }

    public void setDevengado(Double Devengado) {
        this.Devengado = Devengado;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public Double getFactorRecargo() {
        return factorRecargo;
    }

    public void setFactorRecargo(Double factorRecargo) {
        this.factorRecargo = factorRecargo;
    }

    public Double getFactorRecargoInef() {
        return factorRecargoInef;
    }

    public void setFactorRecargoInef(Double factorRecargoInef) {
        this.factorRecargoInef = factorRecargoInef;
    }

    public Double getGastoPorIneficiencia() {
        return gastoPorIneficiencia;
    }

    public void setGastoPorIneficiencia(Double gastoPorIneficiencia) {
        this.gastoPorIneficiencia = gastoPorIneficiencia;
    }
}
