package com.uet.esxi_api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.uet.esxi_api.dto.NewVM;

@RestController
@RequestMapping("/api")
public class VMController {
	@PostMapping("/create_VM")
	public ResponseEntity<Object> createVM(@RequestBody NewVM newVM) {
//		final String name = newVM.getName();
//		final String os = newVM.getOs();
//		final int numCpu = newVM.getNumCpu();
//		final int ramGB = newVM.getRamGB();

		return null;
	}

	@GetMapping("/test_pws")
	public ResponseEntity<Object> testAPI() {
		PowerShell powerShell = PowerShell.openSession();
	    String script = "E:/Virtual Machine/test.ps1";
	    PowerShellResponse response;
	    response =  powerShell.executeScript(script);
	    System.out.println(response.getCommandOutput().length());
	    return null;
	}
}
