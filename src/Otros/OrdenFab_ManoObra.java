package Otros;

import java.util.Date;

public class OrdenFab_ManoObra {
    private int idordenmod;
    private OrdenFabricacion idorden;
    private Empleado idempleado;
    private Double horas;
    private Double monto;
    private Date fecha;    

    public int getIdordenmod() {
        return idordenmod;
    }

    public void setIdordenmod(int idordenmod) {
        this.idordenmod = idordenmod;
    }

    public OrdenFabricacion getIdorden() {
        return idorden;
    }

    public void setIdorden(OrdenFabricacion idorden) {
        this.idorden = idorden;
    }

    public Empleado getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Empleado idempleado) {
        this.idempleado = idempleado;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
