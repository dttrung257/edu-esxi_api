package com.uet.esxi_api.dto.vm;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewVM {
	@NotBlank
	private String name;
	
	@NotBlank
	private String os;
	
	@NotNull
	@Min(value = 1, message = "CPU field must greater than or equal to 1")
	private Integer numCPU;
	
	@NotNull
	@Min(value = 1, message = "RAM field must greater than or equal to 1")
	private Integer ramGB;
	
	@NotNull
	@Min(value = 4, message = "Storage field must greater than or equal to 4")
	private Integer storage;
}
