package agenda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBContactos {
    private final String url = "jdbc:mysql://localhost/agenda";
    private final String user = "root";
    private final String password = "2604";
    private Connection conexion;
    
    public void connect() {
        try {
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion Exitosa.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            if (conexion != null) {
                conexion.close();
            }    
            System.out.println("Desconectado con Exito.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void agregarContacto(Contacto contacto) {
        connect();
        String sql = "INSERT INTO contactos (Nombre, Apellidos, Empresa, Telefono, CorreoElectronico) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getApellido());
            pstmt.setString(3, contacto.getEmpresa());
            pstmt.setString(4, contacto.getTelefono());
            pstmt.setString(5, contacto.getCorreoElectronico());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }
    
    public void editarContacto(Contacto contacto) {
        connect();
        String sql = "UPDATE contactos SET Apellidos = ?, Empresa = ?, Telefono = ?, "
                + "CorreoElectronico = ? WHERE Nombre = ?";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, contacto.getApellido());
            pstmt.setString(2, contacto.getEmpresa());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setString(4, contacto.getCorreoElectronico());
            pstmt.setString(5, contacto.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        disconnect();
    }

    public Contacto buscarContacto(String nombre) {
        connect();
        String sql = "SELECT * FROM contactos WHERE Nombre = ?";
        Contacto contacto = null;

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String apellido = rs.getString("Apellidos");
                String empresa = rs.getString("Empresa");
                String telefono = rs.getString("Telefono");
                String correoElectronico = rs.getString("CorreoElectronico");
                contacto = new Contacto(nombre, apellido, empresa, telefono, correoElectronico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        disconnect();
        return contacto;
    }

    public List<Contacto> obtenerTodosLosContactos() {
    connect();
    String sql = "SELECT * FROM contactos";
    List<Contacto> contactos = new ArrayList<>();

    try {
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("Id");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellidos");
            String empresa = rs.getString("Empresa");
            String telefono = rs.getString("Telefono");
            String correoElectronico = rs.getString("CorreoElectronico");
            contactos.add(new Contacto(id, nombre, apellido, empresa, telefono, correoElectronico)); // Usa el constructor con ID
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    disconnect();
    return contactos;
}


    public void borrarContacto(String nombre) {
        connect();
        String sql = "DELETE FROM contactos WHERE Nombre = ?";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        disconnect();
    }
}