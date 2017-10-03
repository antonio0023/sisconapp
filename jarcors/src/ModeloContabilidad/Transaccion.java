/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloContabilidad;

import java.util.Date;

/**
 *
 * @author Morelia_Gomes
 */
public class Transaccion {
    private int id_transaccion;
    private String descripcion;
    private Date fecha;

    public int getNo() {
        return id_transaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setNo(int no) {
        this.id_transaccion = no;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
