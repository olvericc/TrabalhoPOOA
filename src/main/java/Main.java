public class Main {
    public static void main(String[] args) {
        Repositorio<Usuario> repositorioUsuarios = new RepositorioEmMemoria<>();
        Repositorio<Conteudo> repositorioConteudos = new RepositorioEmMemoria<>();

        GerenciadorUsuarios gerenciadorUsuarios = new GerenciadorUsuarios(repositorioUsuarios);
        GerenciadorConteudos gerenciadorConteudos = new GerenciadorConteudos(repositorioConteudos);

        SistemaCMS sistema = new SistemaCMS(gerenciadorUsuarios, gerenciadorConteudos);
        sistema.iniciar();
    }
}
