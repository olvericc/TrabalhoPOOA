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
        boolean executando = true;

        while (executando) {
            if (usuarioLogado == null) {
                mostrarMenuPublico(scanner);
            } else {
                mostrarMenuPrivado(scanner);
            }
        }
    }

    private void mostrarMenuPublico(Scanner scanner) {
        System.out.println("Menu Inicial:");
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
        System.out.println("Menu Após Login:");
        System.out.println("1. Criar Conteúdo");
        System.out.println("2. Listar Conteúdos");
        System.out.println("3. Atualizar Conteúdo");
        System.out.println("4. Excluir Conteúdo");
        System.out.println("5. Criar Usuário");
        System.out.println("6. Listar Usuários");
        System.out.println("7. Alterar Usuário");
        System.out.println("8. Excluir Usuário");
        System.out.println("9. Alterar Senha");
        System.out.println("10. Logout");
        int opcao = scanner.nextInt();

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
            case 10 -> logout();
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Digite o username:");
        String username = scanner.next();
        System.out.println("Digite a senha:");
        String senha = scanner.next();

        Usuario usuario = usuarioPersistencia.listar().stream()
                .filter(u -> u.getUsername().equals(username) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            usuarioLogado = usuario;
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Credenciais inválidas.");
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
        System.out.println("Digite o ID do conteúdo a ser excluído:");
        int id = scanner.nextInt();
        boolean sucesso = conteudoPersistencia.remover(id);
        if (sucesso) {
            System.out.println("Conteúdo excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir conteúdo.");
        }
    }

    private void criarUsuario(Scanner scanner) {
        System.out.println("Digite o username:");
        String username = scanner.next();
        System.out.println("Digite a senha:");
        String senha = scanner.next();
        Usuario usuario = new Usuario(username, senha);
        usuarioPersistencia.save(usuario);
        System.out.println("Usuário criado com sucesso!");
    }

    private void listarUsuarios() {
        usuarioPersistencia.listar().forEach(System.out::println);
    }

    private void alterarUsuario(Scanner scanner) {
        System.out.println("Digite o username do usuário a ser alterado:");
        String username = scanner.next();
        Usuario usuario = usuarioPersistencia.listar().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        System.out.println("Digite a nova senha:");
        String novaSenha = scanner.next();
        usuario.setSenha(novaSenha);
        usuarioPersistencia.atualizar(usuario);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void excluirUsuario(Scanner scanner) {
        System.out.println("Digite o username do usuário a ser excluído:");
        String username = scanner.next();
        Usuario usuario = usuarioPersistencia.listar().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        boolean sucesso = usuarioPersistencia.remover(usuarioPersistencia.listar().indexOf(usuario));
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
        System.out.println("Logout realizado com sucesso.");
    }
}
