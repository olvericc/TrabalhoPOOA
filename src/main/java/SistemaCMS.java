import java.util.Scanner;

public class SistemaCMS {
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorConteudos gerenciadorConteudos;
    private Usuario usuarioLogado;

    public SistemaCMS(GerenciadorUsuarios gerenciadorUsuarios, GerenciadorConteudos gerenciadorConteudos) {
        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.gerenciadorConteudos = gerenciadorConteudos;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            if (usuarioLogado == null) {
                System.out.println("Menu Inicial:");
                System.out.println("1. Login");
                System.out.println("2. Listar Conteúdos");
                System.out.println("3. Sair");
                int opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        listarConteudos();
                        break;
                    case 3:
                        executando = false;
                        break;
                }
            } else {
                System.out.println("Menu Após Login:");
                // Adicione os métodos para gerenciar Conteúdos e Usuários
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Digite o username:");
        String username = scanner.next();
        System.out.println("Digite a senha:");
        String senha = scanner.next();
        Usuario usuario = gerenciadorUsuarios.buscarUsuario(username);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioLogado = usuario;
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    private void listarConteudos() {
        gerenciadorConteudos.listarConteudos().forEach(System.out::println);
    }
}
