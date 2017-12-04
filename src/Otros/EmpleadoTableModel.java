/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Otros;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class EmpleadoTableModel extends AbstractTableModel{

public List<Empleado> empleados = new ArrayList<Empleado>();
@Override
public int getRowCount(){
    return empleados.size();
}

@Override
public int getColumnCount(){
    return 4;
}

    @Override
    public  Object getValueAt(int rowIndex, int columnIndex) {
        Empleado empleado = empleados.get(rowIndex);
        Object valor=null;
        switch(columnIndex){
            case 0: valor= empleado.getCodigo();
                break;
            case 1: valor= empleado.getNombre();
                break;
            case 2: valor= empleado.getSalario();
                break;
            case 3: valor=empleado.getEficiencia();
        }
        return valor;
    }
    

}
