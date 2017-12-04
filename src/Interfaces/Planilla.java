/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Conexion.Conexion.conectar;
import static Conexion.Conexion.con;
import Otros.Empleado;
import Otros.DetallePlanillaTableModel;
import java.awt.BorderLayout;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Core 2
 */
public class Planilla extends javax.swing.JFrame {
    public DetallePlanillaTableModel planillaTable = new DetallePlanillaTableModel();
    DecimalFormat df = new DecimalFormat("0.00");
    private int idPlanilla;
    public Planilla(int idMaestroPlanilla) {
        initComponents();
        idPlanilla = idMaestroPlanilla;
        setLocationRelativeTo(null);
        
        inicializarColumnas();
        conectar();
        consultaPlanilla();
        
        if (getPlanillaPorId().isEstatus() == true)
        {
            btnAprobar.setEnabled(false);
            btnAprobar.setVisible(false);
        }
    }
    
    public Planilla() {
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        inicializarColumnas();
        conectar();
        consultaPlanilla();
        
        if (getPlanillaPorId().isEstatus() == true)
        {
            btnAprobar.setEnabled(false);
            btnAprobar.setVisible(false);
        }
    }
    
    private void inicializarColumnas(){
            TableColumnModel tColumnModel = new DefaultTableColumnModel();
            
            for (int i = 0; i < 14; i++) {
                TableColumn col = new TableColumn(i);
                switch (i) {
                    case 0:
                        col.setHeaderValue("Código");
                        break;
                    case 1:
                        col.setHeaderValue("Nombre");
                        break;
                    case 2:
                        col.setHeaderValue("Salario");
                        break;
                    case 3:
                        col.setHeaderValue("Eficiencia");
                        break;
                    case 4:
                        col.setHeaderValue("Años Laborados");
                        break;
                    case 5:
                        col.setHeaderValue("Sal. Deven.");
                        break;
                    case 6: 
                        col.setHeaderValue("ISSS");
                        break;
                    case 7: 
                        col.setHeaderValue("AFP");
                        break;                        
                    case 8:
                        col.setHeaderValue("Aguinaldo");
                        break;
                    case 9:
                        col.setHeaderValue("Vacaciones");
                        break;
                    case 10:
                        col.setHeaderValue("Total");
                        break;
                    case 11:
                        col.setHeaderValue("FR");
                        break;
                    case 12:
                        col.setHeaderValue("FRE");
                        break;
                    case 13:
                        col.setHeaderValue("GI");
                        break;
                    }
                tColumnModel.addColumn(col);
            }
    jTable1.setColumnModel(tColumnModel);
    }
    
    private void consultaPlanilla() {
        conectar();        
        try {
            String sentenciaSql = "SELECT `idempleado`,`iddetalleplanilla`,`aguinaldo`,"
                    + "`vacacion`,`ISSS`,`AFP`,`Devengado`,`Total`,`FactorRecargo`,"
                    + "`FactorRecargoIneficiencia`,`GastoPorIneficiencia` "
                    + "FROM `detalleplanilla` WHERE `idplanilla` = " + idPlanilla + ";";
            Statement statement = con.createStatement(HIDE_ON_CLOSE, NORMAL);
            ResultSet rs = statement.executeQuery(sentenciaSql);
            while (rs.next()) {
                Otros.DetallePlanilla dp = new Otros.DetallePlanilla();
                dp.setAFP(rs.getDouble("AFP"));
                dp.setAguinaldo(rs.getDouble("aguinaldo"));
                dp.setDevengado(rs.getDouble("Devengado"));
                dp.setEmpleado(getEmpleadoPorId(rs.getInt("idempleado")));
                dp.setFactorRecargo(rs.getDouble("FactorRecargo"));
                dp.setFactorRecargoInef(rs.getDouble("FactorRecargoIneficiencia"));
                dp.setGastoPorIneficiencia(rs.getDouble("GastoPorIneficiencia"));
                dp.setISSS(rs.getDouble("ISSS"));
                dp.setPlanilla(getPlanillaPorId());
                dp.setIdDetallePlanilla(rs.getInt("iddetalleplanilla"));
                dp.setTotal(rs.getDouble("Total"));
                dp.setVacacion(rs.getDouble("vacacion"));
                this.planillaTable.detallePlanilla.add(dp);
            }
            jTable1.updateUI();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los productos de la base de datos");
            ex.printStackTrace();
        }
    }
    
