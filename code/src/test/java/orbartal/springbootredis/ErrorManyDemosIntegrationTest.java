package orbartal.springbootredis;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.gson.Gson;

import orbartal.springbootswagger.SpringbootSwaggerMain;
import orbartal.springbootswagger.model.DemoDto;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringbootSwaggerMain.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorManyDemosIntegrationTest {

	private static final String KEY_A1 = "ka1";
	private static final String VALUE_A1 = "va1";
	private static final String KEY_B1 = "kb1";
	private static final String VALUE_B1 = "vb1";
	private static final String KEY_C1 = "kc1";
	private static final String VALUE_C1 = "vc1";
	private static final String KEY_D1 = "kd1";
	private static final String VALUE_D1 = "vd1";

	@LocalServerPort
	private int port;

	private final Gson gson = new Gson();
	
	//Part 1: clean and validate initial state before the test stare

	@Order(0)
	@Test
	public void testDeleteAllStart() throws Exception {
		String url = buildUrlDemo();
        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .DELETE()
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
	}

	@Order(1)
	@Test
	public void testGetAllEmptyStart() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertEquals("[]", response.body());
	}
	
	//Part 2: Prepare state and validate without error

	@Order(2)
	@Test
    public void testCreateDemoA() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_A1, VALUE_A1);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode());
    }
	
	@Order(3)
	@Test
    public void testCreateDemoB() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_B1, VALUE_B1);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode());
    }
	
	@Order(4)
	@Test
    public void testCreateDemoC() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_C1, VALUE_C1);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode());
    }

	@SuppressWarnings("rawtypes")
	@Order(5)
	@Test
    public void testGetAllDemosAfterCreate() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		List output = gson.fromJson(response.body(), List.class);

		Assertions.assertNotNull(output);
		Assertions.assertEquals(3, output.size());

		Map aOut =  (Map) output.get(0);
		Assertions.assertEquals(KEY_A1, aOut.get("key"));
		Assertions.assertEquals(VALUE_A1, aOut.get("value"));
		
		Map bOut =  (Map) output.get(1);
		Assertions.assertEquals(KEY_B1, bOut.get("key"));
		Assertions.assertEquals(VALUE_B1, bOut.get("value"));
		
		Map cOut =  (Map) output.get(2);
		Assertions.assertEquals(KEY_C1, cOut.get("key"));
		Assertions.assertEquals(VALUE_C1, cOut.get("value"));
    }

	//Part 3: Request that should result in error

	@Order(6)
	@Test
    public void testGetDemoError() throws Exception {
		String url = buildUrlDemo() + "/" + KEY_D1;
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.statusCode());
    }

	@Order(7)
	@Test
    public void testReCreateDemoError() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_C1, VALUE_C1);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.statusCode());
    }

	@Order(8)
	@Test
    public void testUpdateDemoDError() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_D1, VALUE_D1);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.statusCode());
    }

	@Order(9)
	@Test
	 public void testDeleteDemoD() throws Exception {
		String url = buildUrlDemo() + "/" + KEY_D1;
        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .DELETE()
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.statusCode());
    }

	@SuppressWarnings("rawtypes")
	@Order(5)
	@Test
    public void testGetAllDemosAfterErrors() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		List output = gson.fromJson(response.body(), List.class);

		Assertions.assertNotNull(output);
		Assertions.assertEquals(3, output.size());

		Map aOut =  (Map) output.get(0);
		Assertions.assertEquals(KEY_A1, aOut.get("key"));
		Assertions.assertEquals(VALUE_A1, aOut.get("value"));
		
		Map bOut =  (Map) output.get(1);
		Assertions.assertEquals(KEY_B1, bOut.get("key"));
		Assertions.assertEquals(VALUE_B1, bOut.get("value"));
		
		Map cOut =  (Map) output.get(2);
		Assertions.assertEquals(KEY_C1, cOut.get("key"));
		Assertions.assertEquals(VALUE_C1, cOut.get("value"));
    }
	
	//Part 4: reset and validate to end the test in a clean state
	
	@Order(11)
	@Test
	public void testDeleteAllDemoEnd() throws Exception {
		String url = buildUrlDemo();
        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .DELETE()
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
	}
	
	@Order(12)
	@Test
	public void testGetAllDemoEmptyEnd() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertEquals("[]", response.body());
	}


	private String buildUrlDemo() {
		return "http://localhost:" + port + "/api/demo";
	}

	private DemoDto buildDemoDto(String key, String value) {
		DemoDto entity = new DemoDto();
		entity.setId(System.currentTimeMillis()); // Mock DB generate id
		entity.setKey(key);
		entity.setValue(value);
		return entity;
	}
}