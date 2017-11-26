
package Otros;

import Otros.Empleado;
import Otros.DetallePlanilla;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DetallePlanillaTableModel extends AbstractTableModel {
    public List<DetallePlanilla> detallePlanilla = new ArrayList<DetallePlanilla>();

    @Override
    public int getRowCount() {
        return detallePlanilla.size();
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DetallePlanilla det = detallePlanilla.get(rowIndex);
        Object valor = null;
        
        switch(columnIndex) {
            case 0: valor = det.getEmpleado().getCodigo();
                break;
            case 1: valor = det.getEmpleado().getNombre();
                break;
            case 2: valor = det.getEmpleado().getSalario();
                break;
            case 3: valor=det.getEmpleado().getAnio();
                break;
            case 4: valor=det.getDevengado();
                break;
            case 5: valor=det.getISSS();
                break;
            case 6: valor=det.getAFP();
                break;
            case 7: valor=det.getAguinaldo();
                break;
            case 8: valor=det.getVacacion();
                break;
            case 9: valor=det.getTotal();
                break;
            case 10: valor=det.getFactorRecargo();
                break;
            case 11: valor=det.getFactorRecargoInef();
                break;
            case 14: valor=det.getGastoPorIneficiencia();
                break;
        }
        return valor;
    }
}
