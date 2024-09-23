import java.util.Scanner;

public class SistemaCMS {
    private final Persistencia<Usuario> usuarioPersistencia;
    private final Persistencia<Conteudo> conteudoPersistencia;
    private Usuario usuarioLogado;

    public SistemaCMS(Persistencia<Usuario> usuarioPersistencia, Persistencia<Conteudo> conteudoPersistencia) {
        this.usuarioPersistencia = usuarioPersistencia;
        this.conteudoPersistencia = conteudoPersistencia;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (usuarioLogado == null) {
                mostrarMenuPublico(scanner);
            } else {
                mostrarMenuPrivado(scanner);
            }
        }
    }

    private void mostrarMenuPublico(Scanner scanner) {
        System.out.println("Menu Inicial: (público)");
        System.out.println("1. Login");
        System.out.println("2. Listar Conteúdos");
        System.out.println("3. Sair");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> login(scanner);
            case 2 -> listarConteudos();
            case 3 -> System.exit(0);
        }
    }

    private void mostrarMenuPrivado(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Olá, " + usuarioLogado.getUsername() + "!");
            System.out.println("Seja bem-vindo ao CMS (menu privado após login), agora vamos lá:");
            System.out.println();
            System.out.println("+--------CRUD DE CONTEÚDO---------+");
            System.out.println("|                                 |");
            System.out.println("|      1. Criar Conteúdo          |");
            System.out.println("|      2. Listar Conteúdo         |");
            System.out.println("|      3. Atualizar Conteúdo      |");
            System.out.println("|      4. Excluir Conteúdo        |");
            System.out.println("|                                 |");
            System.out.println("+--------CRUD DE USUÁRIO----------+");
            System.out.println("|                                 |");
            System.out.println("|      5. Criar Usuário           |");
            System.out.println("|      6. Listar Usuário          |");
            System.out.println("|      7. Alterar Usuário         |");
            System.out.println("|      8. Excluir Usuário         |");
            System.out.println("|                                 |");
            System.out.println("+---------OUTRAS OPÇÕES-----------+");
            System.out.println("|                                 |");
            System.out.println("|      9. Alterar Senha           |");
            System.out.println("|      10. Logout                 |");
            System.out.println("|                                 |");
            System.out.println("+---------------------------------+");
            System.out.print("Escolha uma opção: ");
            System.out.println();

            if (scanner.hasNextInt()) {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> criarConteudo(scanner);
                    case 2 -> listarConteudos();
                    case 3 -> atualizarConteudo(scanner);
                    case 4 -> excluirConteudo(scanner);
                    case 5 -> criarUsuario(scanner);
                    case 6 -> listarUsuarios();
                    case 7 -> alterarUsuario(scanner);
                    case 8 -> excluirUsuario(scanner);
                    case 9 -> alterarSenha(scanner);
                    case 10 -> {
                        logout();
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine();
            }
        }
    }


    private void login(Scanner scanner) {
        System.out.println("Digite o nome do usuário:");
        String username = scanner.next();
        System.out.println("Digite a senha do usuário:");
        String senha = scanner.next();

        if (username.equals(senha)) {
            usuarioLogado = new Usuario(username, senha);
            System.out.println("Login bem-sucedido!");
            System.out.println();
        } else {
            System.out.println("Credenciais inválidas.");
            System.out.println();
        }
    }

    private void listarConteudos() {
        conteudoPersistencia.listar().forEach(System.out::println);
    }

    private void criarConteudo(Scanner scanner) {
        System.out.println("Digite o título do conteúdo:");
        String titulo = scanner.next();
        System.out.println("Digite o texto do conteúdo:");
        String texto = scanner.next();
        Conteudo conteudo = new Conteudo(null, titulo, texto, usuarioLogado);
        conteudoPersistencia.save(conteudo);
        System.out.println("Conteúdo criado com sucesso!");
    }

    private void atualizarConteudo(Scanner scanner) {
        System.out.println("Digite o ID do conteúdo a ser atualizado:");
        int id = scanner.nextInt();
        Conteudo conteudo = conteudoPersistencia.listar().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (conteudo == null) {
            System.out.println("Conteúdo não encontrado.");
            return;
        }
        System.out.println("Digite o novo título:");
        String novoTitulo = scanner.next();
        System.out.println("Digite o novo texto:");
        String novoTexto = scanner.next();
        conteudo.setTitulo(novoTitulo);
        conteudo.setTexto(novoTexto);
        conteudoPersistencia.atualizar(conteudo);
        System.out.println("Conteúdo atualizado com sucesso!");
    }

    private void excluirConteudo(Scanner scanner) {
        System.out.println("Digite o título do conteúdo a ser excluído:");
        String titulo = scanner.next();
        boolean sucesso = conteudoPersistencia.remover(titulo);
        if (sucesso) {
            System.out.println("Conteúdo excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir conteúdo.");
        }
    }

    private void criarUsuario(Scanner scanner) {
        System.out.println("Digite o nome do usuário:");
        String username = scanner.next();
        System.out.println("Digite a senha do usuário:");
        String senha = scanner.next();
        Usuario usuario = new Usuario(username, senha);
        usuarioPersistencia.save(usuario);
        System.out.println("Novo usuário criado com sucesso!");
    }

    private void listarUsuarios() {
        System.out.println("INFORMAÇÕES DOS USUÁRIOS");
        for (Usuario usuario : usuarioPersistencia.listar()) {
            String senhaMascarada = "*".repeat(usuario.getSenha().length());
            System.out.println("Usuário [ Nome: " + usuario.getUsername() + ", Senha: " + senhaMascarada + " ]");
        }
    }

    private void alterarUsuario(Scanner scanner) {
        System.out.println("Digite o nome do usuário a ser alterado:");
        String username = scanner.next();

        Usuario usuario = usuarioPersistencia.listar().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Digite o novo nome de usuário (ou pressione Enter para manter o atual):");
        scanner.nextLine();
        String novoUsername = scanner.nextLine();
        if (!novoUsername.isEmpty()) {
            usuario.setUsername(novoUsername);
        }

        System.out.println("Digite a nova senha (ou pressione Enter para manter a atual):");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.isEmpty()) {
            usuario.setSenha(novaSenha);
        }

        usuarioPersistencia.atualizar(usuario);
        usuarioPersistencia.remover(username);
        usuarioPersistencia.save(usuario);

        System.out.println("A senha do Usuário Logado, " + usuario.getUsername() + " foi atualizada com sucesso!");
    }

    private void excluirUsuario(Scanner scanner) {
        System.out.println("Digite o nome do usuário a ser excluído:");
        String username = scanner.next();
        Usuario usuario = usuarioPersistencia.listar().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        boolean sucesso = usuarioPersistencia.remover(usuario.getUsername());
        if (sucesso) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir usuário.");
        }
    }

    private void alterarSenha(Scanner scanner) {
        System.out.println("Digite a nova senha:");
        String novaSenha = scanner.next();
        usuarioLogado.setSenha(novaSenha);
        usuarioPersistencia.atualizar(usuarioLogado);
        System.out.println("Senha alterada com sucesso!");
    }

    private void logout() {
        usuarioLogado = null;
        System.out.println("Logout realizado com sucesso, por favor, volte sempre!");
    }
}
