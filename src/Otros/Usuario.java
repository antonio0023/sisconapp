package Otros;

/**
 *
 * @author Alexis Lopez
 */
public class Usuario {
    private int id_usuario;
    private String usuario;
    private String contrasena;
    private int tipoUsuario;

    
    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    

    public int getId_usuario() {
        return id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    

}
