# Finaya - QA API Test Automation

Projeto de automação de testes de API para o endpoint `/posts` do JSONPlaceholder, demonstrando habilidades em **Java + RestAssured** e **Postman + Newman**.

## Tecnologias Utilizadas

- **Java 11**: Linguagem base.
- **RestAssured**: DSL para testes de API fluentes.
- **JUnit 5**: Framework de execução de testes.
- **Jackson**: Serialização e Deserialização de JSON (POJO).
- **Maven**: Gerenciador de dependências.
- **Postman**: Criação e estruturação da coleção.
- **Newman**: Execução da coleção via linha de comando (CLI) e geração de relatórios.

## Estrutura do Projeto


├── postman             # Collection e Environment exportados
├── reports             # Relatórios gerados pelo Newman
├── src/test
│   ├── java
│   │   ├── base        # Configuração base (URL)
│   │   ├── models      # POJOs (Mapeamento JSON -> Objeto)
│   │   └── tests       # Testes funcionais e de contrato
│   └── resources
│       └── schemas     # Contratos JSON (.json)
├── pom.xml             # Dependências Maven
└── README.md           # Documentação do projeto

## Como Executar
1. Testes Automatizados (Java)

Certifique-se de ter o Maven intalado e configurado.
Execute o comando na raiz do projeto:

mvn clean test

2. Testes de Integração (Postman/Newman)

Certifique-se de ter o Node.js e Newman instalados (npm install -g newman).

# Para rodar os testes e ver o resultado no terminal:

newman run postman/collection.json -e postman/environment.json

# Para rodar gerando relatório HTML e JUnit (XML):

newman run postman/collection.json -e postman/environment.json -r cli,html,junit --reporter-html-export reports/report.html --reporter-junit-export reports/report.xml

O relatório estará disponível em: ./reports/report.html

## Decisões Técnicas

Uso de POJO (Java): Optei por criar a classe PostModel para garantir a integridade dos tipos de dados e facilitar a validação dos campos, simulando um cenário real de aplicação escalável.

Validação de Contrato: Implementada via json-schema-validator para garantir que a estrutura da API não foi alterada.

BaseTest: Criação de uma classe base para centralizar a configuração da BaseURI, evitando repetição de código.

Newman: Incluído para demonstrar capacidade de integração contínua (CI/CD), permitindo que a coleção do Postman seja executada em pipelines sem interface gráfica.


## Cenários de Teste (Modelagem em Gherkin)

Os cenários foram modelados utilizando a sintaxe **Gherkin (BDD)**. 
Este formato facilita a leitura tanto por desenvolvedores quanto por stakeholders, além de servir como base para automação.

### Funcionalidade: Gestão de Postagens da API JSONPlaceholder

**Cenário 01: Listar todas as postagens com sucesso**
  **Dado** que a API JSONPlaceholder está online
  **Quando** eu envio uma requisição GET para o endpoint "/posts"
  **Então** o status code da resposta deve ser 200
  **E** o corpo da resposta deve conter uma lista com 100 postagens
  **E** cada postagem deve conter os campos "userId", "id", "title" e "body"

**Cenário 02: Consultar um post específico por ID (Contrato e Dados)**
  **Dado** que desejo consultar a postagem com ID 1
  **Quando** eu envio uma requisição GET para o endpoint "/posts/1"
  **Então** o status code da resposta deve ser 200
  **E** o campo "id" do retorno deve ser igual a 1
  **E** os campos "title" e "userId" não devem ser nulos
  **E** a estrutura do JSON deve respeitar o contrato definido (Schema)

**Cenário 03: Validar consulta de post inexistente (Cenário Negativo)**
  **Dado** que possuo um ID de postagem que não existe (ex: 99999)
  **Quando** eu envio uma requisição GET para o endpoint "/posts/99999"
  **Então** o status code da resposta deve ser 404 (Not Found)
  **E** o corpo da resposta deve estar vazio ({})

**Cenário 04: Validar tipos de dados do contrato (Schema Check)**
  **Dado** que recebo os dados de uma postagem válida
  **Então** o campo "userId" deve ser do tipo numérico
  **E** o campo "id" deve ser do tipo numérico
  **E** o campo "title" deve ser do tipo texto (string)
  **E** o campo "body" deve ser do tipo texto (string)