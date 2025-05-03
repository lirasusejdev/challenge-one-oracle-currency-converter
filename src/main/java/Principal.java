import service.ConversorDeMoedas;
import util.SecretsManagerUtil;

/**
 * Classe principal que inicia o programa.
 */
public class Principal {

    private static final String SECRET_NAME = "CurrencyConverterApiKey";
    private static final String ENV_VAR_NAME = "CURRENCY_CONVERTER_API_KEY";
    private static final String REGION = "us-east-1"; // Substitua pela sua região

    public static void main(String[] args) {
        try {
            // Recupera a chave de API
            String apiKey = recuperarChaveApi(args);

            // Inicia o conversor de moedas com a chave obtida
            ConversorDeMoedas conversor = new ConversorDeMoedas(apiKey);
            conversor.iniciar();
        } catch (Exception e) {
            exibirMensagemErro(e.getMessage());
        }
    }

    /**
     * Recupera a chave de API de diferentes fontes (linha de comando, variável de ambiente, Secrets Manager).
     *
     * @param args Argumentos passados pela linha de comando.
     * @return A chave de API.
     * @throws RuntimeException Se a chave não for encontrada.
     */
    private static String recuperarChaveApi(String[] args) {
        // Verifica se a chave foi passada por linha de comando
        if (args.length > 0 && !args[0].trim().isEmpty()) {
            System.out.println("Usando chave da API fornecida via linha de comando.");
            return args[0];
        }

        try {
            // Tenta recuperar a chave de outras fontes
            System.out.println("Tentando recuperar a chave da API...");
            String apiKey = SecretsManagerUtil.getApiKey(SECRET_NAME, ENV_VAR_NAME, REGION);

            // Valida a chave recuperada
            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("A chave da API não foi encontrada.");
            }

            return apiKey;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar a chave da API: " + e.getMessage(), e);
        }
    }

    /**
     * Exibe uma mensagem de erro e instruções para o usuário.
     *
     * @param mensagemErro A mensagem de erro a ser exibida.
     */
    private static void exibirMensagemErro(String mensagemErro) {
        System.err.println(mensagemErro);
        System.err.println("\nPara usar o conversor de moedas, você tem três opções:");
        System.err.println("1. Configure a variável de ambiente CURRENCY_CONVERTER_API_KEY");
        System.err.println("   No PowerShell, você pode usar: $env:CURRENCY_CONVERTER_API_KEY = \"sua-chave-api\"");
        System.err.println("2. Execute com chave temporária: mvn exec:java -Dexec.args=\"SUA_CHAVE_API\"");
        System.err.println("3. Configure credenciais AWS para acessar o Secrets Manager");
        System.err.println("\nSaindo do programa...");
    }
}