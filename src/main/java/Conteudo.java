public class Conteudo {

    private Integer id;
    private String titulo;
    private String texto;
    private Usuario autor;
    private String corpo;

    public Conteudo(Integer id, String titulo, String texto, Usuario autor, String corpo) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.autor = autor;
        this.corpo = corpo;
    }

    public Conteudo(Integer id, String titulo, String texto, Usuario autor) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.autor = autor;
        this.corpo = "";
    }

    public Conteudo(String titulo) {
        this.titulo = titulo;
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCorpo() {
        return corpo;
    }

    @Override
    public String toString() {
        return "Conteudo [id=" + id + ", titulo=" + titulo + ", autor=" + autor.getUsername() + "]";
    }
}
