public class Conteudo {

    private Integer id;
    private String titulo;
    private String texto;
    private Usuario autor;

    public Conteudo(Integer id, String titulo, String texto, Usuario autor) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.titulo = titulo;
    }

    public Conteudo(String titulo) {
        this.titulo = titulo;
    }

    public Conteudo() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Conteudo [id=" + id + ", titulo=" + titulo + ", texto=" + texto + ", autor=" + autor + "]";
    }
}
