/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosFinancieros;

import Interfaces.EstadosFinancieros;
import Conexion.Conexion;
import static Conexion.Conexion.conectar;
import Otros.Cuenta;
import Otros.PeriodoContable;
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
public class EstadoDeResultados extends javax.swing.JFrame {
    EstadoDeResultadosTableModel gananciasTModel = new EstadoDeResultadosTableModel();
    EstadoDeResultadosTableModel perdidasTModel = new EstadoDeResultadosTableModel();
    /**
     * Creates new form EstadoDeResultados
     */
    public EstadoDeResultados() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Estado De Resultados");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/inicio.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/Imagenes/cafe.png"));
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
        tablaGanancias.setColumnModel(tColumnModel);
        tablaPerdidas.setColumnModel(tColumnModel);
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
            String sentencia = "select codigo, nombre, debe, haber from cuenta where estadofinanciero = 4 and (operacionenestado = 0 or operacionenestado = 1) and (debe != 0 or haber != 0)";
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
                
                if (cuenta.getResultado() > 0) {
                    cuenta.setResultado(cuenta.getResultado() * -1);
                    totalGanancias += cuenta.getSaldoAcreedor();
                    this.gananciasTModel.cuentas.add(cuenta);
                    tablaGanancias.repaint();
                }
                else {
                    totalPerdidas += cuenta.getSaldoDeudor();
                    this.perdidasTModel.cuentas.add(cuenta);
                    //tablaPerdidas.repaint();
                }
            }
            
            tablaGanancias.repaint();
            tablaPerdidas.repaint();
            lIngresos.setText("$" + Double.toString(totalGanancias) + "");
            lEgresos.setText("($" + Double.toString(totalPerdidas) + ")");
            
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

        jLabel1 = new javax.swing.JLabel();
        lBalance = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGanancias = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lIngresos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPerdidas = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lEgresos = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lUtilidad = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel1.setText("Estado de Resultados");

        lBalance.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lBalance.setText("Estado");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Ingresos");

        tablaGanancias.setModel(gananciasTModel);
        jScrollPane1.setViewportView(tablaGanancias);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Total Ingresos");

        lIngresos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lIngresos.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Egresos");

        tablaPerdidas.setModel(perdidasTModel);
        jScrollPane2.setViewportView(tablaPerdidas);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Egresos");

        lEgresos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lEgresos.setForeground(new java.awt.Color(255, 255, 255));
        lEgresos.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Utilidad");

        lUtilidad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lUtilidad.setForeground(new java.awt.Color(255, 255, 255));
        lUtilidad.setText("jLabel4");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Atras");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lIngresos))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lUtilidad))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lEgresos))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(lBalance)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lBalance)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lIngresos))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lEgresos))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lUtilidad))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EstadosFinancieros menu = new EstadosFinancieros();
        menu.setVisible(true);
        this.setVisible(false);
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
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstadoDeResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EstadoDeResultados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lBalance;
    private javax.swing.JLabel lEgresos;
    private javax.swing.JLabel lIngresos;
    private javax.swing.JLabel lUtilidad;
    private javax.swing.JTable tablaGanancias;
    private javax.swing.JTable tablaPerdidas;
    // End of variables declaration//GEN-END:variables
}
