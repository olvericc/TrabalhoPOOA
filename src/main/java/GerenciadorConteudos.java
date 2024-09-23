import java.util.List;

public class GerenciadorConteudos {
    private Repositorio<Conteudo> repositorio;

    public GerenciadorConteudos(Repositorio<Conteudo> repositorio) {
        this.repositorio = repositorio;
    }

    public void criarConteudo(String titulo) {
        Conteudo conteudo = new Conteudo(titulo);
        repositorio.salvar(conteudo);
    }

    public Conteudo buscarConteudo(String titulo) {
        return repositorio.buscar(titulo);
    }

    public void atualizarConteudo(Conteudo conteudo) {
        repositorio.atualizar(conteudo);
    }

    public void deletarConteudo(String titulo) {
        repositorio.deletar(titulo);
    }

    public List<Conteudo> listarConteudos() {
        return repositorio.listar();
    }
}
