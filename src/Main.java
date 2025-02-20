    import javax.swing.text.DefaultEditorKit;
    import java.io.*;
    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            Definicoes definicoes = new Definicoes(
                    "C:\\Users\\pjtug\\Desktop\\GIT_repos\\ProjetoLp1\\src\\",
                    "C:\\Users\\pjtug\\Desktop\\GIT_repos\\ProjetoLp1\\src\\",
                    "C:\\Users\\pjtug\\Desktop\\GIT_repos\\ProjetoLp1\\src\\",
                    "C:\\Users\\pjtug\\Desktop\\GIT_repos\\ProjetoLp1\\src\\",
                    ",",
                    20,
                    2,
                    5.0,
                    "admin123"
            );

            Clientes[] clientes = LeitorClientes.lerClientesDoFicheiro(definicoes.getCaminhoClientes(), definicoes.getSeparadorFicheiros());
            Prato[] pratos = LeitorPratos.lerPratosDoFicheiro(definicoes.getCaminhoPratos(), definicoes.getSeparadorFicheiros());
            Reservas[] reservas = LeitorReservas.lerReservasDoFicheiro(definicoes.getCaminhoReservas(), definicoes.getSeparadorFicheiros());
            Mesa[] mesas = LeitorMesas.lerMesasDoFicheiro(definicoes.getCaminhoMesas(), definicoes.getSeparadorFicheiros());
            Pedidos[] pedidos = new Pedidos[0];
            int dia = 0;

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            // Array de opções do menu
            String[] opcoesMenu = {
                    "1. Gerir Mesas",
                    "2. Gerir Pratos",
                    "3. Consultar Estatísticas",
                    "4. Configurações",
                    "5. Gerir Dia-a-Dia",
                    "6. Sair"
            };

            while (running) {
                // Exibir o menu
                System.out.println("\n--- Menu Principal ---");
                for (String opcao : opcoesMenu) {
                    System.out.println(opcao);
                }

                // Ler a escolha do usuário
                System.out.print("Escolha uma opção: ");
                int escolha = scanner.nextInt();
                scanner.nextLine();


                switch (escolha) {
                    case 1:
                        gerirMesas(scanner, mesas);
                        break;
                    case 2:
                        gerirMenus(scanner, pratos);
                        break;
                    case 3:
                        consultarEstatisticas(pedidos, pratos);
                        break;
                    case 4:
                        configurarAplicacao(scanner, definicoes);
                        break;
                    case 5:
                        gerirDiaADia(definicoes, clientes, mesas, pratos, pedidos, dia);
                        break;
                    case 6:
                        sair();
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

            scanner.close();
        }

        private static void gerirMesas(Scanner scanner, Mesa[] mesas) {
            while (true) {
                System.out.println("\n--- Gestão de Mesas ---");
                System.out.println("1. Listar mesas");
                System.out.println("2. Adicionar mesa");
                System.out.println("3. Remover mesa");
                System.out.println("4. Alterar estado da mesa");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        GestorRestaurante.listarMesas(mesas);
                        break;
                    case 2:
                        mesas = GestorRestaurante.adicionarMesa(scanner, mesas);
                        break;
                    case 3:
                        mesas = GestorRestaurante.removerMesa(scanner, mesas);
                        break;
                    case 4:
                        GestorRestaurante.alterarEstadoMesa(scanner, mesas);
                        break;
                    case 5:
                        System.out.println("A sair da gestão de mesas.");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

        private static void gerirMenus(Scanner scanner, Prato[] pratos) {
            menuPratos(scanner, pratos, pratos.length);
        }


        private static void consultarEstatisticas(Pedidos[] pedidos, Prato[] pratos) {
            GestorRestaurante.consultarEstatisticas(pedidos, pratos);
        }

        private static void configurarAplicacao(Scanner scanner, Definicoes definicoes) {
            menuDefinicoes(scanner, definicoes);
        }
        private static void gerirDiaADia(Definicoes definicoes, Clientes[] clientes, Mesa[] mesas, Prato[] pratos, Pedidos[] pedidos, int dia) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Gerir Dia-a-Dia - Dia " + dia + " ---");
            System.out.println("1. Consultar Logs do Dia");
            System.out.println("2. Começar Novo Dia");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    consultarLogs(dia);
                    break;
                case 2:
                    dia++;
                    iniciarDia(definicoes, clientes, mesas, pratos, pedidos, dia);

                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        

        private static void consultarLogs(int dia) {
            String filename = "logs_dia_" + dia + ".txt";
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Nenhum log encontrado para o dia " + dia);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linha;
                System.out.println("Logs do dia " + dia + ":");
                System.out.println("-------------------------------------------------");
                System.out.printf("%-20s %-20s %-10s%n", "Cliente", "Prato", "Valor");
                System.out.println("-------------------------------------------------");

                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(","); // Supondo que os dados estão separados por vírgula
                    if (dados.length < 3) continue; // Ignorar linhas inválidas

                    String cliente = dados[0];
                    String nomePrato = dados[1];
                    double valor = Double.parseDouble(dados[2]);

                    // Exibir os dados formatados
                    System.out.printf("%-20s %-20s %-10.2f%n", cliente, nomePrato, valor);
                }
                System.out.println("-------------------------------------------------");
            } catch (IOException e) {
                System.out.println("Erro ao ler os logs: " + e.getMessage());
            }
        }



                private static int adicionarPrato(String[] pratosNomes, String nomePrato) {
                    for (int i = 0; i < pratosNomes.length; i++) {
                        if (pratosNomes[i] == null) { // Encontra um espaço vazio
                            pratosNomes[i] = nomePrato; // Adiciona o nome do prato
                            return i; // Retorna o índice do prato
                        }
                    }
                    return -1; // Retorna -1 se não houver espaço
                }


        private static void iniciarDia(Definicoes definicoes, Clientes[] clientes, Mesa[] mesas, Prato[] pratos, Pedidos[] pedidos, int dia) {
            Scanner scanner = new Scanner(System.in);
            boolean diaAtivo = true;
            int[] tempoAtual = {0};

            while (diaAtivo) {
                System.out.println("__________|Dia - " + dia + "|__________");
                System.out.println("1. Avançar Tempo");
                System.out.println("2. Adicionar Cliente");
                System.out.println("3. Atribuir Mesa");
                System.out.println("4. Registar Pedidos");
                System.out.println("5. Listar Clientes");
                System.out.println("6. Listar Pedidos");
                System.out.println("7. Terminar Dia");

                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        GestorRestaurante.avancarTempo(definicoes, clientes, mesas, pratos, tempoAtual);
                        break;
                    case 2:
                        // Coletar dados do cliente
                        System.out.print("Digite o nome da reserva: ");
                        String nomeReserva = scanner.nextLine();
                        System.out.print("Digite o número de pessoas: ");
                        int numPessoas = scanner.nextInt();
                        System.out.print("Digite o número de entradas: ");
                        int numEntradas = scanner.nextInt();
                        System.out.print("Digite o número de sobremesas: ");
                        int numSobremesas = scanner.nextInt();
                        System.out.print("Digite o tempo máximo de espera pela mesa: ");
                        int tempoMaxEsperaMesa = scanner.nextInt();
                        System.out.print("Digite o tempo máximo de espera pelo atendimento: ");
                        int tempoMaxEsperaAtendimento = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha

                        // Adiciona o cliente ao array de clientes
                        clientes = GestorRestaurante.adicionarCliente(clientes, nomeReserva, numPessoas, numEntradas, numSobremesas, tempoMaxEsperaMesa, tempoMaxEsperaAtendimento, tempoAtual[0]);
                        break;
                    case 3:
                        GestorRestaurante.listarMesas(mesas); // Chama o método para listar mesas

                        // Listar clientes à espera
                        System.out.println("\nClientes à espera:");
                        boolean clientesAguardando = false;
                        for (Clientes cliente : clientes) {
                            // Verifica se o cliente chegou e ainda está à espera
                            if (cliente.getChegadaUT() <= tempoAtual[0] &&
                                    (cliente.getChegadaUT() + cliente.getMaxUTatendimento() > tempoAtual[0])) {
                                int tempoRestante = (cliente.getChegadaUT() + cliente.getMaxUTatendimento()) - tempoAtual[0];
                                System.out.println(cliente.getNomeReserva() + " | Tempo restante: " + tempoRestante);
                                clientesAguardando = true;
                            }
                        }
                        if (!clientesAguardando) {
                            System.out.println("Nenhum cliente à espera.");
                        }

                        // Atribuir mesa
                        System.out.print("Digite o nome da reserva para atribuir mesa: ");
                        String nomeReservaMesa = scanner.nextLine();
                        System.out.print("Digite o número da mesa: ");
                        int numeroMesa = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha

                        // Atribui a mesa ao cliente, passando o tempoAtual
                        GestorRestaurante.atribuirMesa(clientes, mesas, nomeReservaMesa, numeroMesa, tempoAtual[0]);
                        break;
                    case 4:
                        GestorRestaurante.registarPedidos(scanner, pedidos, mesas, pratos, clientes, dia);
                        break;
                    case 5:
                        GestorRestaurante.listarClientes(clientes, mesas, tempoAtual[0]);
                        break;
                    case 6:
                        listarPedidos(pedidos);
                        break;
                    case 7:
                        System.out.println("Saindo do dia.");
                        diaAtivo = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
        private static void listarPedidos(Pedidos[] pedidos) {
            System.out.println("\n--- Lista de Pedidos ---");
            if (pedidos.length == 0) {
                System.out.println("Nenhum pedido registrado.");
                return;
            }
            for (Pedidos pedido : pedidos) {
                System.out.println(pedido.toString());
            }
        }


        private static void sair() {
            System.out.println("Encerrando o sistema. Até logo!");
        }

        private static void menuDefinicoes(Scanner scanner, Definicoes definicoes) {
            System.out.print("Insira a senha para acessar as configurações: ");
            String senha = scanner.nextLine();

            if (!definicoes.validarSenha(senha)) {
                System.out.println("Senha incorreta! Retornando ao menu principal.");
                return;
            }
            boolean sair = false;

            while (!sair) {
                System.out.println("\n====== Menu de Definições ======");
                System.out.println("1. Alterar Caminho dos Ficheiros");
                System.out.println("2. Alterar Separador dos Ficheiros");
                System.out.println("3. Alterar Unidades de Tempo do Dia");
                System.out.println("4. Alterar Tempo de Espera do Cliente");
                System.out.println("5. Alterar Custo por Cliente Não Atendido");
                System.out.println("6. Alterar Senha");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                switch (opcao) {
                    case 1:
                        // Alterar caminho dos ficheiros
                        System.out.println("Escolha o ficheiro para alterar o caminho:");
                        System.out.println("1. Caminho dos Clientes");
                        System.out.println("2. Caminho dos Pratos");
                        System.out.println("3. Caminho das Reservas");
                        System.out.print("Escolha uma opção: ");
                        int escolhaFicheiro = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha

                        switch (escolhaFicheiro) {
                            case 1:
                                System.out.print("Digite o novo caminho para os clientes: ");
                                String novoCaminhoClientes = scanner.nextLine();
                                definicoes.setCaminhoClientes(novoCaminhoClientes);
                                System.out.println("Caminho dos clientes atualizado.");
                                break;
                            case 2:
                                System.out.print("Digite o novo caminho para os pratos: ");
                                String novoCaminhoPratos = scanner.nextLine();
                                definicoes.setCaminhoPratos(novoCaminhoPratos);
                                System.out.println("Caminho dos pratos atualizado.");
                                break;
                            case 3:
                                System.out.print("Digite o novo caminho para as reservas: ");
                                String novoCaminhoReservas = scanner.nextLine();
                                definicoes.setCaminhoReservas(novoCaminhoReservas);
                                System.out.println("Caminho das reservas atualizado.");
                                break;
                            default:
                                System.out.println("Opção inválida. Retornando ao menu de definições.");
                        }
                        break;

                    case 2:
                        System.out.print("Digite o novo separador dos ficheiros: ");
                        String novoSeparador = scanner.nextLine();  // Leitura do novo separador
                        definicoes.setSeparadorFicheiros(novoSeparador);  // Atualiza o separador
                        System.out.println("Separador dos ficheiros atualizado.");
                        break;

                    case 3:
                        System.out.print("Digite as novas unidades de tempo do dia: ");
                        int novasUnidadesTempo = scanner.nextInt();  // Leitura do novo número de unidades de tempo
                        scanner.nextLine(); // Consumir a nova linha
                        definicoes.setUnidadesTempoDia(novasUnidadesTempo);  // Atualiza as unidades de tempo
                        System.out.println("Unidades de tempo do dia atualizadas.");
                        break;

                    case 4:
                        System.out.print("Digite o novo tempo de espera do cliente: ");
                        int novoTempoEspera = scanner.nextInt();  // Leitura do novo tempo de espera
                        scanner.nextLine(); // Consumir a nova linha
                        definicoes.setTempoEsperaCliente(novoTempoEspera);  // Atualiza o tempo de espera
                        System.out.println("Tempo de espera do cliente atualizado.");
                        break;

                    case 5:
                        System.out.print("Digite o novo custo por cliente não atendido: ");
                        double novoCusto = scanner.nextDouble();  // Leitura do novo custo
                        scanner.nextLine(); // Consumir a nova linha
                        definicoes.setCustoClienteNaoAtendido(novoCusto);  // Atualiza o custo por cliente não atendido
                        System.out.println("Custo por cliente não atendido atualizado.");
                        break;

                    case 6:
                        System.out.print("Digite a senha atual: ");
                        String senhaAtual = scanner.nextLine();  // Leitura da senha atual
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = scanner.nextLine();  // Leitura da nova senha

                        if (definicoes.alterarPassword(senhaAtual, novaSenha)) {  // Verifica se a senha foi alterada com sucesso
                            System.out.println("Senha alterada com sucesso.");
                        } else {
                            System.out.println("Senha atual incorreta. Não foi possível alterar.");
                        }
                        break;

                    case 7:
                        System.out.println("Saindo do menu de definições.");
                        sair = true;  // Sai do menu de configurações
                        break;

                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                        break;
                }
            }
        }

        private static void menuPratos(Scanner scanner, Prato[] pratos, int numeroPratos) {
            boolean sair = false;

            while (!sair) {
                System.out.println("\n====== Gerir pratos ======");
                System.out.println("1. Listar Pratos");
                System.out.println("2. Alterar Disponibilidade de um Prato");
                System.out.println("3. Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        GestorRestaurante.listarPratos(pratos, numeroPratos);
                        break;
                    case 2:
                        GestorRestaurante.alterarDisponibilidadePrato(scanner, pratos, numeroPratos); // Ensure this method is defined
                        break;
                    case 3:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            }
        }

    }