package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Books;
import br.com.erudio.data.vo.BooksVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BooksRepository;

@Service
public class BooksServices {

	@Autowired
	private BooksRepository repository;
	
	public BooksVO create(BooksVO book) {
		var entity = DozerConverter.parseObject(book, Books.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BooksVO.class);

		return vo;
	}
	
	public List<BooksVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BooksVO.class);

	}

	public BooksVO findById(Long id) {
		var vo = DozerConverter.parseObject(
				repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado")),
				BooksVO.class);
		return vo;
	}

	public BooksVO update(BooksVO book) {
		
		Books entity = repository.findById(book.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado"));
		entity = Books.builder()
				.author(book.getAuthor())
				.launchDate(book.getLaunchDate())
				.price(book.getPrice())
				.title(book.getTitle())
				.build();

		var vo = DozerConverter.parseObject(repository.save(entity), BooksVO.class);
		return vo;

	}

	public void delete(Long id) {
		Books entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro nao encontrado"));
		repository.delete(entity);
	}
}
