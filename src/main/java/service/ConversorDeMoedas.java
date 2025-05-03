package service;

import api.ConsultaApi;
import model.Moedas;

import java.util.Map;
import java.util.Scanner;

/**
 * Classe responsável por gerenciar o fluxo do conversor de moedas.
 */
public class ConversorDeMoedas {

    private final String apiKey;

    public ConversorDeMoedas(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("A chave da API não pode ser nula ou vazia.");
        }
        this.apiKey = apiKey;
    }

    public void iniciar() {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsultaApi consultaApi = new ConsultaApi();

            while (true) {
                exibirMenu();

                int opcao = obterOpcaoUsuario(scanner);

                switch (opcao) {
                    case 1 -> converterUsdParaOutraMoeda(scanner, consultaApi);
                    case 2 -> converterOutraMoedaParaUsd(scanner, consultaApi);
                    case 3 -> converterUsdParaBrl(scanner, consultaApi);
                    case 4 -> {
                        System.out.println("Saindo do programa...");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }

                System.out.println(); // Linha em branco para separar as interações
            }
        }
    }

    private void converterUsdParaBrl(Scanner scanner, ConsultaApi consultaApi) {
        double valorEmUsd = obterValorNumerico(scanner, "Digite o valor em USD: ");
        realizarConversao(consultaApi, valorEmUsd, "USD", "BRL");
    }

    private void converterUsdParaOutraMoeda(Scanner scanner, ConsultaApi consultaApi) {
        System.out.print("Digite o código da moeda para conversão (ex: EUR, GBP, JPY): ");
        String moedaDestino = scanner.next().toUpperCase();

        double valorEmUsd = obterValorNumerico(scanner, "Digite o valor em USD: ");
        realizarConversao(consultaApi, valorEmUsd, "USD", moedaDestino);
    }

    private void converterOutraMoedaParaUsd(Scanner scanner, ConsultaApi consultaApi) {
        System.out.print("Digite o código da moeda de origem (ex: EUR, GBP, JPY): ");
        String moedaOrigem = scanner.next().toUpperCase();

        double valorNaMoedaOrigem = obterValorNumerico(scanner, "Digite o valor na moeda de origem: ");
        realizarConversao(consultaApi, valorNaMoedaOrigem, moedaOrigem, "USD");
    }

    private void realizarConversao(ConsultaApi consultaApi, double valor, String moedaOrigem, String moedaDestino) {
        try {
            Moedas moedas = consultaApi.buscaMoedas(apiKey);
            Map<String, Double> taxasConversao = moedas.getConversion_rates();

            Double taxaOrigem = taxasConversao.get(moedaOrigem);
            Double taxaDestino = taxasConversao.get(moedaDestino);

            if (taxaOrigem == null || taxaDestino == null) {
                System.out.println("Moeda não encontrada. Verifique o código e tente novamente.");
                exibirMoedasDisponiveis(taxasConversao);
                return;
            }

            // Calcula o valor convertido
            double valorConvertido = (valor / taxaOrigem) * taxaDestino;
            System.out.printf("Valor convertido: %.2f %s%n", valorConvertido, moedaDestino);
        } catch (Exception e) {
            System.out.println("Erro ao consultar a API: " + e.getMessage());
        }
    }

    private void exibirMenu() {
        System.out.println("=== Conversor de Moedas ===");
        System.out.println("1. Converter de Dólar Americano (USD) para outra moeda");
        System.out.println("2. Converter de outra moeda para Dólar Americano (USD)");
        System.out.println("3. Converter de Dólar Americano (USD) para Real Brasileiro (BRL)");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int obterOpcaoUsuario(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next(); // Descarta a entrada inválida
        }
        return scanner.nextInt();
    }

    private double obterValorNumerico(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, insira um valor numérico válido.");
            scanner.next(); // Descarta a entrada inválida
        }
        return scanner.nextDouble();
    }

    private void exibirMoedasDisponiveis(Map<String, Double> conversionRates) {
        System.out.println("Moedas disponíveis para conversão:");
        for (String moeda : conversionRates.keySet()) {
            System.out.print(moeda + " ");
        }
        System.out.println();
    }
}