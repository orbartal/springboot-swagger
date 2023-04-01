package orbartal.springbootswagger.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import orbartal.springbootswagger.model.DemoDto;

@Component
public class DemoData {

	private Map<String, DemoDto> demo = new HashMap<>();

	public List<DemoDto> readAll() {
		return demo.values().stream().sorted((a,b)->a.getKey().compareTo(b.getKey())).toList();
	}

	public DemoDto readByKey(String key) {
		return demo.get(key);
	}

	public synchronized DemoDto create(DemoDto input) {
		String key = input.getKey();
		DemoDto dto = demo.get(key);
		if (dto != null) {
			throw new RuntimeException("Duplicate key:" + key);
		}
		DemoDto entity = buildDemoDto(key, input.getValue());
		demo.put(input.getKey(), entity);
		return entity;
	}

	public synchronized DemoDto update(DemoDto input) {
		String key = input.getKey();
		DemoDto entity = demo.get(key);
		if (entity == null) {
			throw new RuntimeException("Missing key: " + key);
		}
		entity.setValue(input.getValue());
		return entity;
	}

	private DemoDto buildDemoDto(String key, String value) {
		DemoDto entity = new DemoDto();
		entity.setId(System.currentTimeMillis()); // Mock DB generate id
		entity.setKey(key);
		entity.setValue(value);
		return entity;
	}

	public synchronized void deleteByKey(String key) {
		DemoDto entity = demo.get(key);
		if (entity == null) {
			throw new RuntimeException("Missing key: " + key);
		}
		demo.remove(key);
	}

	public void deleteAll() {
		demo.clear();
	}

}
