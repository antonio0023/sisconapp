/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

import Otros.OrdenFab_ManoObra;
import Otros.OrdenFabricacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class OrdenManoObraTableModel extends AbstractTableModel {
    public List<OrdenFab_ManoObra> ordenesMO = new ArrayList<OrdenFab_ManoObra>();

    @Override
    public int getRowCount() {
        return ordenesMO.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrdenFab_ManoObra orden = ordenesMO.get(rowIndex);
        Object valor = null;        
        switch(columnIndex) {
            case 0: valor = orden.getIdempleado();
                break;
            case 1: valor = orden.getHoras();
                break;
            case 2: valor = "$ " + orden.getMonto();
                break;
            case 3: valor = orden.getFecha().toString();
        }
        return valor;
    }
    
}
