package br.com.erudio.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@Api(value = "Person EndPoint", description = "Description for person", tags = { "PersonEndPoint" })
//@CrossOrigin()
@Api(tags = "PersonEndPoint")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonServices services;

	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

	/*
	 * //@CrossOrigin() - Exemplo de Cross Dominio.
	 * 
	 * @ApiOperation(value = "Find all people recorded")
	 * 
	 * @GetMapping(produces = { "application/json", "application/xml",
	 * "application/x-yaml" }) public List<PersonVO> findAll() { List<PersonVO>
	 * persons = services.findAll(); persons.stream().forEach(p -> { Link link =
	 * linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel();
	 * p.add(link); });
	 * 
	 * return persons; }
	 */
	
	

	@ApiOperation(value = "Find all people recorded")
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<RepresentationModel<?>> findPersonByName(
			@PathVariable("firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

		Page<PersonVO> persons = services.findPersonByName(firstName, pageable);
		persons.stream().forEach(p -> {
			Link link = linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel();
			p.add(link);
		});

		RepresentationModel<?> resources = assembler.toModel(persons);

		return new ResponseEntity<RepresentationModel<?>>(resources, HttpStatus.OK);
	}
	
	
	

	@ApiOperation(value = "Find all people recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<RepresentationModel<?>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

		Page<PersonVO> persons = services.findAll(pageable);
		persons.stream().forEach(p -> {
			Link link = linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel();
			p.add(link);
		});

		RepresentationModel<?> resources = assembler.toModel(persons);

		return new ResponseEntity<RepresentationModel<?>>(resources, HttpStatus.OK);
	}

	@ApiOperation(value = "Find by ID person")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(@PathVariable("id") Long id) {

		PersonVO person = services.findById(id);
		Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		person.add(link);

		return person;
	}

	@ApiOperation(value = "Create person in DB")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		var p = services.create(person);
		Link link = linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel();
		p.add(link);
		return p;
	}

	/*
	 * @PostMapping("/v2") public PersonVOV2 createV2(@RequestBody PersonVOV2
	 * person) { return services.createV2(person); }
	 */
	@ApiOperation(value = "Update person in DB")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@RequestBody PersonVO person) {
		var p = services.update(person);
		Link link = linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel();
		p.add(link);
		return p;
	}

	@ApiOperation(value = "Update field enable to disable by ID person")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO disable(@PathVariable("id") Long id) {

		PersonVO person = services.disablePerson(id);
		Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		person.add(link);

		return person;
	}

	@ApiOperation(value = "Delete person in DB")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}
}
