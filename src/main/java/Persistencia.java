import java.util.List;

public interface Persistencia<T> {
    void save(T t);
    List<T> listar();
    void atualizar(T t);

    boolean remover(int id);

    boolean remover(String id);
}
