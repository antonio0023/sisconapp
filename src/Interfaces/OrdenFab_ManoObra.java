/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Conexion.Conexion.con;
import static Conexion.Conexion.conectar;
import Otros.Empleado;
import Otros.OrdenFabricacion;
import Otros.EmpleadosComboModel;
import Otros.OrdenManoObraTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import static java.lang.Math.round;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Ilich Daniel Morales
 */
public class OrdenFab_ManoObra extends javax.swing.JFrame {

    public OrdenManoObraTableModel OrdenMOModel = new OrdenManoObraTableModel();
    public EmpleadosComboModel empleadosTM = new EmpleadosComboModel();
    private int idOrdenFabricacion;
    
    public OrdenFab_ManoObra(int idOrden) {        
        initComponents();
        idOrdenFabricacion = idOrden;        
        llenarEmpleados();        
        Inicializar();
        inicializarColumnas();
        consultaInicial();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        this.setTitle("Mano de Obra");
        
    }
    
    public void Inicializar() {
        txtCantidadHoras.setText("");
        txtMonto.setText("");
        
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fechahoy = Calendar.getInstance();
        Dimension size = txtFecha.getCalendarPreferredSize();
        size.width += 90;
        txtFecha.setCalendarPreferredSize(size);
        txtFecha.setText(formatoFecha.format(fechahoy.getTime()));
        jLabel11.setText(getOrdenFabricacionPorId().getNombre());
        ddlEmpleado.setModel(empleadosTM);
        if (empleadosTM.getSize() > 0)
            ddlEmpleado.setSelectedIndex(0);
        
        if (getOrdenFabricacionPorId().getAbierta() == 0)
        {
            jButton2.setEnabled(false);
            txtFecha.setEnabled(false);
            ddlEmpleado.setEnabled(false);
            txtCantidadHoras.setEnabled(false);            
        }
    }
    
