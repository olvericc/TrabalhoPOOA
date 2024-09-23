import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConteudoHSQL implements Persistencia<Conteudo> {

    private static final String DB_URL = "jdbc:hsqldb:mem:conteudoDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection = null;

    public ConteudoHSQL() {
        criarTabela();
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return connection;
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS Conteudo (" +
                "id INTEGER IDENTITY PRIMARY KEY, " +
                "titulo VARCHAR(255), " +
                "texto VARCHAR(10000), " +
                "autor VARCHAR(255))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Conteudo conteudo) {
        String sql = "INSERT INTO Conteudo (titulo, texto, autor) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setString(2, conteudo.getTexto());
            stmt.setString(3, conteudo.getAutor().getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Conteudo> listar() {
        List<Conteudo> conteudos = new ArrayList<>();
        String sql = "SELECT * FROM Conteudo";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario autor = new Usuario(rs.getString("autor"), "");
                Conteudo conteudo = new Conteudo(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("texto"),
                        autor,
                        ""
                );
                conteudos.add(conteudo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conteudos;
    }

    @Override
    public void atualizar(Conteudo conteudo) {
        String sql = "UPDATE Conteudo SET titulo = ?, texto = ?, autor = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setString(2, conteudo.getTexto());
            stmt.setString(3, conteudo.getAutor().getUsername());
            stmt.setInt(4, conteudo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remover(String titulo) {
        String sql = "DELETE FROM Conteudo WHERE titulo = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
