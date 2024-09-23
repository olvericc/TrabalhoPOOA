import java.util.List;

public interface Repositorio<T> {
    void salvar(T entidade);
    T buscar(String id);
    void atualizar(T entidade);
    void deletar(String id);
    List<T> listar();
}
