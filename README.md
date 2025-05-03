![Programação-Challenge - Conversor de Moedas](https://github.com/user-attachments/assets/c18b5746-0642-4acd-9faf-298cc5ba3d89)

# 💱 Conversor de Moedas - Challenge

Este é um projeto de um **Conversor de Moedas** desenvolvido em Java. O programa permite realizar conversões de moedas utilizando a API [ExchangeRate-API](https://www.exchangerate-api.com/). Ele oferece uma interface interativa no console para que o usuário possa selecionar opções de conversão e visualizar os resultados.
Este projeto faz parte de um desafio do programa ONE ORACLE G8 - CHALLENGE | CONVERSOR DE MOEDAS, sendo como projeto final do curso: "Java e Orientação a Objetos G8 - ONE"
---

## ✨ Funcionalidades

- 🌎 **Converter de Dólar Americano (USD) para outra moeda**:
  - O usuário insere o código da moeda de destino e o valor em USD.
  - O programa exibe o valor convertido.

- 💵 **Converter de outra moeda para Dólar Americano (USD)**:
  - O usuário insere o código da moeda de origem e o valor na moeda.
  - O programa exibe o valor convertido para USD.

- 🇧🇷 **Converter de Dólar Americano (USD) para Real Brasileiro (BRL)**:
  - O usuário insere o valor em USD.
  - O programa exibe o valor convertido para BRL.

- 📋 **Menu interativo**:
  - O programa exibe um menu com opções para o usuário escolher a conversão desejada ou sair do programa.

---

## 🛠️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter:

1. ☕ **Java 17 ou superior** instalado.
2. 🔑 Uma chave de API válida da [ExchangeRate-API](https://www.exchangerate-api.com/).

---

## Configuração

### 1. Configurar a API Key
O projeto requer uma API Key válida da [ExchangeRate-API](https://www.exchangerate-api.com/). Você pode configurar a API Key de três maneiras:

#### **Opção 1: Variável de Ambiente**
Defina a variável de ambiente `CURRENCY_CONVERTER_API_KEY` com sua API Key:
- **No Windows (PowerShell):**
  ```powershell
  $env:CURRENCY_CONVERTER_API_KEY = "SUA_API_KEY"
  ```
- **No Linux/Mac:**
  ```bash
  export CURRENCY_CONVERTER_API_KEY="SUA_API_KEY"
  ```

#### **Opção 2: Passar a API Key como Argumento**
Você pode passar a API Key diretamente ao executar o programa:
```bash
mvn exec:java -Dexec.mainClass=Principal -Dexec.args="SUA_API_KEY"
```

#### **Opção 3: AWS Secrets Manager**
Armazene a API Key no AWS Secrets Manager e configure o projeto para recuperá-la automaticamente. Siga os passos abaixo:
1. Crie um segredo no AWS Secrets Manager com o nome `CurrencyConverterApiKey`.
2. Adicione a API Key como valor do segredo.
3. Certifique-se de que as credenciais da AWS estão configuradas no ambiente:
   - **No Windows (PowerShell):**
     ```powershell
     $env:AWS_ACCESS_KEY_ID = "SEU_ACCESS_KEY_ID"
     $env:AWS_SECRET_ACCESS_KEY = "SEU_SECRET_ACCESS_KEY"
     ```
   - **No Linux/Mac:**
     ```bash
     export AWS_ACCESS_KEY_ID="SEU_ACCESS_KEY_ID"
     export AWS_SECRET_ACCESS_KEY="SEU_SECRET_ACCESS_KEY"
     ```

### 2. Executar o Projeto
Após configurar a API Key, você pode executar o projeto com o Maven:
```bash
mvn compile exec:java -Dexec.mainClass=Principal
```
-----

## 🚀 Como Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/lirasusejdev/conversor-de-moedas.git
   cd conversor-de-moedas
   ```

2. Compile o projeto:
   ```bash
   javac -d bin -sourcepath src src/Principal.java
   ```

3. Execute o programa:
   ```bash
   java -cp bin Principal
   ```

4. Siga as instruções exibidas no console para realizar as conversões.

---

## 📂 Estrutura do Projeto

```
src/
├── api/
│   └── ConsultaApi.java          # Classe responsável por consultar a API
├── model/
│   └── Moedas.java               # Classe que representa os dados retornados pela API
├── service/
│   └── ConversorDeMoedas.java    # Classe que implementa a lógica do conversor
└── Principal.java                # Classe principal que inicia o programa
```

---

## 🖥️ Exemplo de Uso

### Menu Interativo
```
=== Conversor de Moedas ===
1. Converter de Dólar Americano (USD) para outra moeda
2. Converter de outra moeda para Dólar Americano (USD)
3. Converter de Dólar Americano (USD) para Real Brasileiro (BRL)
4. Sair
Escolha uma opção: 3
Digite o valor em USD: 100
Valor convertido: 567,18 BRL
```

---

## 🛠️ Tecnologias Utilizadas

- ☕ **Java 17**: Linguagem de programação utilizada.
- 🌐 **ExchangeRate-API**: API para obter as taxas de câmbio.
- 📦 **Gson**: Biblioteca para deserialização de JSON.
-  **Maven** para gerenciar dependências.
- **AWS SDK** para integração com o AWS Secrets Manager.

---

## 🔧 Personalização

Se desejar usar sua própria chave de API, substitua o valor da variável `apiKey` no arquivo `ConversorDeMoedas.java`:
```java
String apiKey = "SUA_API_KEY"; // Substitua pela sua chave de API válida
```

---

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

---

## 📜 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

## 👤 Autor

Desenvolvido por [Lirasusej](https://github.com/lirasusejdev).



