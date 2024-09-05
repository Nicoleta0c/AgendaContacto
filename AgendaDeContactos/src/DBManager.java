
import java.sql.*;

public class DBManager {
    
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost/agenda";
        String user = "root";
        String password = "2604";
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
          conexion = DriverManager.getConnection(url, user, password);
          String query = "Insert Into contactos(Nombre, Apellidos, Empresa, Telefono, CorreoElectronico) "
                    + "Values ('Yamile', 'Guzman', 'Loteka', '8097165492', 'yamile87@gmail.com')";
        
           statement = conexion.createStatement();
           statement.executeUpdate(query);
           
           query = "Select ID, NOMBRE, APELLIDOS, EMPRESA, TELEFONO, CORREOELECTRONICO From agenda";
           rs = statement.executeQuery(query);
           
           while(rs.next()) {
               System.out.println("ID: " + rs.getInt("Id")
                       + "NOMBRE: " + rs.getString("Nombre") 
                       + "APELLIDOS: " + rs.getString("Apellidos")
                       + "EMPRESA: " + rs.getString("Empresa")
                       + "TELEFONO: " + rs.getString("Telefono")
                       + "CORREOELECTRONICO: " + rs.getString("CorreoElectronico")
                       
               );
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
}
    
    