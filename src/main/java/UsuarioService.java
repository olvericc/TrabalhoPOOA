import java.util.List;

public class UsuarioService {

    private Persistencia<Usuario> usuarioPersistencia;

    public UsuarioService() {
        this.usuarioPersistencia = new UsuarioHSQL();
        criarUsuarioPadrao();
    }

    public Usuario validarLogin(String username, String password) {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        System.out.println("Lista de usuários no banco:");
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getUsername() + " | Senha: " + usuario.getSenha());
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getSenha().equals(password)) {
                System.out.println("Login bem-sucedido para o usuário: " + username);
                return usuario;
            }
        }
        System.out.println("Falha no login para o usuário: " + username);
        return null;
    }

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

    public void criarUsuario(Usuario novoUsuario) {
        if (usuarioExistente(novoUsuario.getUsername())) {
            System.out.println("Erro: Usuário já existe!");
        } else {
            usuarioPersistencia.save(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
        }
    }

    private boolean usuarioExistente(String username) {
        List<Usuario> usuarios = usuarioPersistencia.listar();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioPersistencia.listar();
    }

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

    public void excluirUsuario(String username) {
        boolean removido = usuarioPersistencia.remover(username);
        if (removido) {
            System.out.println("Usuário " + username + " excluído com sucesso!");
        } else {
            System.out.println("Erro: Usuário não encontrado.");
        }
    }

    public void alterarSenha(Usuario usuario, String novaSenha) {
        usuario.setSenha(novaSenha);
        usuarioPersistencia.atualizar(usuario);
        System.out.println("Sua senha foi alterada com sucesso!");
    }
}
