import java.util.ArrayList;
import java.util.List;

public class RepositorioEmMemoria<T> implements Repositorio<T> {
    private List<T> dados = new ArrayList<>();

    @Override
    public void salvar(T entidade) {
        dados.add(entidade);
    }

    @Override
    public T buscar(String id) {
        // Aqui, a busca pode ser personalizada conforme a estrutura das entidades
        return dados.stream().filter(e -> e.toString().contains(id)).findFirst().orElse(null);
    }

    @Override
    public void atualizar(T entidade) {
        deletar(entidade.toString());
        salvar(entidade);
    }

    @Override
    public void deletar(String id) {
        dados.removeIf(e -> e.toString().contains(id));
    }

    @Override
    public List<T> listar() {
        return dados;
    }
}
