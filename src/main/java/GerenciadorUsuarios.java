import java.util.List;

public class GerenciadorUsuarios {
    private final Repositorio<Usuario> repositorio;

    public GerenciadorUsuarios(Repositorio<Usuario> repositorio) {
        this.repositorio = repositorio;
    }

    public void criarUsuario(String username, String senha) {
        Usuario usuario = new Usuario(username, senha);
        repositorio.salvar(usuario);
    }

    public Usuario buscarUsuario(String username) {
        return repositorio.buscar(username);
    }

    public void atualizarUsuario(Usuario usuario) {
        repositorio.atualizar(usuario);
    }

    public void deletarUsuario(String username) {
        repositorio.deletar(username);
    }

    public List<Usuario> listarUsuarios() {
        return repositorio.listar();
    }
}
