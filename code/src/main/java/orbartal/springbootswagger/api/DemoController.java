package orbartal.springbootswagger.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import orbartal.springbootswagger.app.DemoApp;
import orbartal.springbootswagger.model.DemoDto;

@SuppressWarnings("unchecked")
@Tag(name = "demo", description = "Example Swagger rest crud APIs")
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DemoController {

	@Autowired
	private DemoApp demoApp;

	@Operation(summary = "Retrieve all demos", tags = { "demo"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = DemoDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "There are no demos", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/demo")
	public ResponseEntity<List<DemoDto>> getAllDemoDtos() {
		return demoApp.readAll();
	}

	@Operation(summary = "Retrieve a demo by key", 
			description = "Get a demo object by specifying its key. The response is DemoDto object with id and value.", 
			tags = { "demo"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = DemoDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/demo/{key}")
	public ResponseEntity<DemoDto> getDemoDtoById(@PathVariable("key") String key) {
		return demoApp.readByKey(key);
	}

	@Operation(summary = "Create a new demo", tags = { "demo"})
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(implementation = DemoDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/demo")
	public ResponseEntity<DemoDto> createDemo(@RequestBody DemoDto input) {
		return demoApp.create(input);
	}

	@Operation(summary = "Update a demo by key", tags = { "demo"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = DemoDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/demo")
	public ResponseEntity<DemoDto> updateDemo(@RequestBody DemoDto demo) {
		return demoApp.update(demo);
	}

	@Operation(summary = "Delete a demo by key", tags = { "demo"})
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@DeleteMapping("/demo/{key}")
	public ResponseEntity<HttpStatus> deleteDemo(@PathVariable("key") String key) {
		return demoApp.deleteByKey(key);
	}

}
