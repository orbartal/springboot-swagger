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
public class HttpClientRestIntegrationTest {

	private static final String KEY_1 = "ka1";
	private static final String VALUE_1 = "va1";
	private static final String VALUE_2 = "va2";

	@LocalServerPort
	private int port;

	private final Gson gson = new Gson();

	@Order(1)
	@Test
	public void testGetAllDemoEmptyBeforeCreate() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertEquals("[]", response.body());
	}

	@Order(2)
	@Test
    public void testCreateDemo() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_1, VALUE_1);
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
	@Order(3)
	@Test
    public void testGetAllDemosAfterCreate() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		List output = gson.fromJson(response.body(), List.class);

		Assertions.assertNotNull(output);
		Assertions.assertEquals(1, output.size());
		//Assertions.assertEquals(Map.class, output.get(0).getClass());
		Map FirstResult =  (Map) output.get(0);
		Assertions.assertEquals(KEY_1, FirstResult.get("key"));
		Assertions.assertEquals(VALUE_1, FirstResult.get("value"));
    }

	@Order(4)
	@Test
    public void testUpdateDemo() throws Exception {
		String url = buildUrlDemo();
		DemoDto input = buildDemoDto(KEY_1, VALUE_2);
		String requestBody = gson.toJson(input);

        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    }

	@SuppressWarnings("rawtypes")
	@Order(5)
	@Test
    public void testGetAllDemosAfterUpdate() throws Exception {
		String url = buildUrlDemo();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		List output = gson.fromJson(response.body(), List.class);

		Assertions.assertNotNull(output);
		Assertions.assertEquals(1, output.size());
		//Assertions.assertEquals(Map.class, output.get(0).getClass());
		Map FirstResult =  (Map) output.get(0);
		Assertions.assertEquals(KEY_1, FirstResult.get("key"));
		Assertions.assertEquals(VALUE_2, FirstResult.get("value"));
    }


	@Order(6)
	@Test
	 public void testDeleteDemo() throws Exception {
		String url = buildUrlDemo() + "/" + KEY_1;
        HttpRequest request = HttpRequest.newBuilder()
        	.uri(new URI(url))
            .headers("Content-Type", "application/json")
            .DELETE()
            .build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, handler);

		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    }

	@Order(7)
	@Test
	public void testGetAllDemoEmptyAfterDelete() throws Exception {
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