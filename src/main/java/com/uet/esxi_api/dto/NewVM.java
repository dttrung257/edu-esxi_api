package com.uet.esxi_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewVM {
	private String name;
	private String os;
	private int numCpu;
	private int ramGB;
	private String storage;
}
