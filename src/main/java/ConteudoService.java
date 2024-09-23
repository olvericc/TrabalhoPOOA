public class ConteudoService {

    private final Persistencia<Conteudo> conteudoPersistencia;

    public ConteudoService(Persistencia<Conteudo> conteudoPersistencia) {
        this.conteudoPersistencia = conteudoPersistencia;
    }

    // Método que cria um conteúdo sem o campo "corpo"
    public void criarConteudoSemCorpo(Integer id, String titulo, String texto, Usuario autor) {
        Conteudo conteudo = new Conteudo(id, titulo, texto, autor);
        conteudoPersistencia.save(conteudo);
        System.out.println("Conteúdo criado com sucesso!");
    }

    // Método que cria um conteúdo com o campo "corpo"
    public void criarConteudoComCorpo(Integer id, String titulo, String texto, Usuario autor, String corpo) {
        Conteudo conteudo = new Conteudo(id, titulo, texto, autor, corpo);
        conteudoPersistencia.save(conteudo);
        System.out.println("Conteúdo com corpo criado com sucesso!");
    }

    // Listar conteúdos
    public void listarConteudos() {
        conteudoPersistencia.listar().forEach(System.out::println);
    }
}
