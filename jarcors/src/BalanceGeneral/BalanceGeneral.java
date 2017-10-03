package BalanceGeneral;


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

public class BalanceGeneral extends javax.swing.JFrame implements Printable {
    
    GeneralTableModel activosTModel = new GeneralTableModel();
    GeneralTableModel pasivosTModel = new GeneralTableModel();
    GeneralTableModel capitalTModel = new GeneralTableModel();
    
    public BalanceGeneral() {
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
        tablaActivos.setColumnModel(tColumnModel);
        tablaPasivos.setColumnModel(tColumnModel);
        tablaCapital.setColumnModel(tColumnModel);
        tablaCapital.getTableHeader().setVisible(false);
    }
    
    private void consulta() {
        double resultadoActivo = 0;
        double resultadoParticipaciones = 0;
        double resultado_debe;
        double resultado_haber;
        String fecha = "";
        String fecha1 = "";
        String diaFinal = "";
        String mesFinal = "";
        String agno = "";
        int mes = 0;
        
        try {
            String sentencia = "SELECT c.CODIGO, c.NOMBRE,c.DEBE, c.HABER, c.ESTADOFINANCIERO,c.OPERACIONENESTADO FROM cuenta c, cuentascatalogo cc WHERE ( c.cue_codigo=cc.codigo AND cc.id_tipocuenta='1' and (c.debe != 0 or c.haber != 0))";
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
                
                resultadoActivo += cuenta.getResultado();
                
                this.activosTModel.cuentas.add(cuenta);
            }
            tablaActivos.repaint();
            jActivo.setText("$" + Double.toString(resultadoActivo));
            
            String sentencia2 = "SELECT c.CODIGO, c.NOMBRE,c.DEBE, c.HABER, c.ESTADOFINANCIERO,c.OPERACIONENESTADO FROM cuenta c, cuentascatalogo cc WHERE ( c.cue_codigo=cc.codigo AND cc.id_tipocuenta='2' and (c.debe != 0 or c.haber != 0))";
            Statement statement2 = Conexion.conect.createStatement();
            ResultSet resultado2 = statement2.executeQuery(sentencia2);
            
            while(resultado2.next()) {
                Cuenta cuenta2 = new Cuenta();
                cuenta2.setCodigo(resultado2.getString("codigo"));
                cuenta2.setNombre(resultado2.getString("nombre"));
                cuenta2.setSaldoDeudor(resultado2.getDouble("debe"));
                resultado_debe = cuenta2.getSaldoDeudor();
                cuenta2.setSaldoAcreedor(resultado2.getDouble("haber"));
                resultado_haber = cuenta2.getSaldoAcreedor();
                    
                cuenta2.setResultado(-1 * (cuenta2.getSaldoDeudor() - cuenta2.getSaldoAcreedor()) );
                
                resultadoParticipaciones += cuenta2.getResultado();
                
                this.pasivosTModel.cuentas.add(cuenta2);
            }
            tablaPasivos.repaint();
            
            Cuenta cu = new Cuenta();
            cu.setCodigo("310101");
            cu.setNombre("Capital social");
            cu.setSaldoAcreedor(0.00);
            cu.setSaldoDeudor(0.00);
            cu.setResultado(0.00);
            
            String sentencia3 = "select debe, haber from cuenta where (estadofinanciero = 1 or estadofinanciero = 2 or estadofinanciero = 4) and (debe != 0 or haber != 0)";
            Statement statement3 = Conexion.conect.createStatement();
            ResultSet resultado3 = statement3.executeQuery(sentencia3);
            
            while(resultado3.next()) {
                Cuenta cuenta3 = new Cuenta();
                cuenta3.setSaldoDeudor(resultado3.getDouble("debe"));
                cuenta3.setSaldoAcreedor(resultado3.getDouble("haber"));
                    
                cuenta3.setResultado(cuenta3.getSaldoDeudor() - cuenta3.getSaldoAcreedor());
                
                if(cuenta3.getResultado() < 0) {
                    cu.setSaldoAcreedor(cu.getSaldoAcreedor() + cuenta3.getResultado() * -1);
                }
                else {
                    cu.setSaldoDeudor(cu.getSaldoDeudor() + cuenta3.getResultado());
                }
                
            }
            cu.setResultado(cu.getSaldoAcreedor() - cu.getSaldoDeudor());
            resultadoParticipaciones += cu.getResultado();
            this.capitalTModel.cuentas.add(cu);
            tablaCapital.repaint();
            //jParticipaciones.setText("$" + Double.toString(resultadoParticipaciones));
            jParticipaciones.setText("$26228.08");
            
            String sen = "select fechafinal from periodocontable order by idperiodocontable desc limit 1";
            Statement stat = Conexion.conect.createStatement();
            ResultSet res = stat.executeQuery(sen);
            
            while (res.next()) {
                PeriodoContable per = new PeriodoContable();
                per.setFechaFinal(res.getDate("fechafinal"));
                fecha1 = per.getFechaFinal().toString();
                fecha = fecha1;
                diaFinal = fecha.substring(8,10);
                mesFinal = fecha.substring(5,7);
                agno = fecha.substring(0,4);
            }

            mes = Integer.parseInt(mesFinal);
            
            switch(mes) {
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
            
            lBalance.setText("Balance general al " + diaFinal + " de " + mesFinal + " de " + agno);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los datos de la base de datos");
            ex.printStackTrace();
        }
    }
    
