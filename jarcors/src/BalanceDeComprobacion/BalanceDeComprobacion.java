package BalanceDeComprobacion;

import BDContabilidad.Conexion;
import static BDContabilidad.Conexion.conectar;
import ModeloContabilidad.Cuenta;
import ModeloContabilidad.PeriodoContable;
import interfaces.EstadosFinancieros;
import interfaces.PanelImagen;
import java.awt.*;
import java.awt.print.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author JOSE
 */
public class BalanceDeComprobacion extends javax.swing.JFrame implements Printable {

    public ComprobacionTableModel comprobacionTModel = new ComprobacionTableModel();
    /**
     * Creates new form EstadoDeComprobacion
     */
    public BalanceDeComprobacion() {
        initComponents();
        
        //centrar frame
        setLocationRelativeTo(null);
        setVisible(true);
        
        PanelImagen obj = new PanelImagen();
        obj.setImagen("/Imagenes/panaderia_fondo.png");
        this.add(obj, BorderLayout.CENTER);
        this.pack();
        
        inicializarColumnas();
        
        conectar();
        
        consulta();
    }
    
    private void inicializarColumnas()
    {
         TableColumnModel tColumnModel = new DefaultTableColumnModel();
        
        for(int i = 0; i < 4; i++) {
            TableColumn col = new TableColumn(i);
            
            switch(i) {
                case 0: col.setHeaderValue("Codigo");
                    break;
                case 1: col.setHeaderValue("Nombre");
                    break;
                case 2: col.setHeaderValue("Debe ($)");
                    break;
                case 3: col.setHeaderValue("Haber ($)");
            }
            
            tColumnModel.addColumn(col);
        }
        tablaComprobacion.setColumnModel(tColumnModel);
    }
    
    private void consulta() { 
        double resultado_debe = 0;
        double resultado_haber = 0;
        int cont = 0;
        double res = 0.0;
        
        String fechaFinal = "";
        String fechaInicio = "";
        
        String fecha1 = "";
        String fecha2 = "";
        
        String diaFinal = "";
        String mesFinal = "";
        String agnoFinal = "";
        
        String diaInicio = "";
        String mesInicio = "";
        String agnoInicio = "";
        
        int mesi = 0;
        int mesf = 0;
        
        
        try { 
            String sentencia = "SELECT codigo, nombre, debe, haber from cuenta where debe != 0 or haber != 0";
            Statement statement = Conexion.conect.createStatement();
            ResultSet resultado = statement.executeQuery(sentencia);
            
            while(resultado.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(resultado.getString("codigo"));
                cuenta.setNombre(resultado.getString("nombre"));
                cuenta.setSaldoDeudor(resultado.getDouble("debe"));
                cuenta.setSaldoAcreedor(resultado.getDouble("haber"));
                
                resultado_debe += cuenta.getSaldoDeudor();
                resultado_haber += cuenta.getSaldoAcreedor();
                this.comprobacionTModel.cuentas.add(cuenta);
            }
            Cuenta cuenta_total = new Cuenta();
            cuenta_total.setCodigo("Total");
            cuenta_total.setNombre("");
            cuenta_total.setSaldoDeudor(resultado_debe);
            cuenta_total.setSaldoAcreedor(resultado_haber);
            this.comprobacionTModel.cuentas.add(cuenta_total);
            tablaComprobacion.repaint();
            
            //lDebe.setText("$" + Double.toString(resultado_debe));
            //lHaber.setText("$" + Double.toString(resultado_haber));
            
            String sen = "select fechainicio, fechafinal from periodocontable order by idperiodocontable desc limit 1";
            Statement stat = Conexion.conect.createStatement();
            ResultSet resu = stat.executeQuery(sen);
            
            while (resu.next()) {
                PeriodoContable per = new PeriodoContable();
                per.setFechaInicio(resu.getDate("fechainicio"));
                per.setFechaFinal(resu.getDate("fechafinal"));
                
                fecha1 = per.getFechaInicio().toString();
                fecha2 = per.getFechaFinal().toString();
                
                fechaInicio = fecha1;
                fechaFinal = fecha2;
                
                diaInicio = fechaInicio.substring(8,10);
                mesInicio = fechaInicio.substring(5,7);
                agnoInicio = fechaInicio.substring(0,4);
                
                diaFinal = fechaFinal.substring(8,10);
                mesFinal = fechaFinal.substring(5,7);
                agnoFinal = fechaFinal.substring(0,4);
            }

            mesi = Integer.parseInt(mesInicio);
            
            switch(mesi) {
                case 1: mesInicio = "Enero";
                    break;
                case 2: mesInicio = "Febrero";
                    break;
                case 3: mesInicio = "Marzo";
                    break;
                case 4: mesInicio = "Abril";
                    break;
                case 5: mesInicio = "Mayo";
                    break;
                case 6: mesInicio = "Junio";
                    break;
                case 7: mesInicio = "Julio";
                    break;
                case 8: mesInicio = "Agosto";
                    break;
                case 9: mesInicio = "Septiembre";
                    break;
                case 10: mesInicio = "Octubre";
                    break;
                case 11: mesInicio = "Noviembre";
                    break;
                case 12: mesInicio = "Diciembre";
                    break;
            }
            
            mesf = Integer.parseInt(mesFinal);
            
            switch(mesf) {
                case 1: mesFinal = "Enero";
                    break;
                case 2: mesFinal = "Febrero";
                    break;
                case 3: mesFinal = "Marzo";
                    break;
                case 4: mesFinal = "Abril";
                    break;
                case 5: mesFinal = "Mayo";
                    break;
                case 6: mesFinal = "Junio";
                    break;
                case 7: mesFinal = "Julio";
                    break;
                case 8: mesFinal = "Agosto";
                    break;
                case 9: mesFinal = "Septiembre";
                    break;
                case 10: mesFinal = "Octubre";
                    break;
                case 11: mesFinal = "Noviembre";
                    break;
                case 12: mesFinal = "Diciembre";
                    break;
            }
            
            lBalance.setText("Del " + diaInicio + " de " + mesInicio + " de " + agnoInicio + " al " + diaFinal + " de " + mesFinal + " de " + agnoFinal);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los datos de la base de datos");
            ex.printStackTrace();
        }
    }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        estado = new javax.swing.JPanel();
        jlEmpresa = new javax.swing.JLabel();
        jlEstado = new javax.swing.JLabel();
        lBalance = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaComprobacion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        estado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        estado.setOpaque(false);

