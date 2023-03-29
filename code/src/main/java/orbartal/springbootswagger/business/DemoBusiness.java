package orbartal.springbootswagger.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import orbartal.springbootswagger.data.DemoData;
import orbartal.springbootswagger.model.DemoDto;

@Component
public class DemoBusiness {

	@Autowired
	private DemoData data;

	public List<DemoDto> readAll() {
		return data.readAll();
	}

	public Optional<DemoDto> readByKey(String key) {
		return Optional.ofNullable(data.readByKey(key));
	}

	public DemoDto create(DemoDto input) {
		return data.create(input);
	}

	public DemoDto update(DemoDto input) {
		return data.update(input);
	}

	public void deleteByKey(String key) {
		data.deleteByKey(key);
	}

}
