package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo2.PersonVOV2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonConverter converter;

	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);

		return vo;
	}

	public PersonVOV2 createV2(PersonVOV2 person) {
		var entity = converter.convertVOToEntity(person);
		var vo = converter.convertEntityToVO(repository.save(entity));

		return vo;
	}
	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);

	}

	public PersonVO findById(Long id) {
		var vo = DozerConverter.parseObject(
				repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado")),
				PersonVO.class);
		return vo;
	}

	public PersonVO update(PersonVO person) {
		
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado"));
		entity = Person.builder().firstName(person.getFirstName()).lastName(person.getLastName())
				.address(person.getAddress()).gender(person.getGender()).build();

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;

	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado"));
		repository.delete(entity);
	}

}
