/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import static BDContabilidad.Conexion.conect;
import static BDContabilidad.Conexion.conectar;
import ModeloContabilidad.OrdenFabricacion;
import OrdenFabricacion.OrdenTableModel;
import java.awt.Dimension;

import static java.awt.Frame.NORMAL;
//import java.awt.Point;
import java.sql.CallableStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Cristian
 */
public class CrearOrdenFab extends javax.swing.JFrame {
String cod;
int cueCod;
String nombre;
int estadoF;
int oper;
String numOrden;
String saldoA;
String fecha;
String orden;
String mp;
String mo;
String cif;
public OrdenTableModel ordenesTM = new OrdenTableModel();

    /**
     * Creates new form CrearOrdenFab
     */
    public CrearOrdenFab() {
        initComponents();
        Inicializar();
        inicializarColumnas();
        consultaInicial();
    }
    
    public void Inicializar() {
        numOrdentxt.setText("");
        //saldoAtxt.setText("0.00");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fechahoy = Calendar.getInstance();
        Dimension size = txtfecha.getCalendarPreferredSize();
        size.width += 90;
        txtfecha.setCalendarPreferredSize(size);
        txtfecha.setText(formatoFecha.format(fechahoy.getTime()));
        //fechatxt.setText("");
        //ordentxt.setText("");
        //MPtxt.setText("0.00");
        //MOtxt.setText("0.00");
        //CIFtxt.setText("0.00");
        nombretxt.setText("");
        
    }
    