        jlEmpresa.setBackground(new java.awt.Color(152, 190, 211));
        jlEmpresa.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlEmpresa.setText("Panadería Dulce Pan");

        jlEstado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlEstado.setText("Balance de Comprobación");

        lBalance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBalance.setText("Balance");

        tablaComprobacion.setModel(comprobacionTModel);
        tablaComprobacion.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tablaComprobacion);

        javax.swing.GroupLayout estadoLayout = new javax.swing.GroupLayout(estado);
        estado.setLayout(estadoLayout);
        estadoLayout.setHorizontalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estadoLayout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(jlEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
            .addGroup(estadoLayout.createSequentialGroup()
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(jlEstado))
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                            .addComponent(lBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        estadoLayout.setVerticalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlEstado)
                .addGap(19, 19, 19)
                .addComponent(lBalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jButton1.setText("Grabar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabar(evt);
            }
        });

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(36, 36, 36))
                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grabar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabar
        try {
            PrinterJob gap = PrinterJob.getPrinterJob();
            gap.setPrintable(this);
            boolean top = gap.printDialog();
            
            if(top) {
                gap.print();
            }
        }
        catch(PrinterException pex) {
            JOptionPane.showMessageDialog(null, "ERROR DE PROGRAMA", "Error\n" + pex, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_grabar

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        EstadosFinancieros obj = new EstadosFinancieros();
        obj.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BalanceDeComprobacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BalanceDeComprobacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BalanceDeComprobacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BalanceDeComprobacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BalanceDeComprobacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel estado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlEmpresa;
    private javax.swing.JLabel jlEstado;
    private javax.swing.JLabel lBalance;
    private javax.swing.JTable tablaComprobacion;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics graf, PageFormat pagFor, int index) throws PrinterException {
        if(index > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D hub = (Graphics2D) graf;
        hub.translate(pagFor.getImageableX() + 30, pagFor.getImageableY() + 30);
        hub.scale(0.75, 0.75);
        
        estado.printAll(graf);
        return PAGE_EXISTS;
    }
}