    private Empleado getEmpleadoPorId(int idempleado) {
        //conectar();
        Empleado emple = new Empleado();
         try {
             //OrdenMOModel.ordenesMO.clear();
             String sql = "SELECT IDEMPLEADO, NOMBREEMPRESA, NOMBREEMPLEADO, SALARIO"
                     + " FROM empleado WHERE IDEMPLEADO = ?";
             java.sql.PreparedStatement stat = con.prepareStatement(sql);
             stat.setInt(1, idempleado);
             ResultSet rs = stat.executeQuery(); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                
                emple.setCodigo(rs.getInt("IDEMPLEADO"));
                emple.setNombre(rs.getString("NOMBREEMPLEADO"));
                emple.setSalario(rs.getDouble("SALARIO"));
            }         
         }
         catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return emple;
    }
    
    void vaciarTabla(){
        planillaTable.detallePlanilla.clear();
        consultaPlanilla();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnAprobar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(117, 60, 17));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setBackground(new java.awt.Color(174, 99, 50));
        jTable1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jTable1.setModel(planillaTable);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("planilla en detalle");

        btnSalir.setBackground(new java.awt.Color(255, 0, 0));
        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnAprobar.setBackground(new java.awt.Color(117, 60, 17));
        btnAprobar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAprobar.setForeground(new java.awt.Color(255, 255, 255));
        btnAprobar.setText("Autorizar Planilla");
        btnAprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprobarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(jLabel5)
                        .addGap(54, 54, 54)
                        .addComponent(btnAprobar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(260, 260, 260))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btnAprobar)))
                .addGap(14, 14, 14)
                .addComponent(btnSalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        cerrar();
    }//GEN-LAST:event_formWindowClosing

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
       Planilla2 pla = new Planilla2();
        pla.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprobarActionPerformed
        // TODO add your handling code here:
                Object[] opciones = {"Aceptar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog // Sigue en la siguiente linea
                (rootPane, "¿Desea aprobar la planilla?", "Mensaje de Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            ActualizarEstatusPlanilla();
            SaldarCuentas();
            JOptionPane.showMessageDialog(this, "La planilla se ha aprobado correctamente");
            Planilla2 obj = new Planilla2();
            obj.setVisible(true);
            this.setVisible(false); 
        }

    }//GEN-LAST:event_btnAprobarActionPerformed

    public void cerrar() {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog // Sigue en la siguiente linea
                (rootPane, "¿En realidad desea cerrar la aplicacion?", "Mensaje de Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
        }
    }
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
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Planilla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAprobar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void ActualizarEstatusPlanilla() {
        conectar();        
        try {
                String sentenciaSql = "UPDATE planilla SET estatus = 1 WHERE idplanilla = " + idPlanilla;
                Statement stm = con.createStatement(HIDE_ON_CLOSE, NORMAL);
                stm.execute(sentenciaSql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar los productos de la base de datos");
            ex.printStackTrace();
        }
    }
    
    private Otros.Planilla getPlanillaPorId() {
        conectar();
        Otros.Planilla pla = new Otros.Planilla();
         try {             
             String sql = "SELECT * FROM planilla WHERE idplanilla = ?";
             java.sql.PreparedStatement stat = con.prepareStatement(sql);
             stat.setInt(1, idPlanilla);
             ResultSet rs = stat.executeQuery(); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                pla.setDescripcion(rs.getString("Descripcion"));
                pla.setEstatus(rs.getBoolean("estatus"));
                pla.setIdplanilla(idPlanilla);
                pla.setPeriodoPlanilla(rs.getDate("periodoplanilla"));
                
            }
         }
         catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return pla;
    }

    private void SaldarCuentas() {        
        try {
            String sql = "SELECT SUM(`aguinaldo`) AS aguinaldo, SUM(`vacacion`) AS vacacion, SUM(`ISSS`) AS isss, SUM(`AFP`) AS afp, SUM(`Devengado`) AS devengado, SUM(`Total`) AS total FROM `sic`.`detalleplanilla` WHERE `idplanilla` = ?;";
            Double aguinaldo = 0.0, vacacion = 0.0, isss = 0.0, afp = 0.0, devengado = 0.0, total = 0.0;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPlanilla);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                aguinaldo = rs.getDouble("aguinaldo");
                vacacion = rs.getDouble("vacacion");
                isss = rs.getDouble("isss");
                afp = rs.getDouble("afp");
                devengado = rs.getDouble("devengado");
                total = rs.getDouble("total");
            }
            
            String sqlDevengado = "SELECT IFNULL(SUM(`Devengado`), 0) AS devengado, 0 AS DeptoProduccion FROM `sic`.`detalleplanilla` D INNER JOIN `sic`.`empleado` E " +
                    "ON E.IDEMPLEADO = D.idempleado WHERE `idplanilla` = ? AND E.DeptoProduccion = 0 UNION " +
                    "SELECT IFNULL(SUM(`Devengado`), 0) AS devengado, 1 AS DeptoProduccion FROM `sic`.`detalleplanilla` D INNER JOIN `sic`.`empleado` E " +
                    "ON E.IDEMPLEADO = D.idempleado WHERE `idplanilla` = ? AND E.DeptoProduccion = 1;";
            
            Double montoMOD = 0.0;
            Double montoMOI = 0.0;
            PreparedStatement psdev = con.prepareStatement(sqlDevengado);
            psdev.setInt(1, idPlanilla);
            psdev.setInt(2, idPlanilla);
            ResultSet rsdev = psdev.executeQuery();
            while (rsdev.next())
            {
                if (rsdev.getBoolean("DeptoProduccion"))//Mano de obra directa
                    montoMOD = rsdev.getDouble("devengado");
                else
                    montoMOI = rsdev.getDouble("devengado");
            }
            
            CallableStatement cstm = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstm.setInt(1,Integer.parseInt("210301"));//codigo de la cuenta: SALARIOS... Mano de obra directa
            cstm.setString(2, "");//vCuenta
            cstm.setString(3, "");//vTipo
            cstm.setDouble(4, montoMOD);//vSDeudor
            cstm.setDouble(5,0);//vSAcreedor
            cstm.setInt(6,1);//vCantidad
            cstm.execute();
            
            CallableStatement cstmmoi = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmmoi.setInt(1,Integer.parseInt("210302"));//codigo de la cuenta: Mano de obra INdirecta
            cstmmoi.setString(2, "");//vCuenta
            cstmmoi.setString(3, "");//vTipo
            cstmmoi.setDouble(4, montoMOI);//vSDeudor
            cstmmoi.setDouble(5,0);//vSAcreedor
            cstmmoi.setInt(6,1);//vCantidad
            cstmmoi.execute();
            
            CallableStatement cstmagui = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmagui.setInt(1,Integer.parseInt("210304"));//codigo de la cuenta: aguinaldo
            cstmagui.setString(2, "");//vCuenta
            cstmagui.setString(3, "");//vTipo
            cstmagui.setDouble(4, aguinaldo);//vSDeudor
            cstmagui.setDouble(5,0);//vSAcreedor
            cstmagui.setInt(6,1);//vCantidad
            cstmagui.execute();
            
            CallableStatement cstmvaca = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmvaca.setInt(1,Integer.parseInt("210303"));//codigo de la cuenta: vacaciones
            cstmvaca.setString(2, "");//vCuenta
            cstmvaca.setString(3, "");//vTipo
            cstmvaca.setDouble(4, vacacion);//vSDeudor
            cstmvaca.setDouble(5,0);//vSAcreedor
            cstmvaca.setInt(6,1);//vCantidad
            cstmvaca.execute();
            
            CallableStatement cstmisss = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmisss.setInt(1,Integer.parseInt("210401"));//codigo de la cuenta: ISSS
            cstmisss.setString(2, "");//vCuenta
            cstmisss.setString(3, "");//vTipo
            cstmisss.setDouble(4, isss);//vSDeudor
            cstmisss.setDouble(5,0);//vSAcreedor
            cstmisss.setInt(6,1);//vCantidad
            cstmisss.execute();
            
            CallableStatement cstmafp = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmafp.setInt(1,Integer.parseInt("210402"));//codigo de la cuenta: AFP
            cstmafp.setString(2, "");//vCuenta
            cstmafp.setString(3, "");//vTipo
            cstmafp.setDouble(4, afp);//vSDeudor
            cstmafp.setDouble(5,0);//vSAcreedor
            cstmafp.setInt(6,1);//vCantidad
            cstmafp.execute();
            
            CallableStatement cstmtotal = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstmtotal.setInt(1,Integer.parseInt("410101"));//codigo de la cuenta: Gastos por salario
            cstmtotal.setString(2, "");//vCuenta
            cstmtotal.setString(3, "");//vTipo
            cstmtotal.setDouble(4, 0.0);//vSDeudor
            cstmtotal.setDouble(5,total);//vSAcreedor
            cstmtotal.setInt(6,1);//vCantidad
            cstmtotal.execute();
        } catch (Exception e) {
                e.printStackTrace();

        }
    }
}
