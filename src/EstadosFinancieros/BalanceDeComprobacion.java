/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosFinancieros;

import static Conexion.Conexion.conectar;
import Conexion.Conexion;
import Interfaces.EstadosFinancieros;
import Otros.Cuenta;
import Otros.PeriodoContable;
import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author Alexis Lopez
 */
public class BalanceDeComprobacion extends javax.swing.JFrame {

    public BalanceDeComprobacionTableModel comprobacionTModel = new BalanceDeComprobacionTableModel();
    /**
     * Creates new form BalanceDeComprobacion
     */
    public BalanceDeComprobacion() {
        initComponents();
        
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Balance De Comprobacion");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/inicio.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/Imagenes/cafe.jpg"));
        JLabel fondo= new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        
        inicializarColumnas();
        conectar();
        consulta();
    }
    
    private void inicializarColumnas(){
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
            Statement statement = Conexion.con.createStatement();
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
            
            
            String sen = "select fechainicio, fechafinal from periodocontable order by idperiodocontable desc limit 1";
            Statement stat = Conexion.con.createStatement();
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
            
            jBalance.setText("Del " + diaInicio + " de " + mesInicio + " de " + agnoInicio + " al " + diaFinal + " de " + mesFinal + " de " + agnoFinal);
            
            
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaComprobacion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBalance = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel1.setText("Balance de Comprobacion");

        tablaComprobacion.setModel(comprobacionTModel);
        jScrollPane1.setViewportView(tablaComprobacion);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Atras");

        jBalance.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 542, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBalance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(175, 175, 175))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        EstadosFinancieros menu = new EstadosFinancieros();
        menu.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JLabel jBalance;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaComprobacion;
    // End of variables declaration//GEN-END:variables
}
