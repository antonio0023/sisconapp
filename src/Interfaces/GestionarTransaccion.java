/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Conexion.Conexion.con;
import static Conexion.Conexion.conectar;
import java.awt.BorderLayout;
import static java.awt.Frame.NORMAL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alexis Lopez
 */
public class GestionarTransaccion extends javax.swing.JFrame {
    private final DefaultTableModel modelo;
    /**
     * Creates new form GestionarTransaccion
     */
    public GestionarTransaccion() {
        initComponents();
                
             //centrar frame
             setLocationRelativeTo(null);
             setVisible(true);
             
             modelo = new DefaultTableModel() {
             @Override
             public boolean isCellEditable(int fila, int columna) {
             return false; //Con esto conseguimos que la tabla no se pueda editar
           }
       };
            setResizable(false);
            setTitle("Gestionar Transaccion");
            setIconImage(new ImageIcon(getClass().getResource("/Imagenes/inicio.png")).getImage());
            ((JPanel)getContentPane()).setOpaque(false);
            ImageIcon uno=new ImageIcon(this.getClass().getResource("/Imagenes/cafe.jpg"));
            JLabel fondo= new JLabel();
            fondo.setIcon(uno);
            getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
            fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight()); 
             jTable1.setModel(modelo); //Metemos el modelo dentro de la tabla
             modelo.addColumn("ID_Transaccion"); //Añadimos las columnas a la tabla (tantas como queramos)
             modelo.addColumn("Descripcion");
             modelo.addColumn("Fecha");
             jTable1.setVisible(true);
             rellenarTabla(); //Llamamos al método que rellena la tabla con los datos de la base de datos

    
    }
    void rellenarTabla(){
           conectar();
         try {
             Statement stat = con.createStatement(HIDE_ON_CLOSE, NORMAL);
          // Se realiza la consulta
             ResultSet rs = stat.executeQuery ("SELECT id_transaccion, descripcion, fecha FROM transacciones"); //con es la conexión que hemos creado antes con el patrón singleton               
                                               //listaEquipos() es la consulta a la base de datos, que retorna un ResultSet
            while(rs.next()){
                Object[] fila = new Object[3];//Creamos un Objeto con tantos parámetros como datos retorne cada fila 
                                              // de la consulta
                fila[0] = rs.getInt("id_transaccion"); //Lo que hay entre comillas son los campos de la base de datos
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getDate("fecha");
                modelo.addRow(fila); // Añade una fila al final del modelo de la tabla
            }
 
                 jTable1.updateUI();//Actualiza la tabla
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }
    
    void vaciarTabla(){
        while (modelo.getRowCount() > 0) modelo.removeRow(0);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnInsertar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel1.setText("Transacciones Realizadas en el Periodo");

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Transaccion", "Descripcion", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnInsertar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Atras");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInsertar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertar)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MenuContador2 menu = new MenuContador2();
        menu.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        AgregarTransaccion agrTrans = new AgregarTransaccion();
        agrTrans.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInsertarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionarTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionarTransaccion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
