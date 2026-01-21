package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


 // Classe base para centralizar configurações comuns dos testes de API, 
 //evitando repetição de código.
 
public class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
