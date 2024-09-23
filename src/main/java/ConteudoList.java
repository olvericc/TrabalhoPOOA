import java.util.ArrayList;
import java.util.List;

public class ConteudoList implements Persistencia<Conteudo> {

    private final List<Conteudo> conteudos = new ArrayList<>();

    @Override
    public void save(Conteudo conteudo) {
        conteudos.add(conteudo);
    }

    @Override
    public List<Conteudo> listar() {
        return new ArrayList<>(conteudos);
    }

    @Override
    public void atualizar(Conteudo conteudo) {
        for (int i = 0; i < conteudos.size(); i++) {
            if (conteudos.get(i).getId().equals(conteudo.getId())) {
                conteudos.set(i, conteudo);
                return;
            }
        }
    }

    @Override
    public boolean remover(int id) {
        return false;
    }

    // Corrigir o modificador de acesso para "public"
    @Override
    public boolean remover(String titulo) {
        return conteudos.removeIf(conteudo -> conteudo.getTitulo().equals(titulo));
    }
}
