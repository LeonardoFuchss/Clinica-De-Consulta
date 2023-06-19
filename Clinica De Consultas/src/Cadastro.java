
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Cadastro {
    Scanner scanner = new Scanner(System.in);
    List<Paciente> pacientesCadastrados = new ArrayList<>(); // Lista de clientes cadastrados.
    List<Consulta> consultasMarcadas = new ArrayList<>(); // Lista de consultas marcadas.

    public void CadastrarPacientes() throws IOException { // Método de cadastro de clientes
        try {

            pacientesCadastrados.clear();

            System.out.println("=== CADASTRAR PACIENTES ===");
            scanner.nextLine();


            System.out.println(" Digite o nome do paciente: "); // Armazenamos a entrada do usuario em uma variavel
            String nome = scanner.nextLine();

            System.out.println(" Agora digite o numero de telefone do paciente: ");
            long numero = scanner.nextLong();

            Paciente paciente = new Paciente(nome, numero); // Declaramos o objeto cliente a passamos por parametro as variaveis da interacao, ja que são as mesmas variaveis dos atributos.


            paciente.CadastroPaciente(); //Chama o metodo cadastroCliente para armazenar no arquivo.
            pacientesCadastrados.add(paciente);
            System.out.println(" Paciente cadastrado com sucesso! ");
            System.out.println("Aperte enter para retornar ao menu: ");
            scanner.nextLine();
            scanner.nextLine();


        } catch (Exception E) {
            System.out.println("Ocorreu um erro! Por favor, digite nomes e numeros validos. ");
            System.out.println("Aperte enter para voltar ao menu: ");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void MarcarConsulta() throws IOException { //metodo para marcar consulta.
        File arquivo = new File("pacientes.txt");


        //Se o arquivo estiver vazio, será exibida a mensagem

        if (arquivo.length() == 0) {
            System.out.println("Nao ha clientes cadastrados.");
            System.out.println("Aperte enter para voltar ao menu: ");
            scanner.nextLine();
            scanner.nextLine();

        } else {
            boolean sair = false;

            while (!sair) { //Realiza a loop enquanto sair for false.

                pacientesCadastrados.clear();

                BufferedReader br = new BufferedReader(new FileReader("pacientes.txt")); //Objeto para Ler o arquivo
                String linha;
                while ((linha = br.readLine()) != null) { // Irá ler o arquivo enquanto a linha não for nula:

                    String[] atributos = linha.split(","); // Separando os atributos por virgula

                    String nome = atributos[0];
                    long numero = Long.parseLong(atributos[1]);

                    Paciente paciente = new Paciente(nome, numero); // Declarando valores do objeto como os atributos armazenados no arquivo.

                    pacientesCadastrados.add(paciente); // E consequentemente adiciona-los a lista de clientes cadastrados.

                    // Desta forma, sempre que o metodo marcarConsulta for chamado, vai ler as linhas do arquivo e adiciona-las a lista de clientesCadastrados.

                }
                System.out.println(" ========== Lista de pacientes cadastrados ========== ");
                Paciente.ExibirPaciente(pacientesCadastrados);


                System.out.println(" Selecione o cliente para agendar uma consulta (ou 0 para voltar):");

                int escolha = scanner.nextInt();
                scanner.nextLine();

                try {
                    if (escolha >= 1 && escolha <= (pacientesCadastrados.size())) { //Então chamamos a lista de clientes cadastrados pro cliente escolher.

                        Paciente pacienteSelecionado = pacientesCadastrados.get(escolha - 1);
                        System.out.println("Cliente selecionado: " + pacienteSelecionado.getNome());


                        System.out.println("Digite a data e hora da consulta: (No formato dd/mm/yyyy | HH:mm) "); //Pedimos pro usuário definir a data e hora da consulta e armazenamos como um DateTimeFormatter
                        String dataStr = scanner.nextLine();

                        DateTimeFormatter dataConsulta = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); //Definindo o formato da data.

                        LocalDateTime dataFormatada = LocalDateTime.parse(dataStr, dataConsulta);
                        String DATA = dataFormatada.format(dataConsulta); // formatando a data.


                        if (dataFormatada.isBefore(LocalDateTime.now())) { //Verificando se a entrada do usuario é uma data valida.
                            System.out.println("Data invalida. A data deve ser posterior ou igual a de hoje. Pressione 1 para voltar. ");
                            String voltarStr = scanner.nextLine();
                            int voltar = Integer.parseInt(voltarStr);

                            if (voltar == 1) {
                                break;
                            }
                        }

                        System.out.println("Agora, digite a especialidade da consulta: ");
                        String especialidade = scanner.nextLine();



                        Consulta consulta = new Consulta(pacienteSelecionado.getNome(), especialidade, DATA); //Definimos os atributos da consulta em parametro. Chamando apenas o nome do cliente selecionado pelo usuário, já que é do tipo String.

                        consulta.MarcarConsultaArquivo(); // Chamamos o método MarcarConsultaArquivo para salvar a consulta no arquivo.

                        consultasMarcadas.add(consulta); // E adicionamos a lista de consultas marcadas(Iremos usa-la para remover um cliente

                        System.out.println(" Consulta marcada com sucesso! ");
                        System.out.println(" Aperte enter para retornar ao menu: ");
                        scanner.nextLine();
                        break;


                    } else if (escolha == 0) {
                        sair = true;

                    } else {
                        System.out.println(" Opcao invalida. ");
                    }

                } catch (Exception e) {
                    System.out.println("Ocorreu um erro. Digite datas e especialidades validas. Aperte 0 para voltar: ");
                    String voltarStr = scanner.nextLine();
                    int voltar = Integer.parseInt(voltarStr);
                    if (voltar == 0) {
                        break;
                    }
                }
            }
        }
    }

    public void CancelarConsulta() throws IOException {
        File arquivo = new File("consultas.txt");

        if (arquivo.length() == 0) {
            System.out.println(" Nao ha consultas marcadas. ");
            System.out.println(" Aperte enter para voltar ao menu: ");
            scanner.nextLine();
            scanner.nextLine();
        } else {
            boolean sair = false;

            while (!sair) {


                try {
                    consultasMarcadas.clear(); //Toda vez que o metodo cancelar consulta for chamado, ira apagar a lista de consultas para não gerar duplicação.

                    BufferedReader reader = new BufferedReader(new FileReader("consultas.txt"));  // Abrindo o arquivo "consultas.txt"
                    String linha;


                    while ((linha = reader.readLine()) != null) { // Lendo cada linha do arquivo enquando não for nulo.


                        String[] atributos = linha.split(","); // Dividindo os atributos em partes separadas por virgula.

                        String pacienteArq = (atributos[0]);
                        String especialidadeArq = (atributos[1]);
                        String dataArq = (atributos[2]);


                        Consulta consulta = new Consulta(pacienteArq, especialidadeArq, dataArq);
                        consultasMarcadas.add(consulta);
                    }


                    System.out.println("Consultas Marcadas: ");
                    Consulta.ExibirConsultas(consultasMarcadas);


                    System.out.println(" Selecione uma consulta que queira cancelar (ou 0 para voltar): ");
                    int escolha = scanner.nextInt();


                    if (escolha >= 1 && escolha <= (consultasMarcadas.size())) {
                        Consulta consultaSelecionada = consultasMarcadas.get(escolha - 1);
                        System.out.println(" Consulta selecionada: " + consultaSelecionada.getPacientes() + " " + consultaSelecionada.getData());
                        consultasMarcadas.remove(escolha - 1);
                        System.out.println(" Consulta removida com sucesso! ");
                        System.out.println(" Pressione enter para voltar: ");
                        scanner.nextLine();
                        scanner.nextLine();


                        BufferedWriter bww = new BufferedWriter(new FileWriter("consultas.txt")); //Metodo para escrever no arquivo.
                        for (Consulta consulta : consultasMarcadas) {
                            String linhaConsulta = consulta.getPacientes() + "," + consulta.getEspecialidade() + "," + consulta.getData();
                            bww.write(linhaConsulta);
                            bww.newLine();
                            //Ira escrever no arquivo consultas.txt de acordo com a lista atualizada de consultas.
                        }
                        bww.close();


                    } else if (escolha == 0) {
                        sair = true;
                    }
                } catch (Exception e) {
                    System.out.println("Opcao invalida. Aperte enter para retornar ao menu: ");
                    scanner.nextLine();
                    scanner.nextLine();
                }
            }
        }
    }

    public void MostrarPacientesCadastrados() throws IOException { // Chama o metodo exibir clientes, que lê o arquivo e mostra na tela.

        Paciente.ExibirPaciente(pacientesCadastrados);

        System.out.println("Aperte enter para retornar ao menu: ");
        scanner.nextLine();
        scanner.nextLine();

    }

    public void MostrarConsultasMarcadas() throws IOException {  // Chama o metodo exibir clientes, que lê o arquivo e mostra na tela.

        Consulta.ExibirConsultas(consultasMarcadas);

        System.out.println("Aperte enter para retornar ao menu: ");
        scanner.nextLine();
        scanner.nextLine();
    }

    public void Menu() throws IOException { //Menu de interação como o usuário.

        boolean sair = false;


        while (!sair) {
            try {
                System.out.println("-------------- CLINICA --------------");
                System.out.println("1. Cadastrar pacientes ");
                System.out.println("2. Marcar consultas ");
                System.out.println("3. Cancelar consultas ");
                System.out.println("4. Pacientes cadastrados ");
                System.out.println("5. Consultas marcadas ");
                System.out.println("6. Sair ");
                System.out.println("-------------------------------------");

                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        CadastrarPacientes();
                        break;
                    case 2:
                        MarcarConsulta();
                        break;
                    case 3:
                        CancelarConsulta();
                        break;
                    case 4:
                        MostrarPacientesCadastrados();
                        break;
                    case 5:
                        MostrarConsultasMarcadas();
                        break;
                    case 6:
                        sair = true;
                        System.out.println(" Programa encerrado! ");
                        break;
                    default:
                        System.out.println("Opcao invalida. Aperte enter para retornar ao menu: ");
                        scanner.nextLine();
                        scanner.nextLine();
                }
            } catch (Exception E) {
                System.out.println("Opcao invalida. Aperte enter para retornar ao menu: ");
                scanner.nextLine();
                scanner.nextLine();
            }
        }
    }
}









