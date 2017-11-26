package Otros;

import Otros.DetallePlanilla;
import Otros.Planilla;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrador
 */
public class PlanillaTableModel extends AbstractTableModel {
    public List<Planilla> plan = new ArrayList<Planilla>();
    @Override
    public int getRowCount() {
        return plan.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Planilla det = plan.get(rowIndex);
        Object valor = null;
        
        switch(columnIndex) {
            case 0: valor = det.getIdplanilla();
                break;
            case 1: valor = det.getPeriodoPlanilla();
                break;
            case 2: valor = det.getDescripcion();
                break;
            case 3: valor = det.isEstatus() == true ? "APROBADA" : "CALCULADA";
                break;
        }
        return valor;
    }
}
