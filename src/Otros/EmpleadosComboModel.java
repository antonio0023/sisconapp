/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Otros;

import Otros.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Ilich Daniel Morales
 */
public class EmpleadosComboModel extends AbstractListModel implements ComboBoxModel {
    public List<Empleado> empleados = new ArrayList<Empleado>();

    Empleado selection = null;
    public Object getElementAt(int index) {
        return empleados.get(index);
    }

    public int getSize() {
        return empleados.size();
    }

    public void setSelectedItem(Object anItem) {
        selection = (Empleado)anItem; // to select and register an
    } // item from the pull-down list

      // Methods implemented from the interface ComboBoxModel
      public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
      }
}
