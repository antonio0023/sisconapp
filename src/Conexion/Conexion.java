package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Alexis Lopez
 */
public class Conexion {

    public static Connection con;

    public static Connection conectar() {
    try {

           Class.forName("org.gjt.mm.mysql.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost/sic","root","");
            }  catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error no se puede conectar con la base de datos");
        }
    return con;
 
 }

    public Statement createStatement(int TYPE_SCROLL_INSENSITIVE, int CONCUR_READ_ONLY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

