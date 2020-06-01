package br.com.erudio.converter.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo2.PersonVOV2;

@Service
public class PersonConverter {


	public PersonVOV2 convertEntityToVO(Person person) {
		PersonVOV2 vo = PersonVOV2.builder()
				.id(person.getId())
				.address(person.getAddress())
				.birthDay(new Date())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.gender(person.getGender())
				.build();
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 person) {
		Person entity = Person.builder()
				.id(person.getId())
				.address(person.getAddress())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.gender(person.getGender())
				.build();
		return entity;
	}

}
