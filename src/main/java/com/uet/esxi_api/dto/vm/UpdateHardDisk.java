package com.uet.esxi_api.dto.vm;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHardDisk {
	@NotNull(message = "storage field is mandatory")
	@Min(value = 1)
	private Integer storage;
}
