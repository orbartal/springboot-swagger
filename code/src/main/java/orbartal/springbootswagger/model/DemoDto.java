package orbartal.springbootswagger.model;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

// Data object for request, response and internal usage.
// In a real system its advice to used separate domain for each.
@Schema(accessMode = Schema.AccessMode.READ_ONLY)
public class DemoDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	private String key;

	private String value;

	public DemoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "KeyValue [id=" + id + ", key=" + key + ", value=" + value + "]";
	}

}
