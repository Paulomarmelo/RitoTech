import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorPratos {

    public static Prato[] lerPratosDoFicheiro(String caminho, String separador) {
        String filePath = caminho + "/Pratos.txt"; // Caminho do arquivo


        int maxPratos = 100; // Define o tamanho máximo do array
        Prato[] listaPratos = new Prato[maxPratos];
        int contador = 0; // Contador para acompanhar o número de pratos

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(separador);

                if (dados.length < 6) {
                    System.err.println("Linha inválida (campos insuficientes): " + linha);
                    continue; // Ignorar a linha inválida
                }

                try {
                    String nome = dados[0];
                    String categoria = dados[1];
                    double precoCusto = Double.parseDouble(dados[2]);
                    double precoVenda = Double.parseDouble(dados[3]);
                    int tempoPreparo = Integer.parseInt(dados[4]);
                    boolean disponivel = Boolean.parseBoolean(dados[5]);

                    Prato prato = new Prato(nome, precoCusto, categoria, precoVenda, tempoPreparo, disponivel);

                    // Armazena o prato no array
                    listaPratos[contador] = prato;
                    contador++;
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter valores numéricos na linha: " + linha);
                }

                // Verifica se o limite do array foi atingido
                if (contador >= listaPratos.length) {
                    System.err.println("Array de pratos está cheio. Alguns pratos podem não ser carregados.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return truncarArray(listaPratos, contador);
    }

    private static Prato[] truncarArray(Prato[] listaPratos, int tamanho) {
        Prato[] resultado = new Prato[tamanho];
        System.arraycopy(listaPratos, 0, resultado, 0, tamanho);
        return resultado;
    }
}
