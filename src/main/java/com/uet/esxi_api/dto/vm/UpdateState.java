package com.uet.esxi_api.dto.vm;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateState {
	@NotBlank(message = "state field is mandatory")
	private String state;
}
