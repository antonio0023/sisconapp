/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

import Otros.OrdenFabricacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Otros.MateriaPrima;

/**
 *
 * @author Cristian
 */
public class MateriaPTableModel extends AbstractTableModel {
     public List<MateriaPrima> materiasP = new ArrayList<MateriaPrima>();

    @Override
    public int getRowCount() {
        return materiasP.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MateriaPrima materia = materiasP.get(rowIndex);
        Object valor = null;
        
        switch(columnIndex) {
            case 0: valor = materia.getNombreMP();
                break;
            case 1: valor = materia.getMonto();
                break;
        }
        return valor;
    }
}
