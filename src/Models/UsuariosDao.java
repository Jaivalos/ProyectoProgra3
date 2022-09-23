package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UsuariosDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public Usuarios login(String user, String clave){
        String sql = "select * from usuarios where usuario = ? and clave = ?";
        Usuarios us = new Usuarios();
        
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            
            if(rs.next()){
                us.setId(rs.getInt("id"));
                us.setUser(rs.getString("usuario"));
                us.setNombre(rs.getString("nombre"));
                us.setCaja(rs.getString("caja"));
                us.setRol(rs.getString("rol"));
                us.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return us;
    }
}
