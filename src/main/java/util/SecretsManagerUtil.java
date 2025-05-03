package util;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitária para acessar segredos armazenados no AWS Secrets Manager ou variáveis de ambiente.
 */
public class SecretsManagerUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecretsManagerUtil.class);

    private static final String SKIP_AWS_PROPERTY = "skipAWS";
    private static final String SKIP_AWS_ENV = "SKIP_AWS";
    private static final String LOCAL_DEV_ENV = "LOCAL_DEV";

    /**
     * Recupera o valor de um segredo preferencialmente de uma variável de ambiente.
     * Se não encontrar, tenta buscar do AWS Secrets Manager.
     *
     * @param secretName Nome do segredo no Secrets Manager.
     * @param envVarName Nome da variável de ambiente.
     * @param region Região da AWS onde o segredo está armazenado.
     * @return O valor do segredo como uma string.
     * @throws RuntimeException Se não for possível recuperar o segredo.
     */
    public static String getApiKey(String secretName, String envVarName, String region) {
        boolean skipAWS = isLocalDevelopment();
        logger.info("Modo desenvolvimento local (skipAWS): {}", skipAWS);

        // Tenta obter da variável de ambiente
        String envValue = System.getenv(envVarName);
        if (envValue != null && !envValue.trim().isEmpty()) {
            logger.info("Chave da API obtida de variável de ambiente: {}", envVarName);
            return envValue;
        }

        // Se estamos em modo local e não tem variável de ambiente, não tenta AWS
        if (skipAWS) {
            throw new RuntimeException("Nenhuma chave de API encontrada. Por favor, defina a variável de ambiente " +
                    envVarName + " para usar o conversor de moedas em modo local.");
        }

        // Tenta buscar do AWS Secrets Manager
        logger.info("Variável de ambiente não encontrada. Tentando AWS Secrets Manager...");
        return getSecret(secretName, region);
    }

    /**
     * Recupera o valor de um segredo armazenado no AWS Secrets Manager.
     *
     * @param secretName Nome do segredo no Secrets Manager.
     * @param region Região da AWS onde o segredo está armazenado.
     * @return O valor do segredo como uma string.
     */
    public static String getSecret(String secretName, String region) {
        if (isLocalDevelopment()) {
            throw new RuntimeException("AWS access bypassed devido a skipAWS=true");
        }

        try (SecretsManagerClient client = createSecretsManagerClient(region)) {
            // Cria a requisição para obter o segredo
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            // Recupera o segredo
            GetSecretValueResponse response = client.getSecretValue(request);

            // Retorna o valor do segredo
            logger.info("Segredo recuperado com sucesso do AWS Secrets Manager.");
            return response.secretString();
        } catch (Exception e) {
            logger.error("Erro ao acessar AWS Secrets Manager: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao acessar AWS Secrets Manager: " + e.getMessage(), e);
        }
    }

    /**
     * Cria e retorna um cliente do AWS Secrets Manager.
     *
     * @param region Região da AWS onde o segredo está armazenado.
     * @return Um cliente configurado do Secrets Manager.
     */
    private static SecretsManagerClient createSecretsManagerClient(String region) {
        return SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();
    }

    /**
     * Verifica se o programa está em modo de desenvolvimento local.
     *
     * @return true se estiver em modo local, false caso contrário.
     */
    private static boolean isLocalDevelopment() {
        return System.getProperty(SKIP_AWS_PROPERTY) != null ||
                "true".equalsIgnoreCase(System.getProperty(SKIP_AWS_PROPERTY)) ||
                System.getenv(SKIP_AWS_ENV) != null ||
                System.getenv(LOCAL_DEV_ENV) != null;
    }
}