import api.ConsultaApi;

import java.util.Scanner;

public class ConversorDeMoedas {

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        ConsultaApi consultaApi = new ConsultaApi(); // Instância da classe que consulta a API
        String apiKey = "SUA_API_KEY"; // Substitua pela sua chave de API válida

        while (true) {
            System.out.println("=== Conversor de Moedas ===");
            System.out.println("1. Converter USD para outra moeda");
            System.out.println("2. Converter outra moeda para USD");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o código da moeda para conversão (ex: EUR, GBP, JPY): ");
                    String moedaDestino = scanner.next().toUpperCase();

                    System.out.print("Digite o valor em USD: ");
                    double valorEmUsd = scanner.nextDouble();

                    try {
                        Moedas moedas = consultaApi.buscaMoedas(apiKey);
                        Double taxaConversao = moedas.getConversion_rates().get(moedaDestino);

                        if (taxaConversao != null) {
                            double valorConvertido = valorEmUsd * taxaConversao;
                            System.out.printf("Valor convertido: %.2f %s%n", valorConvertido, moedaDestino);
                        } else {
                            System.out.println("Moeda não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao consultar a API: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite o código da moeda de origem (ex: EUR, GBP, JPY): ");
                    String moedaOrigem = scanner.next().toUpperCase();

                    System.out.print("Digite o valor na moeda de origem: ");
                    double valorNaMoedaOrigem = scanner.nextDouble();

                    try {
                        Moedas moedas = consultaApi.buscaMoedas(apiKey);
                        Double taxaConversao = moedas.getConversion_rates().get(moedaOrigem);

                        if (taxaConversao != null) {
                            double valorConvertido = valorNaMoedaOrigem / taxaConversao;
                            System.out.printf("Valor convertido: %.2f USD%n", valorConvertido);
                        } else {
                            System.out.println("Moeda não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao consultar a API: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Saindo do programa...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println(); // Linha em branco para separar as interações
        }
    }
}
