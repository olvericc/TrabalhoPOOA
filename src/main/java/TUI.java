import java.util.Scanner;

public class TUI extends UI {

    private Scanner scanner = new Scanner(System.in);
    private UsuarioService usuarioService = new UsuarioService();
    private ConteudoService conteudoService = new ConteudoService(new ConteudoHSQL());
    private Usuario currentUser;

    // Menu inicial (público)
    public void mostrarMenuInicial() {
        while (true) {
            System.out.println("Menu Inicial:");
            System.out.println("1. Login");
            System.out.println("2. Listar Conteúdos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha pendente

            switch (opcao) {
                case 1:
                    currentUser = login();
                    if (currentUser != null) {
                        mostrarMenuConteudo(); // Exibe o menu correto após o login
                    }
                    break;
                case 2:
                    listarConteudo();
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Método de login
    private Usuario login() {
        System.out.print("Digite o username: ");
        String username = scanner.nextLine();
        System.out.print("Digite o password: ");
        String password = scanner.nextLine();

        // Login com nome de usuário e senha iguais
        if (username.equals(password)) {
            currentUser = usuarioService.validarLogin(username, password);
            if (currentUser != null) {
                System.out.println("Login bem-sucedido!");
                return currentUser;
            }
        }
        System.out.println("Credenciais inválidas.");
        return null;
    }

    // Menu após login (para usuários autenticados)
    private void mostrarMenuConteudo() {
        while (currentUser != null) {
            System.out.println("Menu Após Login:");
            System.out.println("1. Criar Conteúdo");
            System.out.println("2. Listar Conteúdo");
            System.out.println("3. Atualizar Conteúdo");
            System.out.println("4. Excluir Conteúdo");
            System.out.println("5. Criar Usuário");
            System.out.println("6. Listar Usuários");
            System.out.println("7. Alterar Usuário");
            System.out.println("8. Excluir Usuário");
            System.out.println("9. Alterar Senha");
            System.out.println("10. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (opcao) {
                case 1:
                    criarConteudo();
                    break;
                case 2:
                    listarConteudo();
                    break;
                case 3:
                    atualizarConteudo();
                    break;
                case 4:
                    excluirConteudo();
                    break;
                case 5:
                    criarUsuario(); // Chama o método para criar usuário
                    break;
                case 6:
                    listarUsuarios(); // Lista todos os usuários
                    break;
                case 7:
                    alterarUsuario(); // Altera um usuário
                    break;
                case 8:
                    excluirUsuario(); // Exclui um usuário
                    break;
                case 9:
                    alterarSenha(); // Altera a senha do usuário logado
                    break;
                case 10:
                    currentUser = null; // Logout
                    System.out.println("Logout realizado com sucesso.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Implementação dos métodos de conteúdo e usuários
    private void criarConteudo() {
        System.out.println("Criação de Conteúdo:");
        String titulo = lerInfo("Digite o Título");
        String texto = lerInfo("Digite o Texto");
        Conteudo conteudo = new Conteudo(null, titulo, texto, currentUser);
        conteudoService.save(conteudo);
        System.out.println("Conteúdo criado com sucesso!");
    }

    private void listarConteudo() {
        System.out.println("Lista de Conteúdos:");
        for (Conteudo conteudo : conteudoService.listarConteudos()) {
            System.out.println(conteudo);
        }
    }

    private void atualizarConteudo() {
        System.out.println("Atualização de Conteúdo:");
        String id = lerInfo("Digite o ID do Conteúdo");
        String titulo = lerInfo("Digite o novo Título");
        String texto = lerInfo("Digite o novo Texto");
        conteudoService.atualizarConteudo(Integer.parseInt(id), titulo, texto, currentUser);
        System.out.println("Conteúdo atualizado com sucesso!");
    }

    private void excluirConteudo() {
        System.out.println("Exclusão de Conteúdo:");
        String id = lerInfo("Digite o ID do Conteúdo");
        boolean removido = conteudoService.removerConteudo(Integer.parseInt(id));
        if (removido) {
            System.out.println("Conteúdo excluído com sucesso!");
        } else {
            System.out.println("Conteúdo não encontrado.");
        }
    }

    // Métodos de usuários
    private void criarUsuario() {
        System.out.println("Criação de Usuário:");
        String username = lerInfo("Digite o Username");
        String senha = lerInfo("Digite a Senha");
        Usuario novoUsuario = new Usuario(username, senha);
        usuarioService.criarUsuario(novoUsuario);
        System.out.println("Usuário criado com sucesso!");
    }

    private void listarUsuarios() {
        System.out.println("Lista de Usuários:");
        for (Usuario usuario : usuarioService.listarUsuarios()) {
            System.out.println(usuario);
        }
    }

    private void alterarUsuario() {
        System.out.println("Alteração de Usuário:");
        String username = lerInfo("Digite o Username do Usuário a ser alterado");
        String novaSenha = lerInfo("Digite a nova Senha");
        usuarioService.alterarUsuario(username, novaSenha);
        System.out.println("Usuário alterado com sucesso!");
    }

    private void excluirUsuario() {
        System.out.println("Exclusão de Usuário:");
        String username = lerInfo("Digite o Username do Usuário");
        usuarioService.excluirUsuario(username);
        System.out.println("Usuário excluído com sucesso!");
    }

    private void alterarSenha() {
        System.out.println("Alteração de Senha:");
        String novaSenha = lerInfo("Digite a nova Senha");
        usuarioService.alterarSenha(currentUser, novaSenha);
        System.out.println("Sua senha foi alterada com sucesso!");
    }

    private String lerInfo(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }
}
