import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaApi {

    public Moedas buscaMoedas(String apiKey) {
        // Cria a URI com a chave da API
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD");

        // Cria a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        try {
            // Envia a requisição e obtém a resposta
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Converte o JSON da resposta para a classe Moedas
            return new Gson().fromJson(response.body(), Moedas.class);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível obter os dados da API de moedas.", e);
        }
    }
}