    private void inicializarColumnas() {
        TableColumnModel tColumnModel = new DefaultTableColumnModel();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 0; i < 10; i++) {
            TableColumn col = new TableColumn(i);
            switch (i) {
                case 0:
                    col.setHeaderValue("Codigo");
                    break;
                case 1:
                    col.setHeaderValue("Nombre");
                    col.setPreferredWidth(110);
                    break;
                /*case 2:
                    col.setHeaderValue("N°");
                    break;*/
                case 2:
                    col.setHeaderValue("Fecha");
                    break;
                case 3:
                    col.setHeaderValue("Saldo");
                    col.setCellRenderer(rightRenderer);
                    break;
                case 4:
                    col.setHeaderValue("MP");
                    col.setPreferredWidth(40);
                    col.setCellRenderer(rightRenderer);
                    break;
                case 5:
                    col.setHeaderValue("MO");
                    col.setCellRenderer(rightRenderer);
                    col.setPreferredWidth(40);
                    break;
                case 6:
                    col.setHeaderValue("CIF");
                    col.setPreferredWidth(40);
                    col.setCellRenderer(rightRenderer);
                    break;
                case 7:
                    col.setHeaderValue("Estado");
                    break;
                case 8:
                    col.setHeaderValue("Cant.");
                    col.setPreferredWidth(40);
                    break;
                case 9:
                    col.setHeaderValue("Costo u.");
                    col.setCellRenderer(rightRenderer);
                    break;
            }
            tColumnModel.addColumn(col);
        }
        tablaOrden.setColumnModel(tColumnModel);
    }
    
    public void consultaInicial() {
        conectar();
         try {
             ordenesTM.ordenes.clear();
             Statement stat = conect.createStatement(HIDE_ON_CLOSE, NORMAL);
          // Se realiza la consulta
             ResultSet rs = stat.executeQuery ("SELECT codigo,noorden,nombre,fechacrea,saldoactual,"
                     + "materiaprima,manodeobra,costoindirectofab,"
                     + "abierta,idorden,cantidad, costounitario FROM ordenfabricacion"); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                OrdenFabricacion orden = new OrdenFabricacion();
                orden.setCodigo(rs.getInt("codigo"));
                orden.setNoorden(rs.getInt("noorden"));
                orden.setNombre(rs.getString("nombre"));
                orden.setFecha(rs.getString("fechacrea"));
                orden.setSaldoactual(rs.getDouble("saldoactual"));
                orden.setMateriaprima(rs.getDouble("materiaprima"));
                orden.setManodeobra(rs.getDouble("manodeobra"));
                orden.setCif(rs.getDouble("costoindirectofab"));
                orden.setAbierta(rs.getByte("abierta"));
                orden.setIdorden(rs.getInt("idorden"));
                orden.setCantidad(rs.getInt("cantidad"));
                orden.setPrecio(rs.getDouble("costounitario"));
                this.ordenesTM.ordenes.add(orden);
            }
            tablaOrden.updateUI();//Actualiza la tabla
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numOrdentxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nombretxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnFabRegresar = new javax.swing.JButton();
        btnRegistrarOrden = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOrden = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtfecha = new datechooser.beans.DateChooserCombo();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPorcentajeMOD = new javax.swing.JTextField();
        txtPorcentajeMP = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(152, 190, 211));

        jPanel1.setBackground(new java.awt.Color(152, 190, 211));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Numero de orden");

        numOrdentxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOrdentxtActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("Nombre");

        nombretxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setText("Fecha");

        jLabel9.setBackground(new java.awt.Color(152, 190, 211));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setText("ORDENES CREADAS ");

        btnFabRegresar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnFabRegresar.setText("Regresar");
        btnFabRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFabRegresarActionPerformed(evt);
            }
        });

        btnRegistrarOrden.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnRegistrarOrden.setText("Registrar Orden");
        btnRegistrarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarOrdenActionPerformed(evt);
            }
        });

        tablaOrden.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tablaOrden.setModel(ordenesTM);
        tablaOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaOrdenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaOrden);

        jLabel10.setBackground(new java.awt.Color(152, 190, 211));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("CREAR ORDEN DE FABRICACIÓN");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Costos indirectos de fabricación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setText("Porcentaje MOD:");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setText("Porcentaje MP:");

        txtPorcentajeMOD.setText("100");
        txtPorcentajeMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeMODActionPerformed(evt);
            }
        });

        txtPorcentajeMP.setText("0");
        txtPorcentajeMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeMPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtPorcentajeMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPorcentajeMP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtPorcentajeMOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPorcentajeMP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setText("Cantidad:");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnRegistrarOrden)
                                .addGap(52, 52, 52)
                                .addComponent(btnFabRegresar))
                            .addComponent(jLabel9))
                        .addGap(268, 268, 268))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(numOrdentxt, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(nombretxt, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(numOrdentxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombretxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarOrden)
                    .addComponent(btnFabRegresar))
                .addGap(13, 13, 13)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFabRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFabRegresarActionPerformed
       MenuPrincipalConta obj = new MenuPrincipalConta();
       obj.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnFabRegresarActionPerformed

    private void btnRegistrarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarOrdenActionPerformed
        //Codigo para registrar la orden de fabricación
         conectar();
         numOrden=numOrdentxt.getText();
         saldoA="0";//saldoAtxt.getText();
         try 
         {
            DateFormat dffrom = new SimpleDateFormat("MM-dd-yy");
            DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");  
            Date today;
            today = dffrom.parse(txtfecha.getText());
            fecha = dfto.format(today);
        }
        catch (ParseException ex) {
            Logger.getLogger(CrearOrdenFab.class.getName()).log(Level.SEVERE, null, ex);
        }
         orden="0";//ordentxt.getText();
         mp="0";//MPtxt.getText();
         mo="0";//MOtxt.getText();
         cif="0";//CIFtxt.getText();
         int porcentajeMOD, porcentajeMP, cantidad;
         porcentajeMOD = Integer.parseInt(txtPorcentajeMOD.getText());
         porcentajeMP = Integer.parseInt(txtPorcentajeMP.getText());
         cantidad = Integer.parseInt(txtCantidad.getText());
         
         if (numOrden.length() == 1)//Para ordenes 1, 2, 3, 4 (de un dígito). etc
             numOrden = "0" + numOrden;
         
         cod="1109"+numOrden;
         nombre=nombretxt.getText();
         if(!numOrden.equals("")&&!nombre.equals("")&&!fecha.equals("")){
             int estadoF=3;
            try {
                CallableStatement cstm = conect.prepareCall("{call sic.sp_nuevaCuenta(?,?,?,?,?)}");
                cstm.setInt(1,Integer.parseInt(cod));//codigo de la cuenta
                cstm.setInt(2,1109);//tipo de cuenta(1109 para orden de fabricación)
                cstm.setString(3,nombre);//nombre de cuenta
                cstm.setInt(4,1);//Estado financiero 1=activo,2=pasivo,3=Capital
                cstm.setInt(5,0);//tipo de operacion (1=cuenta normal,2=contra activo,3=Egreso,4=Desinversion)
                //solo puede ser 1 o 0 
                cstm.execute();
            } catch (Exception e) {
                e.printStackTrace();

            }
          //Registrar orden
            try{
                // Preparamos la consulta
                Statement stat = conect.createStatement();
                stat.execute("insert into ordenfabricacion (idorden,noorden,codigo,nombre,fechacrea,saldoactual,abierta,"
                  + "materiaprima,manodeobra,costoindirectofab, porcentajemod, porcentajemp, cantidad, costounitario) VALUES(NULL,'"+numOrden+"','"+cod+"','"+nombre+"','"+fecha+"','"+saldoA+"',1,"
                  + "'"+mp+"','"+mo+"','"+cif+"','"+porcentajeMOD+"','"+porcentajeMP+"','"+cantidad+"','0')");
                stat.close();
                conect.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }
            
            consultaInicial();
         }
         else{
             JOptionPane.showMessageDialog(this,"Ingrese los datos de nombre,número y fecha");
         }
        
            //this.nombreCuenta.setText("");
            //this.jComboBox2.setSelectedIndex(0);
            //this.estFinanciero.setSelectedIndex(0);
            //this.operacion.setSelectedIndex(0);       
    }//GEN-LAST:event_btnRegistrarOrdenActionPerformed

    private void tablaOrdenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaOrdenMouseClicked
        int clics = evt.getClickCount();
        int col = tablaOrden.columnAtPoint(evt.getPoint());
        int row = tablaOrden.rowAtPoint(evt.getPoint());
        
        
        if (clics == 2)
        {
            OrdenFabricacion of = ordenesTM.ordenes.get(row);
            if (col == 5)
            {
                OrdenFab_ManoObra mo = new OrdenFab_ManoObra(of.getIdorden());
                //System.out.println(of.getIdorden());
                mo.setVisible(true);
                this.setVisible(false);
            }
            else if (col == 4)
            {
                OrdenFab_MateriaPrima mp = new OrdenFab_MateriaPrima(of.getIdorden(),String.valueOf(of.getCodigo()));// codigo);
                mp.setVisible(true);
               
                this.setVisible(false);
                System.out.println(of.getIdorden());
            }
            else if (col==7)
            {
                if (of.getAbierta() == 0)
                {//Orden cerrada, avisar al usuario que no se puede hacer nada
                    JOptionPane.showMessageDialog(this, "La orden " + of.getNoorden() + " está actualmente cerrada.");
                }
                else
                {
                    /*int f,c;
                    f=tablaOrden.getSelectionModel().getLeadSelectionIndex();;
                    Object CodigoOrden=tablaOrden.getModel().getValueAt(f,0);
                    String codigo=CodigoOrden.toString();*/
                    CerrarOrden co = new CerrarOrden(of.getIdorden(),String.valueOf(of.getCodigo()),of.getSaldoactual(),of.getNombre(),of.getNoorden());
                    co.setVisible(true);
                    this.setVisible(false);
                }
                //System.out.println(of.getIdorden());
            }
        }
    }//GEN-LAST:event_tablaOrdenMouseClicked

    private void numOrdentxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numOrdentxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numOrdentxtActionPerformed

    private void txtPorcentajeMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeMODActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeMODActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPorcentajeMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeMPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeMPActionPerformed

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
            java.util.logging.Logger.getLogger(CrearOrdenFab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearOrdenFab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearOrdenFab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearOrdenFab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearOrdenFab().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFabRegresar;
    private javax.swing.JButton btnRegistrarOrden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombretxt;
    private javax.swing.JTextField numOrdentxt;
    private javax.swing.JTable tablaOrden;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtPorcentajeMOD;
    private javax.swing.JTextField txtPorcentajeMP;
    private datechooser.beans.DateChooserCombo txtfecha;
    // End of variables declaration//GEN-END:variables
}
