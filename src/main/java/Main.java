public class Main {
    public static void main(String[] args) {
        Persistencia<Usuario> usuarioPersistencia = new UsuarioHSQL();
        Persistencia<Conteudo> conteudoPersistencia = new ConteudoHSQL();

        SistemaCMS sistema = new SistemaCMS(usuarioPersistencia, conteudoPersistencia);
        sistema.iniciar();
    }
}
