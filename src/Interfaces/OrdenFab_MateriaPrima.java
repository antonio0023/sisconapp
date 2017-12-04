/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Conexion.Conexion.con;
import static Conexion.Conexion.conectar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import Otros.OrdenFabricacion;
import Otros.MateriaPrima;
import Otros.OrdenTableModel;
import Otros.MateriaPTableModel;
import java.awt.BorderLayout;

import static java.awt.Frame.NORMAL;
import java.sql.CallableStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author Administrador
 */
public class OrdenFab_MateriaPrima extends javax.swing.JFrame {
    String idordenmp="",idmp="",idorden="",monto="",codigomp="";
    double Total=0;
    public String nordem;
    int b=0,pos=0;
    public MateriaPTableModel materiasMP = new MateriaPTableModel();
    
    private int idOrdenFabricacion;
    private String codigoOrden;
    
    public OrdenFab_MateriaPrima(int idOrden,String codigo) {
        idOrdenFabricacion = idOrden;
        codigoOrden=codigo;
        initComponents();
        inicializarColumnas();
        consultaInicial();
        llenarCB();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        this.setTitle("Materia Prima");
        if (getOrdenFabricacionPorId().getAbierta() == 0)
        {
            comboMP.setEnabled(false);
            montoMptxt.setEnabled(false);
            txtNorden.setEnabled(false);
            jButton1.setEnabled(false);
        }
        //txtNorden.setEditable(FALSE);
    }
    
    private void inicializarColumnas() {
        TableColumnModel tColumnModel = new DefaultTableColumnModel();
        for (int i = 0; i <2; i++) {
            TableColumn col = new TableColumn(i);
            switch (i) {
                case 0:
                    col.setHeaderValue("Nombre");
                    break;
                case 1:
                    col.setHeaderValue("Monto");
                    break;
            }
            tColumnModel.addColumn(col);
        }
        tablaMateria.setColumnModel(tColumnModel);
    }
    
    
    private void consultaInicial() {
        conectar();
         try {
             materiasMP.materiasP.clear();
             Statement stat = con.createStatement(HIDE_ON_CLOSE, NORMAL);
          // Se realiza la consulta
             ResultSet rs = stat.executeQuery ("SELECT monto,nombremateriaprima from orden_materiaprima as e join materiasprimas as l "
                     + "on e.idmateriaprima=l.idmateriaprima where e.idorden='"+idOrdenFabricacion+"'"); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                MateriaPrima ordenMP = new MateriaPrima();
                ordenMP.setMonto(rs.getDouble("monto"));
                ordenMP.setNombreMP(rs.getString("nombremateriaprima"));
                this.materiasMP.materiasP.add(ordenMP);                
            }
            tablaMateria.updateUI();//Actualiza la tabla
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //Llenar combo de materias
    public void llenarCB(){
        comboMP.removeAllItems();
        //txtNorden.setText(Integer.toString(idOrdenFabricacion));
        txtNorden.setText(codigoOrden);
        conectar();
        ArrayList<String> mp = new ArrayList<String>();
        
        try {
             Statement stat = con.createStatement(HIDE_ON_CLOSE, NORMAL);
          // Se realiza la consulta
             ResultSet rs = stat.executeQuery ("SELECT idmateriaprima,codigo,nombremateriaprima from materiasprimas ORDER BY (nombremateriaprima)"); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                mp.add(rs.getString("nombremateriaprima"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0; i<mp.size();i++){
        comboMP.addItem(mp.get(i));
        }
        montoMptxt.setText("");
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        comboMP = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMateria = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNorden = new javax.swing.JTextField();
        montoMptxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jButton3.setText("jButton3");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cafee.jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(117, 60, 17));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboMP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(comboMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 104, -1, 22));

        jButton1.setBackground(new java.awt.Color(117, 60, 17));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar MP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 86, -1));

