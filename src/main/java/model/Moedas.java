package model;

import java.util.Map;

/**
 * Classe que representa os dados retornados pela API de taxas de câmbio.
 */
public class Moedas {
    private final String result;
    private final String documentation;
    private final String terms_of_use;
    private final long time_last_update_unix;
    private final String time_last_update_utc;
    private final long time_next_update_unix;
    private final String time_next_update_utc;
    private final String base_code;
    private final Map<String, Double> conversion_rates;

    /**
     * Construtor da classe Moedas.
     *
     * @param result               Resultado da consulta (ex.: "success").
     * @param documentation        URL da documentação da API.
     * @param terms_of_use         URL dos termos de uso da API.
     * @param time_last_update_unix Timestamp da última atualização (UNIX).
     * @param time_last_update_utc Data e hora da última atualização (UTC).
     * @param time_next_update_unix Timestamp da próxima atualização (UNIX).
     * @param time_next_update_utc Data e hora da próxima atualização (UTC).
     * @param base_code            Código da moeda base (ex.: "USD").
     * @param conversion_rates     Mapa com as taxas de conversão.
     */
    public Moedas(String result, String documentation, String terms_of_use,
                  long time_last_update_unix, String time_last_update_utc,
                  long time_next_update_unix, String time_next_update_utc,
                  String base_code, Map<String, Double> conversion_rates) {
        this.result = result;
        this.documentation = documentation;
        this.terms_of_use = terms_of_use;
        this.time_last_update_unix = time_last_update_unix;
        this.time_last_update_utc = time_last_update_utc;
        this.time_next_update_unix = time_next_update_unix;
        this.time_next_update_utc = time_next_update_utc;
        this.base_code = base_code;
        this.conversion_rates = conversion_rates;
    }

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTerms_of_use() {
        return terms_of_use;
    }

    public long getTime_last_update_unix() {
        return time_last_update_unix;
    }

    public String getTime_last_update_utc() {
        return time_last_update_utc;
    }

    public long getTime_next_update_unix() {
        return time_next_update_unix;
    }

    public String getTime_next_update_utc() {
        return time_next_update_utc;
    }

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    @Override
    public String toString() {
        return "Moedas{" +
                "result='" + result + '\'' +
                ", base_code='" + base_code + '\'' +
                ", conversion_rates=" + conversion_rates +
                '}';
    }
}