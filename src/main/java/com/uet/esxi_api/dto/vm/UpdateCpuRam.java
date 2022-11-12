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
public class UpdateCpuRam {
	@NotBlank(message = "Name field cannot be blank")
	private String name;
	
	@NotNull(message = "Cpu field cannot be null")
	@Min(value = 1)
	private Integer numCPU;
	
	@NotNull(message = "Ram field cannot be null")
	@Min(value = 1)
	private Integer ramGB;
}
