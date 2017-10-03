package EstadoDeResultados;

import BDContabilidad.Conexion;
import static BDContabilidad.Conexion.conectar;
import ModeloContabilidad.Cuenta;
import ModeloContabilidad.PeriodoContable;
import interfaces.EstadosFinancieros;
import interfaces.PanelImagen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.print.*;

public class EstadoDeResultados extends javax.swing.JFrame implements Printable {
    
    ResultadoTableModel gananciasTModel = new ResultadoTableModel();
    ResultadoTableModel perdidasTModel = new ResultadoTableModel();

    public EstadoDeResultados() {
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
     
    private void inicializarColumnas() {
        
        TableColumnModel tColumnModel = new DefaultTableColumnModel();
        
        for(int i = 0; i < 3; i++) {
            TableColumn col = new TableColumn(i);
            
            switch(i) {
                case 0: col.setHeaderValue("Codigo");
                    break;
                case 1: col.setHeaderValue("Nombre");
                    break;
                case 2: col.setHeaderValue("Saldo ($)");
                    break;
            }
            
            tColumnModel.addColumn(col);
        }
        tablaGanancias.setColumnModel(tColumnModel);
        tablaPerdidas.setColumnModel(tColumnModel);
        tablaPerdidas.getTableHeader().setVisible(false);
    }
    
    private void consulta() { 
        double resultado_debe;
        double resultado_haber;
        double total;
        double totalGanancias = 0;
        double totalPerdidas = 0;
        double utilidad;
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
            String sentencia = "select codigo, nombre, debe, haber from cuenta where estadofinanciero = 1 and (operacionenestado = 0 or operacionenestado = 1) and (debe != 0 or haber != 0)";
            Statement statement = Conexion.conect.createStatement();
            ResultSet resultado = statement.executeQuery(sentencia);
            
            while(resultado.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(resultado.getString("codigo"));
                cuenta.setNombre(resultado.getString("nombre"));
                cuenta.setSaldoDeudor(resultado.getDouble("debe"));
                resultado_debe = cuenta.getSaldoDeudor();
                cuenta.setSaldoAcreedor(resultado.getDouble("haber"));
                resultado_haber = cuenta.getSaldoAcreedor();
                
                cuenta.setResultado(cuenta.getSaldoDeudor() - cuenta.getSaldoAcreedor());
                
                if (cuenta.getResultado() < 0) {
                    cuenta.setResultado(cuenta.getResultado() * -1);
                    totalGanancias += cuenta.getSaldoAcreedor();
                    this.gananciasTModel.cuentas.add(cuenta);
                }
                else {
                    totalPerdidas += cuenta.getSaldoDeudor();
                    this.perdidasTModel.cuentas.add(cuenta);
                }
            }
            
            tablaPerdidas.repaint();
             lIngresos.setText("$" + Double.toString(totalGanancias) + "");
            lEgresos.setText("($" + Double.toString(totalPerdidas) + ")");
            
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
        
        utilidad = totalGanancias - totalPerdidas;
        lUtilidad.setText("$" + Double.toString(utilidad));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGanancias = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPerdidas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lIngresos = new javax.swing.JLabel();
        lEgresos = new javax.swing.JLabel();
        lUtilidad = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lBalance = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        estado.setOpaque(false);

        tablaGanancias.setModel(gananciasTModel);
        jScrollPane1.setViewportView(tablaGanancias);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Ingresos");

        tablaPerdidas.setModel(perdidasTModel);
        jScrollPane2.setViewportView(tablaPerdidas);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Egresos");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Total ingresos");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Total Egresos");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Utilidad");

        lIngresos.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        lIngresos.setText("Ingresos");

        lEgresos.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        lEgresos.setText("Egresos");

        lUtilidad.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        lUtilidad.setText("Total");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Panadería Dulce Pan");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Estado de Resultados");

        lBalance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBalance.setText("Balance");

        javax.swing.GroupLayout estadoLayout = new javax.swing.GroupLayout(estado);
        estado.setLayout(estadoLayout);
        estadoLayout.setHorizontalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estadoLayout.createSequentialGroup()
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(estadoLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lUtilidad))
                            .addGroup(estadoLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lEgresos)))
                        .addGap(70, 70, 70))
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(estadoLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lIngresos))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(25, Short.MAX_VALUE))))
        );
        estadoLayout.setVerticalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lBalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lIngresos))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lEgresos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lUtilidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(30, 30, 30))
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
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EstadoDeResultados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel estado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lBalance;
    private javax.swing.JLabel lEgresos;
    private javax.swing.JLabel lIngresos;
    private javax.swing.JLabel lUtilidad;
    private javax.swing.JTable tablaGanancias;
    private javax.swing.JTable tablaPerdidas;
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