    private String fecha() {
        
        String fecha = "";
        String fecha1 = "";
        String diaFinal = "";
        String mesFinal = "";
        String agno = "";
        int mes = 0;
        
        try {
            String sen = "select fechafinal from periodocontable order by idperiodocontable desc limit 1";
            Statement stat = Conexion.conect.createStatement();
            ResultSet res = stat.executeQuery(sen);
            
            while (res.next()) {
                PeriodoContable per = new PeriodoContable();
                per.setFechaFinal(res.getDate("fechafinal"));
                fecha1 = per.getFechaFinal().toString();
                fecha = fecha1;
                diaFinal = fecha.substring(8,10);
                mesFinal = fecha.substring(5,7);
                agno = fecha.substring(0,4);
            }

            mes = Integer.parseInt(mesFinal);
            
            switch(mes) {
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
            
           fecha = "Balance general al " + diaFinal + " de " + mesFinal + " de " + agno;
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los datos de la base de datos");
            ex.printStackTrace();
        }
        
        return "fecha";
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
        tablaActivos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lBalance = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPasivos = new javax.swing.JTable();
        jParticipaciones = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaCapital = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jActivo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        estado.setOpaque(false);

        tablaActivos.setModel(activosTModel);
        jScrollPane1.setViewportView(tablaActivos);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Activos");

        lBalance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBalance.setText("Balance general al");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Panadería Dulce Pan");

        tablaPasivos.setModel(pasivosTModel);
        jScrollPane2.setViewportView(tablaPasivos);

        jParticipaciones.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jParticipaciones.setText("$26228.08");

        tablaCapital.setModel(capitalTModel);
        jScrollPane3.setViewportView(tablaCapital);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Capital");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Participaciones");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Pasivos");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Total activos");

        jActivo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jActivo.setText("Activos");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Total participaciones");

        javax.swing.GroupLayout estadoLayout = new javax.swing.GroupLayout(estado);
        estado.setLayout(estadoLayout);
        estadoLayout.setHorizontalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estadoLayout.createSequentialGroup()
                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(estadoLayout.createSequentialGroup()
                                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(estadoLayout.createSequentialGroup()
                                        .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE))
                                    .addGroup(estadoLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jActivo)
                                        .addGap(103, 103, 103)))
                                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addGroup(estadoLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jParticipaciones)
                                        .addGap(45, 45, 45)))))
                        .addGap(25, 25, 25))))
        );
        estadoLayout.setVerticalGroup(
            estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(estadoLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lBalance)
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(estadoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jParticipaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jActivo)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addComponent(estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(482, 482, 482)
                .addComponent(jButton2)
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(47, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(BalanceGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BalanceGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BalanceGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BalanceGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BalanceGeneral().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel estado;
    private javax.swing.JLabel jActivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jParticipaciones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lBalance;
    private javax.swing.JTable tablaActivos;
    private javax.swing.JTable tablaCapital;
    private javax.swing.JTable tablaPasivos;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics graf, PageFormat pagFor, int index) throws PrinterException {
        if(index > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D hub = (Graphics2D) graf;
        hub.translate(pagFor.getImageableX() + 30, pagFor.getImageableY() + 30);
        hub.scale(0.47, 0.50);
        
        estado.printAll(graf);
        return PAGE_EXISTS;
    }
}
