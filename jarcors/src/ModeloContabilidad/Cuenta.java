/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloContabilidad;

/**
 *
 * @author Morelia_Gomes
 */
public class Cuenta {

    private String codigo;
    private String tipo;
    private String nombre;
    private Double saldoDeudor;
    private Double saldoAcreedor;
    private Double resultado;
    private int cantidad;
    private Double precio;

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    
    

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getSaldoDeudor() {
        return saldoDeudor;
    }

    public Double getSaldoAcreedor() {
        return saldoAcreedor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSaldoDeudor(Double saldoDeudor) {
        this.saldoDeudor = saldoDeudor;
    }

    public void setSaldoAcreedor(Double saldoAcreedor) {
        this.saldoAcreedor = saldoAcreedor;
    }

}