    private void inicializarColumnas() {
        TableColumnModel tColumnModel = new DefaultTableColumnModel();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 0; i < 4; i++) {
            TableColumn col = new TableColumn(i);
            switch (i) {
                case 0:
                    col.setHeaderValue("Empleado");
                    break;
                case 1:
                    col.setHeaderValue("Horas");
                    break;
                case 2:
                    col.setHeaderValue("Monto");
                    col.setCellRenderer(rightRenderer);
                    break;                    
                case 3:
                    col.setHeaderValue("Fecha");
                    break;
            }
            tColumnModel.addColumn(col);
        }
        tablaMod.setColumnModel(tColumnModel);
    }
    
    private void llenarEmpleados() {
        conectar();
         try {
             OrdenMOModel.ordenesMO.clear();
             String sql = "SELECT IDEMPLEADO, NOMBREEMPRESA, NOMBREEMPLEADO, SALARIO"
                     + " FROM empleado WHERE DeptoProduccion = 1";
             java.sql.PreparedStatement stat = con.prepareStatement(sql);
             ResultSet rs = stat.executeQuery(); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                Otros.Empleado emple = new Otros.Empleado();
                emple.setCodigo(rs.getInt("IDEMPLEADO"));
                emple.setNombre(rs.getString("NOMBREEMPLEADO"));
                emple.setSalario(rs.getDouble("SALARIO"));
                this.empleadosTM.empleados.add(emple);                
            }
         }
         catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private OrdenFabricacion getOrdenFabricacionPorId() {
        conectar();
        OrdenFabricacion fab = new OrdenFabricacion();
         try {             
             String sql = "SELECT idorden, noorden, codigo, nombre, fechacrea, saldoactual, abierta, materiaprima, manodeobra, costoindirectofab,"
                     + "porcentajemod, porcentajemp, cantidad, costounitario"
                     + " FROM ordenfabricacion WHERE idorden = ?";
             java.sql.PreparedStatement stat = con.prepareStatement(sql);
             stat.setInt(1, idOrdenFabricacion);
             ResultSet rs = stat.executeQuery(); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                fab.setAbierta(rs.getInt("abierta"));
                fab.setCif(rs.getDouble("costoindirectofab"));
                fab.setCodigo(rs.getInt("codigo"));
                fab.setFecha(rs.getString("fechacrea"));
                fab.setIdorden(rs.getInt("idorden"));
                fab.setManodeobra(rs.getDouble("manodeobra"));
                fab.setMateriaprima(rs.getDouble("materiaprima"));
                fab.setNombre(rs.getString("nombre"));
                fab.setNoorden(rs.getInt("noorden"));
                fab.setSaldoactual(rs.getDouble("saldoactual"));
                fab.setPorcentajeMOD(rs.getInt("porcentajemod"));
                fab.setPorcentajeMP(rs.getInt("porcentajemp"));
                fab.setCantidad(rs.getInt("cantidad"));
                fab.setPrecio(rs.getDouble("costounitario"));
            }
         }
         catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return fab;
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
    
    private void consultaInicial() {
        conectar();
        try {
            OrdenMOModel.ordenesMO.clear();
            String sql = "SELECT idordenmod, idorden, idempleado, horas, monto, fecha"
                    + " FROM orden_manoobra WHERE idorden = ?";
            java.sql.PreparedStatement stat = con.prepareStatement(sql);
            stat.setInt(1, idOrdenFabricacion);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                Otros.OrdenFab_ManoObra ordenmod = new Otros.OrdenFab_ManoObra();
                ordenmod.setFecha(rs.getDate("fecha"));
                ordenmod.setHoras(rs.getDouble("horas"));
                ordenmod.setIdempleado(getEmpleadoPorId(rs.getInt("idempleado")));
                ordenmod.setIdorden(getOrdenFabricacionPorId());
                ordenmod.setMonto(rs.getDouble("monto"));
                this.OrdenMOModel.ordenesMO.add(ordenmod);
                //System.out.println("añadir: " + getEmpleadoPorId(rs.getInt("idempleado")) + " de " + OrdenMOModel.ordenesMO.size());
            }
            tablaMod.updateUI();
            tablaMod.repaint();//Actualiza la tabla 
        } catch (SQLException e) {
            //System.out.println("OCURRIÓ UN ERROR");
            e.printStackTrace();
        }
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMod = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        ddlEmpleado = new javax.swing.JComboBox<>();
        txtFecha = new datechooser.beans.DateChooserCombo();
        jSeparator6 = new javax.swing.JSeparator();
        txtCantidadHoras = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(152, 190, 211));

        jPanel1.setBackground(new java.awt.Color(117, 60, 17));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setBackground(new java.awt.Color(117, 60, 17));
        jLabel10.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MANO DE OBRA");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setOpaque(true);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 22, 482, -1));

        jLabel1.setBackground(new java.awt.Color(117, 60, 17));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Empleado:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 100, 76, 24));

        jLabel2.setBackground(new java.awt.Color(117, 60, 17));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cantidad de horas:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 90, -1, 44));

        jLabel3.setBackground(new java.awt.Color(117, 60, 17));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Monto:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 154, 52, 27));

        jLabel4.setBackground(new java.awt.Color(117, 60, 17));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fecha:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 149, 52, 27));

        tablaMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        tablaMod.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tablaMod.setModel(OrdenMOModel);
        jScrollPane1.setViewportView(tablaMod);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 240, 503, 190));

        jLabel11.setBackground(new java.awt.Color(117, 60, 17));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("OF");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setOpaque(true);
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 60, 572, -1));

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cerrar");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 86, -1));

        jButton2.setBackground(new java.awt.Color(117, 60, 17));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 211, 86, -1));

        ddlEmpleado.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ddlEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(ddlEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 103, 168, -1));

        txtFecha.setLocale(new java.util.Locale("es", "SV", ""));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 154, 168, -1));

        jSeparator6.setBackground(new java.awt.Color(73, 181, 172));
        jSeparator6.setForeground(new java.awt.Color(73, 181, 172));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1073, 148, 1, -1));

        txtCantidadHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadHorasActionPerformed(evt);
            }
        });
        txtCantidadHoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadHorasKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidadHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 44, -1));

        txtMonto.setEditable(false);
        txtMonto.setBackground(new java.awt.Color(255, 250, 250));
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });
        jPanel1.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 44, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cafee.jpg"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CrearOrdenFab obj = new CrearOrdenFab();
        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Empleado emp = (Empleado)ddlEmpleado.getSelectedItem();
        Double horas = Double.parseDouble(txtCantidadHoras.getText());
        Double monto = Double.parseDouble(txtMonto.getText());
        Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMOD() / 100.0;
        try {
            CallableStatement cstm = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstm.setInt(1,Integer.parseInt("210301"));//codigo de la cuenta: SALARIOS
            cstm.setString(2, "");//vCuenta
            cstm.setString(3, "");//vTipo
            cstm.setDouble(4, 0);//vSDeudor
            cstm.setDouble(5,monto);//vSAcreedor
            cstm.setInt(6,1);//vCantidad
            cstm.execute();
        } catch (Exception e) {
                e.printStackTrace();

        }
        
        try {
            //Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMOD() / 100.0;
            String codigoOF = "1109";
            if (Integer.toString(getOrdenFabricacionPorId().getNoorden()).length() == 1)
                codigoOF += "0";
            codigoOF += Integer.toString(getOrdenFabricacionPorId().getNoorden());
            
            CallableStatement cstm = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
            cstm.setInt(1,Integer.parseInt(codigoOF));//codigo de la cuenta: Orden de fabricación
            cstm.setString(2, "");//vCuenta
            cstm.setString(3, "");//vTipo
            //cstm.setDouble(4, monto);//vSDeudor
            cstm.setDouble(4,monto + (monto * porcentaje));
            cstm.setDouble(5, 0);//vSAcreedor
            cstm.setInt(6,1);//vCantidad
            cstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try
        {
            
            DateFormat dffrom = new SimpleDateFormat("MM-dd-yy");
            DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");  
            Date today;
            today = dffrom.parse(txtFecha.getText());
            String fecha = dfto.format(today);
            
            String sqlInsert = "INSERT INTO `orden_manoobra`(`idordenmod`, `idorden`, `idempleado`, `horas`, `monto`, `fecha`) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            java.sql.PreparedStatement statI = con.prepareStatement(sqlInsert);
            statI.setNull(1, java.sql.Types.INTEGER);
            statI.setInt(2, idOrdenFabricacion);
            statI.setInt(3, emp.getCodigo());
            statI.setDouble(4, horas);
            statI.setDouble(5, monto);
            statI.setString(6, fecha);            
            statI.execute();
            
            String sqlUpdate = "UPDATE `ordenfabricacion` SET `manodeobra` = `manodeobra` + ?,"
                    + "`costoindirectofab` = `costoindirectofab` + ?,"
                    + "`saldoactual` = `saldoactual` + ? "
                    + "WHERE idorden = ?";
            java.sql.PreparedStatement statU = con.prepareStatement(sqlUpdate);
            statU.setDouble(1, monto);
            //Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMOD() / 100.0;
            statU.setDouble(2, monto * porcentaje);
            statU.setDouble(3, monto + (monto * porcentaje));
            statU.setInt(4, idOrdenFabricacion);            
            statU.execute();
            //JOptionPane.showMessageDialog(this, "Registro añadido correctamente");
            consultaInicial();
            //Inicializar();
            con.close();
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(OrdenFab_ManoObra.class.getName()).log(Level.SEVERE, "No se pudo convertir el valor de fecha", ex);
        }
        
        //Actualiza haber de Variación de CIF
        conectar(); 
             try{
                // Preparamos la consulta
                Statement stat = con.createStatement();
                stat.execute("UPDATE cuenta SET HABER=HABER +'"+(monto * porcentaje)+"' WHERE CODIGO=110801");
                stat.close();
                con.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }
             
        // Se agrega un CIF a la orden de Fabricación en base a la mano de obra
        /*try{
                conectar();
                Statement stat = conect.createStatement();
                double cif=1.5; //cif de prueba
                double montoCIF=cif*monto;
                stat.execute("UPDATE ordenfabricacion SET costoindirectofab=costoindirectofab +'"+montoCIF+"',saldoactual=saldoactual +'"+montoCIF+"' WHERE idorden='"+idOrdenFabricacion+"'");
                stat.close();
                conect.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }*/
        tablaMod.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ddlEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlEmpleadoActionPerformed
        CalcularMontoTotal();
    }//GEN-LAST:event_ddlEmpleadoActionPerformed

    private void txtCantidadHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadHorasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadHorasActionPerformed

    private void txtCantidadHorasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadHorasKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if(Character.isLetter(c)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se admiten números","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCantidadHorasKeyTyped

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoKeyTyped
    
    private void CalcularMontoTotal()
    {
        if (txtCantidadHoras.getText().length() > 0)
        {
            Double horas;
            Empleado emp = (Empleado)ddlEmpleado.getSelectedItem();
            horas = Double.parseDouble(txtCantidadHoras.getText());
            txtMonto.setText(Double.toString(round(emp.getSalario() / 240 * horas * 100) / 100.0));
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
            java.util.logging.Logger.getLogger(OrdenFab_ManoObra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_ManoObra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_ManoObra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_ManoObra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearOrdenFab().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ddlEmpleado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tablaMod;
    private javax.swing.JTextField txtCantidadHoras;
    private datechooser.beans.DateChooserCombo txtFecha;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
