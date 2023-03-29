package orbartal.springbootswagger.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Value("${orbartal.openapi.url}")
	private String url;

	@Bean
	public OpenAPI myOpenAPI() {
		Info info = getInfo();
		Server devServer = getServer();
		return new OpenAPI().info(info).servers(List.of(devServer));
	}

	private Info getInfo() {
		return new Info()
				.title("Tutorial Springboot Swagger Rest API")
				.version("1.0")
				.contact(getContact())
				.description("This is an example Swagger Rest API.")
				.license(getLicense());
	}

	private License getLicense() {
		return new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
	}

	private Server getServer() {
		Server devServer = new Server();
		devServer.setUrl(url);
		devServer.setDescription("Server URL");
		return devServer;
	}

	private Contact getContact() {
		Contact contact = new Contact();
		contact.setEmail("orbartal@gmail.com");
		contact.setName("Or Bartal");
		return contact;
	}
}
