package orbartal.springbootswagger.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import orbartal.springbootswagger.business.DemoBusiness;
import orbartal.springbootswagger.model.DemoDto;

@SuppressWarnings("rawtypes")
@Component
public class DemoApp {

	@Autowired
	private DemoBusiness business;

	public ResponseEntity readAll() {
		try {
			List<DemoDto> dtos = business.readAll();
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity readByKey(String key) {
		try {
			DemoDto dto = business.readByKey(key).get();
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity create(DemoDto input) {
		try {
			DemoDto dto = business.create(input);
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity update(DemoDto input) {
		try {
			DemoDto dto = business.update(input);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity deleteByKey(String key) {
		try {
			business.deleteByKey(key);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
