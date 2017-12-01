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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
public class EstadoDeCapital extends javax.swing.JFrame {
        EstadoDeCapitalTableModel inversionesTModel = new EstadoDeCapitalTableModel();
        EstadoDeCapitalTableModel desinversionesTModel = new EstadoDeCapitalTableModel();
    /**
     * Creates new form EstadoDeCapital
     */
    public EstadoDeCapital() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Balance De Comprobacion");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/inicio.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/Imagenes/cafe2.jpg"));
        JLabel fondo= new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        
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
        tablaInversiones.setColumnModel(tColumnModel);
        tablaDesinversiones.setColumnModel(tColumnModel);
        //tablaDesinversiones.getTableHeader().setVisible(false);
    }
    
    private void consulta() {
        double resultado_debe;
        double resultado_haber;
        double total;
        double totalInversiones = 0;
        double totalDesinversiones = 0;
        double capital;
        double totalIngresos = 0;
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
            String sentencia = "select codigo, nombre, debe, haber from cuenta where (estadofinanciero = 2 or estadofinanciero = 4) and (operacionenestado = 0 or operacionenestado = 1) and (debe != 0 or haber != 0)";
            Statement statement = Conexion.con.createStatement();
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
                    totalInversiones += cuenta.getSaldoAcreedor();
                    this.inversionesTModel.cuentas.add(cuenta);
                }
                else {
                    totalDesinversiones += cuenta.getSaldoDeudor();
                    this.desinversionesTModel.cuentas.add(cuenta);
                }
            }
            
            Cuenta cu = new Cuenta();
            cu.setCodigo("310201");
            cu.setNombre("Utilidad");
            
            String sentencia2 = "select debe, haber from cuenta where estadofinanciero = 1 and (debe != 0 or haber != 0)";
            Statement statement2 = Conexion.con.createStatement();
            ResultSet resultado2 = statement.executeQuery(sentencia2);
            
            while(resultado2.next()) {
                Cuenta cuenta2 = new Cuenta();
                cuenta2.setSaldoDeudor(resultado2.getDouble("debe"));
                resultado_debe = cuenta2.getSaldoDeudor();
                cuenta2.setSaldoAcreedor(resultado2.getDouble("haber"));
                resultado_haber = cuenta2.getSaldoAcreedor();
                
                cuenta2.setResultado(cuenta2.getSaldoDeudor() - cuenta2.getSaldoAcreedor());
                
                if (cuenta2.getResultado() < 0) {
                    cuenta2.setResultado(cuenta2.getResultado() * -1);
                    totalIngresos += cuenta2.getSaldoAcreedor();
                }
                else {
                    totalPerdidas += cuenta2.getSaldoDeudor();
                }
            }
            
            cu.setResultado(totalIngresos - totalPerdidas);
            if (cu.getResultado() < 0) {
                cu.setResultado(cu.getResultado() * -1);
                cu.setSaldoDeudor(cu.getResultado());
                totalDesinversiones += cu.getSaldoDeudor();
                this.desinversionesTModel.cuentas.add(cu);
            }
            else {
                cu.setSaldoAcreedor(cu.getResultado());
                totalInversiones += cu.getSaldoAcreedor();
                this.inversionesTModel.cuentas.add(cu);
            }
            
            tablaInversiones.repaint();
            tablaDesinversiones.repaint();
            
            lInversiones.setText("$" + Double.toString(totalInversiones));
            
            lDesinversiones.setText("($" + Double.toString(totalDesinversiones) + ")");
            
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
            
            lBalance.setText("Del " + diaInicio + " de " + mesInicio + " de " + agnoInicio + " al " + diaFinal + " de " + mesFinal + " de " + agnoFinal);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los datos de la base de datos");
            ex.printStackTrace();
        }
        
        capital = totalInversiones - totalDesinversiones;
        lCapital.setText("$" + Double.toString(capital));
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInversiones = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lBalance = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDesinversiones = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lCapital = new javax.swing.JLabel();
        lDesinversiones = new javax.swing.JLabel();
        lInversiones = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        estado.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Estado de Capital");

        tablaInversiones.setModel(inversionesTModel);
        jScrollPane1.setViewportView(tablaInversiones);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Inversiones");

        lBalance.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBalance.setText("Estado");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Total Inversiones");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Total Desinversiones");

        tablaDesinversiones.setModel(desinversionesTModel);
        jScrollPane3.setViewportView(tablaDesinversiones);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Desinversiones");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Capital social");

        lCapital.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lCapital.setText("Capital");

        lDesinversiones.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lDesinversiones.setText("Desinversiones");

        lInversiones.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lInversiones.setText("Inversiones");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Atras");

        javax.swing.GroupLayout estadoLayout = new javax.swing.GroupLayout(estado);
        estado.setLayout(estadoLayout);
        estadoLayout.setHorizontalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                            .addComponent(lBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, estadoLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lCapital))
                                .addGroup(estadoLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(461, 461, 461)
                                    .addComponent(lInversiones))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, estadoLayout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lDesinversiones))
                                .addComponent(jScrollPane1)))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estadoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        estadoLayout.setVerticalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lBalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lInversiones))
                .addGap(34, 34, 34)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lDesinversiones))
                .addGap(18, 18, 18)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lCapital))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        EstadosFinancieros obj = new EstadosFinancieros();
        obj.setVisible(true);
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
            java.util.logging.Logger.getLogger(EstadoDeCapital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstadoDeCapital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstadoDeCapital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstadoDeCapital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EstadoDeCapital().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel estado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lBalance;
    private javax.swing.JLabel lCapital;
    private javax.swing.JLabel lDesinversiones;
    private javax.swing.JLabel lInversiones;
    private javax.swing.JTable tablaDesinversiones;
    private javax.swing.JTable tablaInversiones;
    // End of variables declaration//GEN-END:variables
}
