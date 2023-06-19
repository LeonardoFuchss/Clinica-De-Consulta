import java.io.*;
import java.util.List;

//Atributos do cliente
public class Paciente {
    private final String nome;
    private final long numeroTelefone;

    //Construtor
    public Paciente(String nome, long numeroTelefone) {
        this.nome = nome;
        this.numeroTelefone = numeroTelefone;

    }

    //Métodos Getters
    public String getNome() {
        return nome;
    }

    public final long getNumeroTelefone() {
        return numeroTelefone;
    }

    public void CadastroPaciente() throws IOException {
        String nomeArquivo = "pacientes.txt";
        File file = new File(nomeArquivo); //Criando um arquivo
        file.createNewFile();


        BufferedReader reader = new BufferedReader(new FileReader(file));  // Abrindo o arquivo "pacientes.txt" para ler.
        String linha;


        while ((linha = reader.readLine()) != null) { // Lendo cada linha do arquivo enquando não for nulo.


            String[] atributos = linha.split(","); // Definindo os indices como atributos e separando eles por virgulas.

            //Armazenando o nome e o numero do cliente cadastrado em arquivo. Ou seja: o indice 0 vai ser o nome e o indice 1 será o numero.
            String nomeCadastrado = atributos[0];
            long numeroCadastrado = Long.parseLong(atributos[1]);


            if (nome.equals(nomeCadastrado) && numeroTelefone == numeroCadastrado) {  //Verificando se os atributos atuais são iguais os atributos armazenados.
                System.out.println(" Paciente ja cadastrado! ");
                reader.close();
                return;
            }
        }
        reader.close();

        PrintWriter writer = new PrintWriter(new FileWriter("pacientes.txt", true)); // Criando um objetp PrintWriter para escrever dentro do arquivo
        writer.println(nome + "," + numeroTelefone); //Escrevendo no arquivo os atributos que vamos definir ao cadastrar um cliente.
        writer.close();

    }

    public static void ExibirPaciente(List<Paciente> clienteList) throws IOException { //Metodo para exibir os clientes cadastrados no arquivo.

        BufferedReader reader = new BufferedReader(new FileReader("pacientes.txt")); // Criando objeto para ler o arquivo

        String linha;  //

        int contador = 1; //Iniciando variável por 1, será usada para numerar as listas do arquivo

        while ((linha = reader.readLine()) != null) { // Lendo cada linha do arquivo enquando não for nulo.

            String[] atributos = linha.split(","); // Definindo os indices e dividindo os indices em partes separadas por virgula.

            //Criando os indices para ler na linha do arquivo e imprimi-los na tela.
            String nome = atributos[0];
            long numero = Long.parseLong(atributos[1]);

            System.out.println(contador + ". " + "NOME: " + nome + " :: " + " NUMERO: " + numero); // Exibição dos dados da linha
            contador++;
        }
        reader.close();

    }

    @Override
    //Método equals

    public boolean equals(Object obj) {
        if (this == obj) { // Verificando se o obj passado em parametro é igual o objeto de entrada.
            return true; //Se for, retorna true.
        }
        if (obj == null || getClass() != obj.getClass()) { // Verificando se os objetos são de classes iguals ou diferentes.
            return false;
        }
        Paciente other = (Paciente) obj; // Convertendo obj para Cliente
        return nome.equals(other.nome) && numeroTelefone == other.numeroTelefone;
    }

}


