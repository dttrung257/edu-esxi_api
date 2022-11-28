package com.uet.esxi_api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uet.esxi_api.dto.vm.NewVM;
import com.uet.esxi_api.dto.vm.UpdateCpuRam;
import com.uet.esxi_api.dto.vm.UpdateHardDisk;
import com.uet.esxi_api.dto.vm.UpdateState;
import com.uet.esxi_api.entity.VM;
import com.uet.esxi_api.service.VMService;

@RestController
@RequestMapping("/api")
@Validated
public class VMController {
	@Autowired
	private VMService vmService;

	@PostMapping("/VMs")
	public ResponseEntity<VM> createVM(@Valid @RequestBody NewVM newVM) {
		return ResponseEntity.ok(vmService.createVM(newVM));
	}

	@DeleteMapping("/VMs/{name}")
	public ResponseEntity<String> deleteVM(
			@PathVariable(name = "name", required = true) @NotBlank String name) {
		vmService.deleteVM(name.trim());
		return ResponseEntity.ok("Delete VM " + name + " successfully");
	}

	@GetMapping("/VMs")
	public ResponseEntity<List<VM>> getInfoVMs() {
		return ResponseEntity.ok(vmService.getVMs());
	}
	
	@GetMapping("/VMs/{name}")
	public ResponseEntity<VM> getInfoVM(
			@PathVariable("name") @NotBlank(message = "name field is mandatory") String name) {
		return ResponseEntity.ok(vmService.getByName(name.trim()));
	}
	
	@PutMapping("/VMs/{name}/state")
	public ResponseEntity<?> updateState(
			@Valid @RequestBody UpdateState updateState,
			@PathVariable("name") @NotBlank(message = "name field is mandatory") String name) {
		return ResponseEntity.ok(vmService.changeState(updateState.getState().trim(), name.trim()));
	}

	@PutMapping("/VMs/{name}/hard_disk")
	public ResponseEntity<VM> updateHardDisk(
			@Valid @RequestBody UpdateHardDisk updateHardDisk,
			@PathVariable(name = "name", required = true) @NotBlank(message = "name field is mandatory") String name) {
		return ResponseEntity.ok(vmService.updateHarDisk(updateHardDisk, name.trim()));
	}
	
	@PutMapping("/VMs/{name}/CPU_RAM")
	public ResponseEntity<VM> updateRamCpu(
			@Valid @RequestBody UpdateCpuRam updateCpuRam,
			@PathVariable(name = "name", required = true) @NotBlank(message = "name field is mandatory") String name) {
		return ResponseEntity.ok(vmService.updateCpuRam(updateCpuRam, name.trim()));
	}
}
