import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioHSQL implements Persistencia<Usuario> {

    private static final String DB_URL = "jdbc:hsqldb:mem:usuarioDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection = null;

    public UsuarioHSQL() {
        criarTabela();
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return connection;
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                "username VARCHAR(255) PRIMARY KEY, " +
                "senha VARCHAR(255))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Tabela 'Usuario' criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela de usuários:");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Usuario usuario) {
        String sql = "INSERT INTO Usuario (username, senha) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();
            System.out.println("Usuário " + usuario.getUsername() + " salvo com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o usuário:");
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("username"), rs.getString("senha"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários:");
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET senha = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getSenha());
            stmt.setString(2, usuario.getUsername());
            stmt.executeUpdate();
            System.out.println("Usuário " + usuario.getUsername() + " atualizado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o usuário:");
            e.printStackTrace();
        }
    }

    @Override
    public boolean remover(int id) {
        return false; // Método não utilizado
    }

    @Override
    public boolean remover(String username) {
        String sql = "DELETE FROM Usuario WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover o usuário:");
            e.printStackTrace();
            return false;
        }
    }
}
