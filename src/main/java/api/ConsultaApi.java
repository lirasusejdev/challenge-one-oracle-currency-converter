package api;

import com.google.gson.Gson;
import model.Moedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe responsável por consultar a API de taxas de câmbio.
 */
public class ConsultaApi {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static final String BASE_CURRENCY = "USD";
    private final HttpClient httpClient;

    /**
     * Construtor da classe ConsultaApi.
     * Inicializa o HttpClient para reutilização.
     */
    public ConsultaApi() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Consulta a API de taxas de câmbio e retorna os dados como um objeto Moedas.
     *
     * @param apiKey A chave da API para autenticação.
     * @return Um objeto Moedas contendo os dados retornados pela API.
     * @throws IllegalArgumentException Se a chave da API for nula ou vazia.
     * @throws RuntimeException         Se ocorrer um erro ao consultar a API.
     */
    public Moedas buscaMoedas(String apiKey) {
        validarApiKey(apiKey);

        URI uri = construirUri(apiKey);
        System.out.println("URL da API: " + uri);

        HttpRequest request = construirRequisicao(uri);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validarResposta(response);

            return converterRespostaParaMoedas(response.body());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar a API de moedas.", e);
        }
    }

    /**
     * Valida se a chave da API é válida.
     *
     * @param apiKey A chave da API.
     * @throws IllegalArgumentException Se a chave for nula ou vazia.
     */
    private void validarApiKey(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("A chave da API não pode ser nula ou vazia.");
        }
    }

    /**
     * Constrói a URI para a requisição da API.
     *
     * @param apiKey A chave da API.
     * @return A URI construída.
     */
    private URI construirUri(String apiKey) {
        return URI.create(BASE_URL + apiKey + "/latest/" + BASE_CURRENCY);
    }

    /**
     * Constrói a requisição HTTP.
     *
     * @param uri A URI da requisição.
     * @return O objeto HttpRequest.
     */
    private HttpRequest construirRequisicao(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }

    /**
     * Valida a resposta da API.
     *
     * @param response A resposta HTTP.
     * @throws RuntimeException Se o código de status não for 200.
     */
    private void validarResposta(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        System.out.println("Código de status HTTP: " + statusCode);

        if (statusCode != 200) {
            throw new RuntimeException("Erro ao consultar a API: Código de status " + statusCode);
        }
    }

    /**
     * Converte a resposta JSON para um objeto Moedas.
     *
     * @param responseBody O corpo da resposta JSON.
     * @return O objeto Moedas.
     */
    private Moedas converterRespostaParaMoedas(String responseBody) {
        System.out.println("Resposta da API: " + responseBody);
        return new Gson().fromJson(responseBody, Moedas.class);
    }
}