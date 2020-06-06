package br.com.erudio.data.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UploadFileResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
	

}
