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
        jPanel1 = new javax.swing.JPanel();
        comboMP = new javax.swing.JComboBox();
        montoMptxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtNorden = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMateria = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(33, 33, 78));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(0, 0));

        comboMP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        montoMptxt.setBackground(new java.awt.Color(33, 33, 78));
        montoMptxt.setFont(new java.awt.Font("Gotham Thin", 1, 12)); // NOI18N
        montoMptxt.setForeground(new java.awt.Color(73, 181, 172));
        montoMptxt.setBorder(null);
        montoMptxt.setCaretColor(new java.awt.Color(73, 181, 172));

        jLabel1.setBackground(new java.awt.Color(51, 51, 55));
        jLabel1.setFont(new java.awt.Font("Gotham Thin", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(222, 222, 222));
        jLabel1.setText("Monto");

        jLabel2.setBackground(new java.awt.Color(51, 51, 55));
        jLabel2.setFont(new java.awt.Font("Gotham Thin", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(222, 222, 222));
        jLabel2.setText("Materia Prima");

        jButton1.setBackground(new java.awt.Color(33, 33, 62));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar MP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(33, 33, 62));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtNorden.setBackground(new java.awt.Color(33, 33, 78));
        txtNorden.setFont(new java.awt.Font("Gotham Thin", 1, 12)); // NOI18N
        txtNorden.setForeground(new java.awt.Color(255, 255, 255));
        txtNorden.setBorder(null);

        jLabel3.setBackground(new java.awt.Color(51, 51, 55));
        jLabel3.setFont(new java.awt.Font("Gotham Thin", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(222, 222, 222));
        jLabel3.setText("Codigo Orden");

        tablaMateria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        tablaMateria.setModel(materiasMP);
        jScrollPane1.setViewportView(tablaMateria);

        jLabel12.setBackground(new java.awt.Color(33, 33, 78));
        jLabel12.setFont(new java.awt.Font("Gotham Thin", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("MATERIA PRIMA A SELECCIONAR");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel12.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(33, 33, 78));
        jLabel11.setFont(new java.awt.Font("Gotham Thin", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_MP.png"))); // NOI18N
        jLabel11.setText("MATERIA PRIMA UTILIZADA");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(73, 181, 172)));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setOpaque(true);
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator5.setBackground(new java.awt.Color(73, 181, 172));
        jSeparator5.setForeground(new java.awt.Color(73, 181, 172));

        jSeparator6.setBackground(new java.awt.Color(73, 181, 172));
        jSeparator6.setForeground(new java.awt.Color(73, 181, 172));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboMP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(montoMptxt, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNorden, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                .addComponent(jSeparator6)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(161, 161, 161)
                            .addComponent(jButton1)
                            .addGap(78, 78, 78)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(112, 112, 112)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(56, 56, 56)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNorden)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(montoMptxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(3, 3, 3))
                                .addComponent(comboMP, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(19, 19, 19)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField montoMptxt;
    private javax.swing.JTable tablaMateria;
    private javax.swing.JTextField txtNorden;
    // End of variables declaration//GEN-END:variables
}
