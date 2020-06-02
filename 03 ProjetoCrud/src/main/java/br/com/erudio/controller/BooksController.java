package br.com.erudio.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.BooksVO;
import br.com.erudio.services.BooksServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "BookEndPoint")
@RestController
@RequestMapping("/api/book/v1")
public class BooksController {

	@Autowired
	private BooksServices services;

	@ApiOperation(value = "Find all Books in DB")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<BooksVO> findAll() {
		List<BooksVO> books = services.findAll();
		books.stream().forEach(p -> {
			Link link = linkTo(methodOn(BooksController.class).findById(p.getId())).withSelfRel();
			p.add(link);
		});

		return books;
	}

	@ApiOperation(value = "Find by Id Book in DB")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BooksVO findById(@PathVariable("id") Long id) {

		BooksVO book = services.findById(id);
		Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		book.add(link);

		return book;
	}

	@ApiOperation(value = "Create Books in DB")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BooksVO create(@RequestBody BooksVO book) {
		var b = services.create(book);
		Link link = linkTo(methodOn(PersonController.class).findById(b.getId())).withSelfRel();
		b.add(link);
		return b;
	}

	@ApiOperation(value = "Update Books in DB")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BooksVO update(@RequestBody BooksVO book) {
		var b = services.update(book);
		Link link = linkTo(methodOn(PersonController.class).findById(b.getId())).withSelfRel();
		b.add(link);
		return b;
	}

	@ApiOperation(value = "Delete Books in DB")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}

}
