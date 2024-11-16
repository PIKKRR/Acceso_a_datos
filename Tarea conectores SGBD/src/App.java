import java.sql.*;

public class App {
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:3306/conectores"; // URL de la base de datos
        String user = "root"; // Usuario de MySQL
        String password = ""; // Contraseña (por defecto es vacía en XAMPP)

        // Conectar a la base de datos y mostrar los registros de la tabla 'usuarios'
        try {
            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            Connection conn = DriverManager.getConnection(url, user, password);

            // Crear una declaración SQL
            String query = "SELECT * FROM usuarios"; // Consulta SQL

            // Ejecutar la consulta
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Mostrar los registros obtenidos.
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int edad = rs.getInt("edad");
                String correo = rs.getString("correo");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Edad: " + edad + ", Correo: " + correo);
            }

            // Cerrar la conexión
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    }
}