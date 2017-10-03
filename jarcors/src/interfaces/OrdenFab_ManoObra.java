/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import static BDContabilidad.Conexion.conect;
import static BDContabilidad.Conexion.conectar;
import ModeloContabilidad.Empleado;
import ModeloContabilidad.OrdenFabricacion;
import OrdenFabricacion.EmpleadosComboModel;
import OrdenFabricacion.OrdenManoObraTableModel;
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
             java.sql.PreparedStatement stat = conect.prepareStatement(sql);
             ResultSet rs = stat.executeQuery(); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                ModeloContabilidad.Empleado emple = new ModeloContabilidad.Empleado();
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
             java.sql.PreparedStatement stat = conect.prepareStatement(sql);
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
             java.sql.PreparedStatement stat = conect.prepareStatement(sql);
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
            java.sql.PreparedStatement stat = conect.prepareStatement(sql);
            stat.setInt(1, idOrdenFabricacion);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                ModeloContabilidad.OrdenFab_ManoObra ordenmod = new ModeloContabilidad.OrdenFab_ManoObra();
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
        txtCantidadHoras = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        txtFecha = new datechooser.beans.DateChooserCombo();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(152, 190, 211));

        jPanel1.setBackground(new java.awt.Color(152, 190, 211));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jLabel10.setBackground(new java.awt.Color(101, 151, 179));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MANO DE OBRA");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Empleado:");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cantidad de horas:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Monto:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fecha:");

        tablaMod.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tablaMod.setModel(OrdenMOModel);
        jScrollPane1.setViewportView(tablaMod);

        jLabel11.setBackground(new java.awt.Color(101, 151, 179));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("OF");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setOpaque(true);

        jButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        ddlEmpleado.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ddlEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlEmpleadoActionPerformed(evt);
            }
        });

        txtCantidadHoras.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtCantidadHoras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadHorasFocusLost(evt);
            }
        });
        txtCantidadHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadHorasActionPerformed(evt);
            }
        });

        txtMonto.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtMonto.setEnabled(false);
        txtMonto.setFocusable(false);
        txtMonto.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(ddlEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCantidadHoras, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(txtMonto)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(125, 125, 125)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(114, 114, 114)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ddlEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidadHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CrearOrdenFab obj = new CrearOrdenFab();
        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCantidadHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadHorasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadHorasActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Empleado emp = (Empleado)ddlEmpleado.getSelectedItem();
        Double horas = Double.parseDouble(txtCantidadHoras.getText());
        Double monto = Double.parseDouble(txtMonto.getText());
        Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMOD() / 100.0;
        try {
            CallableStatement cstm = conect.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
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
            
            CallableStatement cstm = conect.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
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
            java.sql.PreparedStatement statI = conect.prepareStatement(sqlInsert);
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
            java.sql.PreparedStatement statU = conect.prepareStatement(sqlUpdate);
            statU.setDouble(1, monto);
            //Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMOD() / 100.0;
            statU.setDouble(2, monto * porcentaje);
            statU.setDouble(3, monto + (monto * porcentaje));
            statU.setInt(4, idOrdenFabricacion);            
            statU.execute();
            //JOptionPane.showMessageDialog(this, "Registro añadido correctamente");
            consultaInicial();
            //Inicializar();
            conect.close();
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
                Statement stat = conect.createStatement();
                stat.execute("UPDATE cuenta SET HABER=HABER +'"+(monto * porcentaje)+"' WHERE CODIGO=110801");
                stat.close();
                conect.close();
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
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCantidadHorasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadHorasFocusLost
        CalcularMontoTotal();
    }//GEN-LAST:event_txtCantidadHorasFocusLost

    private void ddlEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlEmpleadoActionPerformed
        CalcularMontoTotal();
    }//GEN-LAST:event_ddlEmpleadoActionPerformed
    
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMod;
    private javax.swing.JTextField txtCantidadHoras;
    private datechooser.beans.DateChooserCombo txtFecha;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
