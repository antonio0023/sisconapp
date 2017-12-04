/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Conexion.Conexion.con;
import static Conexion.Conexion.conectar;
import Otros.OrdenFabricacion;
import OrdenDeFabricacion.OrdenFabTableModel;
import Otros.OrdenTableModel;
import java.awt.BorderLayout;
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
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        this.setTitle("Crear Orden de Fabricacion");
       
    }
    
    public void Inicializar() {
        numOrdentxt.setText("");
        //saldoAtxt.setText("0.00");
        DateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
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
             Statement stat = con.createStatement(HIDE_ON_CLOSE, NORMAL);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnFabRegresar = new javax.swing.JButton();
        btnRegistrarOrden = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOrden = new javax.swing.JTable();
        txtfecha = new datechooser.beans.DateChooserCombo();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPorcentajeMOD = new javax.swing.JTextField();
        txtPorcentajeMP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nombretxt = new javax.swing.JTextField();
        numOrdentxt = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(152, 190, 211));

        jPanel1.setBackground(new java.awt.Color(117, 60, 17));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(51, 51, 55));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Numero de orden");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 78, -1, -1));

        jLabel3.setBackground(new java.awt.Color(51, 51, 55));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 136, -1, 20));

        jLabel2.setBackground(new java.awt.Color(51, 51, 55));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 76, -1, -1));

        jLabel9.setBackground(new java.awt.Color(51, 51, 55));
        jLabel9.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ORDENES CREADAS ");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 313, -1, -1));

        btnFabRegresar.setBackground(new java.awt.Color(117, 60, 17));
        btnFabRegresar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnFabRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnFabRegresar.setText("Regresar");
        btnFabRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFabRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnFabRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        btnRegistrarOrden.setBackground(new java.awt.Color(117, 60, 17));
        btnRegistrarOrden.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegistrarOrden.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarOrden.setText("Registrar Orden");
        btnRegistrarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarOrdenActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrarOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, -1, -1));

        tablaOrden.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaOrden.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tablaOrden.setModel(ordenesTM);
        tablaOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaOrdenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaOrden);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 355, 780, 184));

        txtfecha.setLocale(new java.util.Locale("es", "SV", ""));
        jPanel1.add(txtfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(523, 77, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)), "COSTOS INDIRECTOS DE FABRICACIÓN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gotham Thin", 0, 11))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Gotham Thin", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("% Mano Obra Directa");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 17));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Gotham Thin", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("% Materia Prima");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 17));

        txtPorcentajeMOD.setText("100");
        txtPorcentajeMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeMODActionPerformed(evt);
            }
        });
        jPanel2.add(txtPorcentajeMOD, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 109, -1));

        txtPorcentajeMP.setText("0");
        txtPorcentajeMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeMPActionPerformed(evt);
            }
        });
        jPanel2.add(txtPorcentajeMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 109, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cafee.jpg"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 191, -1, -1));

        jLabel6.setBackground(new java.awt.Color(51, 51, 55));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cantidad:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 76, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ordenFabWhite.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, -1));
        jPanel1.add(nombretxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 138, 311, -1));

        numOrdentxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOrdentxtActionPerformed(evt);
            }
        });
        jPanel1.add(numOrdentxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 77, 56, -1));

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 77, 44, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cafee.jpg"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPorcentajeMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeMPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeMPActionPerformed

    private void txtPorcentajeMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeMODActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeMODActionPerformed

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

    private void btnRegistrarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarOrdenActionPerformed
        //Codigo para registrar la orden de fabricación
        conectar();
        numOrden=numOrdentxt.getText();
        saldoA="0";//saldoAtxt.getText();
        try
        {
            DateFormat dffrom = new SimpleDateFormat("dd-MM-yy");
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
                CallableStatement cstm = con.prepareCall("{call sic.sp_nuevaCuenta(?,?,?,?,?)}");
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
                Statement stat = con.createStatement();
                stat.execute("insert into ordenfabricacion (idorden,noorden,codigo,nombre,fechacrea,saldoactual,abierta,"
                    + "materiaprima,manodeobra,costoindirectofab, porcentajemod, porcentajemp, cantidad, costounitario) VALUES(NULL,'"+numOrden+"','"+cod+"','"+nombre+"','"+fecha+"','"+saldoA+"',1,"
                    + "'"+mp+"','"+mo+"','"+cif+"','"+porcentajeMOD+"','"+porcentajeMP+"','"+cantidad+"','0')");
                stat.close();
                con.close();
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

    private void btnFabRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFabRegresarActionPerformed
        MenuCostos obj = new MenuCostos();
        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnFabRegresarActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if(Character.isLetter(c)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se admiten números","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void numOrdentxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numOrdentxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numOrdentxtActionPerformed

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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
