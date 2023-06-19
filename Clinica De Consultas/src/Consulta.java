import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//Atributos da consulta
public class Consulta {
    private String data;
    private String especialidade;
    private String pacientes;

    public Consulta(String pacientes, String especialidade, String data) { // Construtor
        this.pacientes = pacientes;
        this.data = data;
        this.especialidade = especialidade;

    }

    //Método getter
    public String getData() {
        return data;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getPacientes() {
        return pacientes;
    }

    public void MarcarConsultaArquivo() throws IOException { // Metodo para marcar consulta.

        String nomeArquivo = "consultas.txt";
        File file = new File (nomeArquivo);
        file.createNewFile();

        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));  // Abrindo o arquivo "consultas.txt"
        String linha;


        while ((linha = reader.readLine()) != null) { // Lendo cada linha do arquivo enquando não for nulo.


            String[] atributos = linha.split(","); // Dividindo a linha em partes separadas.

            String pacientesArq = (atributos[0]);
            String especialidadeArq = (atributos[1]);
            String dataArq = (atributos[2]);


            if (pacientes.equals(pacientesArq) && data.equals(dataArq)) {  //Verificando se os atributos atuais são iguais os atributos armazenados.
                System.out.println(" Consulta ja existe! ");
                reader.close();
                return;

            }

        }
        reader.close();

        PrintWriter writer = new PrintWriter(new FileWriter("consultas.txt", true)); // Criando um objetp PrintWriter para escrever dentro do arquivo
        writer.println(pacientes + ", " + especialidade + "," + data); //Escrevendo no arquivo os atributos
        writer.close();

    }

    public static void ExibirConsultas(List<Consulta> consultas) throws IOException { //Metodo para exibir as consultas.


        BufferedReader reader = new BufferedReader(new FileReader("consultas.txt")); // Criando objeto para ler o arquivo
        String linha;
        int contador = 1; //Iniciando variável por 1, será usada para numerar as listas do arquivo


        while ((linha = reader.readLine()) != null) { // Lendo cada linha do arquivo enquando não for nulo.
            String[] atributos = linha.split(",");

            //Armazenando o nome e o numero de clientes cadastrados para exibir no sout

            String nome = (atributos[0]);
            String especialidadeArq = (atributos[1]);
            String dataArq = (atributos[2]);
            String dataVazia = dataArq.replace("'", "");


            System.out.println("========= CONSULTAS =========");

            System.out.println(contador + ". " + "NOME: " + nome + "\n" + "   ESPECIALIDADE:" + especialidadeArq + "\n" + "   DATA: " + dataVazia); //Exibição dos dados
            contador++;
            System.out.println("=============================");
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
        Consulta other = (Consulta) obj; // Convertendo obj para Cliente
        return pacientes.equals(other.getPacientes());
    }

}


