package br.com.erudio.data.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class BooksVO extends RepresentationModel<BooksVO> implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;
	private String author;
	private Date launchDate;
	private BigDecimal  price;
	private String title;

}
