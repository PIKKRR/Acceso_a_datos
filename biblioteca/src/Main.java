import java.sql.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Inserción de datos
            insertarAutor(conn, "Gabriel García Márquez", "Colombiana");
            insertarEditorial(conn, "Editorial Sudamericana", "Buenos Aires");
            int libroId = insertarLibro(conn, "Cien años de soledad", "978-3-16-148410-0", 1988, 1, 1);

            insertarUsuario(conn, "Juan Pérez", "juan.perez@example.com");

            // Uso de LocalDate para manejar fechas
            LocalDate fechaInicio = LocalDate.of(2025, 1, 1); // Año, Mes, Día
            LocalDate fechaFin = LocalDate.of(2025, 1, 15);

            // Convertir LocalDate a java.sql.Date
            insertarPrestamo(conn, 1, libroId, java.sql.Date.valueOf(fechaInicio), java.sql.Date.valueOf(fechaFin));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarAutor(Connection conn, String nombre, String nacionalidad) throws SQLException {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, nacionalidad);
            stmt.executeUpdate();
        }
    }

    private static void insertarEditorial(Connection conn, String nombre, String direccion) throws SQLException {
        String sql = "INSERT INTO editoriales (nombre, direccion) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.executeUpdate();
        }
    }

    private static int insertarLibro(Connection conn, String titulo, String ISBN, int anio, int autorId, int editorialId) throws SQLException {
        String sql = "INSERT INTO libros (titulo, ISBN, anio_publicacion, autor_id, editorial_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, titulo);
            stmt.setString(2, ISBN);
            stmt.setInt(3, anio);
            stmt.setInt(4, autorId);
            stmt.setInt(5, editorialId);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        return -1;
    }

    private static void insertarUsuario(Connection conn, String nombre, String email) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    private static void insertarPrestamo(Connection conn, int usuarioId, int libroId, java.sql.Date fechaInicio, java.sql.Date fechaFin) throws SQLException {
        String sql = "INSERT INTO prestamos (usuario_id, libro_id, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, libroId);
            stmt.setDate(3, fechaInicio);
            stmt.setDate(4, fechaFin);
            stmt.executeUpdate();
        }
    }
}