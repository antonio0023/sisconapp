/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenDeFabricacion;

import ModeloContabilidad.OrdenFabricacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author chavez
 */
public class OrdenFabTableModel extends AbstractTableModel {
    
    public List<OrdenFabricacion> ordenes = new ArrayList<OrdenFabricacion>();
    private final String[] tableHeaders = {"Cod","Nombre","Fecha","S. Actual","MP",
        "MO","CIF","Status","Cantidad","Precio"};
    @Override
    public String getColumnName(int columnIndex){
        return  tableHeaders[columnIndex];
    }
    @Override
    public int getRowCount() {
        return ordenes.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrdenFabricacion orden = ordenes.get(rowIndex);
        Object valor = null;
        
        switch(columnIndex) {
            case 0: valor = orden.getCodigo();
                break;
            case 1: valor = orden.getNombre();
                break;
            case 2: valor = orden.getFecha();
                break;
            case 3: valor = "$ " + orden.getSaldoactual();
                break;
            case 4: valor = "$ " + orden.getMateriaprima();
                break;
            case 5: valor = "$ " + orden.getManodeobra();
                break;
            case 6: valor = "$ " + orden.getCif();
                break;
            case 7: 
                if (orden.getAbierta()==1){
                        valor = "Abierta";
                    }
                    else{
                        valor= "Cerrada"; 
                    }
                    break;
            case 8: valor = orden.getCantidad();
                break;
            case 9: valor = "$ " + orden.getPrecio();
                break;
        }
        return valor;
    }
}

