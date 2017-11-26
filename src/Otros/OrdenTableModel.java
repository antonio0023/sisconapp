package Otros;
import Otros.OrdenFabricacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cristian
 */
public class OrdenTableModel extends AbstractTableModel {
    public List<OrdenFabricacion> ordenes = new ArrayList<OrdenFabricacion>();

    @Override
    public int getRowCount() {
        return ordenes.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
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
            /*case 2: valor = orden.getNoorden();
                break;*/
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
