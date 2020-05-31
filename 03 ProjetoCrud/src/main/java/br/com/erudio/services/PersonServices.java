package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {

	private final AtomicLong atomic = new AtomicLong();

	public Person findById(String id) {
		return mockPerson(Integer.parseInt(id));
	}

	public List<Person> findAll() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 8; i++) {
			persons.add(mockPerson(i));
		}
		return persons;

	}

	public Person create(Person person) {
		person.setId(atomic.incrementAndGet());
		return person;
	}
	
	public Person update(Person person) {
		return person;
	}
	
	public void delete(String id) {
		
	}

	private Person mockPerson(int i) {

		Person p = Person.builder().id(atomic.incrementAndGet()).firstName("Primeiro nome " + i)
				.lastName("Último nome  " + i).address("Rua XXXX - Osasco - SP nro " + i)
				.gender(i % 2 == 0 ? "Male" : "Feme").build();
		return p;
	}
}
