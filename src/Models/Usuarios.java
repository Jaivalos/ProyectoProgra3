package Models;

public class Usuarios {
    private int id;
    private String user;
    private String nombre;
    private String clave;
    private String caja;
    private String rol;
    private String estado;
    
    public Usuarios(){
        
    }

    public Usuarios(int id, String user, String nombre, String clave, String caja, String rol, String estado) {
        this.id = id;
        this.user = user;
        this.nombre = nombre;
        this.clave = clave;
        this.caja = caja;
        this.rol = rol;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
