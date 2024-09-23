import java.util.List;

public class UsuarioService {

    private Persistencia<Usuario> usuarioPersistencia;

    public UsuarioService() {
        this.usuarioPersistencia = new UsuarioHSQL();
        criarUsuarioPadrao(); // Criar o primeiro usuário automaticamente, se necessário
    }

    // Método para validar login
    public Usuario validarLogin(String username, String password) {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        System.out.println("Lista de usuários no banco:");
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getUsername() + " | Senha: " + usuario.getSenha());
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getSenha().equals(password)) {
                return usuario;
            }
        }
        return null; // Se o login for inválido
    }

    // Criar um usuário padrão (ex.: sofia) se a tabela estiver vazia
    private void criarUsuarioPadrao() {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        if (usuarios.isEmpty()) {
            Usuario padrao = new Usuario("sofia", "sofia");
            usuarioPersistencia.save(padrao);
            System.out.println("Usuário padrão 'sofia' criado com sucesso.");
        } else {
            System.out.println("Usuários já presentes no banco:");
            for (Usuario u : usuarios) {
                System.out.println("Usuário: " + u.getUsername() + " | Senha: " + u.getSenha());
            }
        }
    }

    // Método para criar um novo usuário
    public void criarUsuario(Usuario novoUsuario) {
        if (usuarioExistente(novoUsuario.getUsername())) {
            System.out.println("Erro: Usuário já existe!");
        } else {
            usuarioPersistencia.save(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
        }
    }

    // Verificar se o usuário já existe
    private boolean usuarioExistente(String username) {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioPersistencia.listar();
    }

    // Método para alterar um usuário existente
    public void alterarUsuario(String username, String novaSenha) {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                usuario.setSenha(novaSenha);
                usuarioPersistencia.atualizar(usuario);
                System.out.println("Senha do usuário " + username + " atualizada com sucesso!");
                return;
            }
        }
        System.out.println("Erro: Usuário não encontrado.");
    }

    // Método para excluir um usuário pelo username
    public void excluirUsuario(String username) {
        boolean removido = usuarioPersistencia.remover(username);
        if (removido) {
            System.out.println("Usuário " + username + " excluído com sucesso!");
        } else {
            System.out.println("Erro: Usuário não encontrado.");
        }
    }

    // Método para alterar a senha do próprio usuário logado
    public void alterarSenha(Usuario usuario, String novaSenha) {
        usuario.setSenha(novaSenha);
        usuarioPersistencia.atualizar(usuario);
        System.out.println("Sua senha foi alterada com sucesso!");
    }
}
