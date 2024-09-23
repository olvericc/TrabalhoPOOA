import java.util.List;

public class ConteudoService {

    private Persistencia<Conteudo> conteudoPersistencia;

    public ConteudoService(Persistencia<Conteudo> conteudoPersistencia) {
        this.conteudoPersistencia = conteudoPersistencia;
    }

    public void save(Conteudo conteudo) {
        conteudoPersistencia.save(conteudo);
    }

    public List<Conteudo> listarConteudos() {
        // Certifica-se de que a lista retornada é válida
        List<Conteudo> conteudos = conteudoPersistencia.listar();
        if (conteudos == null || conteudos.isEmpty()) {
            System.out.println("Nenhum conteúdo disponível.");
        }
        return conteudos;
    }

    public void atualizarConteudo(int id, String titulo, String texto, Usuario autor) {
        List<Conteudo> conteudos = conteudoPersistencia.listar();
        for (Conteudo conteudo : conteudos) {
            if (conteudo.getId() != null && conteudo.getId() == id) {
                conteudo.setTitulo(titulo);
                conteudo.setTexto(texto);
                conteudo.setAutor(autor);
                conteudoPersistencia.atualizar(conteudo);
                return;
            }
        }
        System.out.println("Conteúdo não encontrado.");
    }

    public boolean removerConteudo(int id) {
        List<Conteudo> conteudos = conteudoPersistencia.listar();
        for (Conteudo conteudo : conteudos) {
            if (conteudo.getId() != null && conteudo.getId() == id) {
                return conteudoPersistencia.remover(conteudo.getTitulo());
            }
        }
        return false;
    }
}