        tablaMateria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        tablaMateria.setModel(materiasMP);
        jScrollPane1.setViewportView(tablaMateria);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 470, 178));

        jLabel11.setBackground(new java.awt.Color(117, 60, 17));
        jLabel11.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("MATERIA PRIMA UTILIZADA");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setOpaque(true);
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 506, -1));

        jLabel10.setBackground(new java.awt.Color(117, 60, 17));
        jLabel10.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Materia prima");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setOpaque(true);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 22, 482, -1));

        jLabel4.setBackground(new java.awt.Color(117, 60, 17));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Codigo Orden");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 80, 24));

        jLabel5.setBackground(new java.awt.Color(117, 60, 17));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Materia Prima");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 100, 80, 24));

        txtNorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNordenActionPerformed(evt);
            }
        });
        txtNorden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNordenKeyTyped(evt);
            }
        });
        jPanel1.add(txtNorden, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 44, -1));

        montoMptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoMptxtActionPerformed(evt);
            }
        });
        montoMptxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                montoMptxtKeyTyped(evt);
            }
        });
        jPanel1.add(montoMptxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 44, -1));

        jLabel6.setBackground(new java.awt.Color(117, 60, 17));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Monto");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 80, 24));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cafee.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 680, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       CrearOrdenFab obj = new CrearOrdenFab();
       //obj.consultaInicial();
       obj.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //DefaultTableModel modelo = (DefaultTableModel) tablaMP.getModel();
        monto=montoMptxt.getText();
        String nomMP=comboMP.getSelectedItem().toString();
        String id ="",codigo="",seleccion="",seleccionA="",seleccion2="",seleccion3="",nombre="",seleccion5="";
        Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMP() / 100.0;
        
            conectar();
            if(montoMptxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Monto no especificado, Ingrese un valor positivo");
            }
            else {
                
                try {
             Statement stat = con.createStatement(HIDE_ON_CLOSE, NORMAL);
          // Se realiza la consulta para obtener idmateriaprima
             ResultSet rs = stat.executeQuery ("SELECT idmateriaprima,codigo from materiasprimas where nombremateriaprima='"+nomMP+"'"); //con es la conexión que hemos creado antes con el patrón singleton                                                     //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                idmp=Integer.toString(rs.getInt("idmateriaprima"));
                codigomp=rs.getString("codigo");
            }
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();  
        }
                conectar();
                try{
                // se crea la orden de materia prima
                Statement stat = con.createStatement();
                stat.execute("insert into orden_materiaprima (idordenmp,idmateriaprima,idorden,monto) "
                        + "VALUES(NULL,'"+idmp+"','"+idOrdenFabricacion+"','"+monto+"')");
                stat.close();
                con.close();
                }catch (SQLException e)
                   {
                        System.out.println (e);
                } 
            //actualizar monto de orden
            conectar();
             Total=Double.parseDouble(monto);   
            try{
                // Preparamos la consulta
                Statement stat = con.createStatement();
                
                //Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMP() / 100.0;
                System.out.println("Porcentaje: " + porcentaje.toString());
                System.out.println("Total CIF: " + Total * porcentaje);
                stat.execute("UPDATE ordenfabricacion SET saldoactual=saldoactual +'"+(Total + Total * porcentaje) +"',materiaprima=materiaprima +'"+Total+"',"
                        + "costoindirectofab=costoindirectofab +'"+ Total * porcentaje +"' WHERE idorden='"+idOrdenFabricacion+"'");
                stat.close();
                con.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }
            //Actualizar inventario abonandola
            conectar(); 
            try{
                // Preparamos la consulta
                Statement stat = con.createStatement();
                stat.execute("UPDATE cuenta SET HABER=HABER +'"+Total+"' WHERE CODIGO='"+codigomp+"'");
                stat.close();
                con.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }
            //Actualiza haber de Variación de CIF
            conectar(); 
             try{
                // Preparamos la consulta
                Statement stat = con.createStatement();
                stat.execute("UPDATE cuenta SET HABER=HABER +'"+(Total * porcentaje)+"' WHERE CODIGO=110801");
                stat.close();
                con.close();
            }catch (SQLException e)
                {
               System.out.println (e);
                }
            consultaInicial();
            montoMptxt.setText("");
            try {
                //Double porcentaje = getOrdenFabricacionPorId().getPorcentajeMP() / 100.0;
                String codigoOF = "1109";
                if (Integer.toString(getOrdenFabricacionPorId().getNoorden()).length() == 1)
                    codigoOF += "0";
                codigoOF += Integer.toString(getOrdenFabricacionPorId().getNoorden());

                CallableStatement cstm = con.prepareCall("{call sic.sp_insertar_cuentas(?,?,?,?,?,?)}");
                cstm.setInt(1,Integer.parseInt(codigoOF));//codigo de la cuenta: Orden de fabricación
                cstm.setString(2, "");//vCuenta
                cstm.setString(3, "");//vTipo
                //cstm.setDouble(4, Total);//vSDeudor
                cstm.setDouble(4,Total + Total * porcentaje);
                cstm.setDouble(5, 0);//vSAcreedor
                cstm.setInt(6,1);//vCantidad
                cstm.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNordenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNordenActionPerformed

    private void txtNordenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNordenKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNordenKeyTyped

    private void montoMptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoMptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_montoMptxtActionPerformed

    private void montoMptxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montoMptxtKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if(Character.isLetter(c)){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se admiten números","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_montoMptxtKeyTyped

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
            java.util.logging.Logger.getLogger(OrdenFab_MateriaPrima.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_MateriaPrima.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_MateriaPrima.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenFab_MateriaPrima.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboMP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField montoMptxt;
    private javax.swing.JTable tablaMateria;
    private javax.swing.JTextField txtNorden;
    // End of variables declaration//GEN-END:variables
}
