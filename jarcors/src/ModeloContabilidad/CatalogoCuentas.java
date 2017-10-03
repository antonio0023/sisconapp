/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloContabilidad;

/**
 *
 * @author Jhonathan
 */
public class CatalogoCuentas {
    
   private int codigo;
   private String cuenta;
   private int tipo;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public int getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return this.codigo+" "+this.cuenta;
    }

   
  
    
}
