package com.uet.esxi_api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;
import com.uet.esxi_api.dto.NewVM;

@RestController
@RequestMapping("/api")
public class VMController {
	@PostMapping("/VM/create_VM")
	public ResponseEntity<Object> createVM(@RequestBody NewVM newVM) {
		final String name = newVM.getName();
		final String os = newVM.getOs();
		final Integer numCpu = newVM.getNumCpu();
		final Integer ramGB = newVM.getRamGB();
		final Integer storage = newVM.getStorage();

		String createVMCmd = String.format("PowerShell -File \"src/main/resources/newVM.ps1\" %s %s %d %d %d", name,
				os.toUpperCase(), numCpu, ramGB, storage);
		String ip = null;
		try (PowerShell psSession = PowerShell.open()) {
			ip = psSession.executeCommands(createVMCmd);
			return ResponseEntity.status(HttpStatus.CREATED).body(ip);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Fail");
	}
	
	@GetMapping("/VM/delete_VM/{name}")
	public ResponseEntity<Object> deleteVM() {

//		String createVMCmd = String.format("PowerShell -File \"src/main/resources/newVM.ps1\" %s %s %d %d %d", name,
//				os.toUpperCase(), numCpu, ramGB, storage);
//		String ip = null;
//		try (PowerShell psSession = PowerShell.open()) {
//			ip = psSession.executeCommands(createVMCmd);
//			return ResponseEntity.status(HttpStatus.CREATED).body(ip);
//		} catch (IOException | PowerShellExecutionException ex) {
//			ex.printStackTrace();
//		}
//		return ResponseEntity.status(HttpStatus.CONFLICT).body("Fail");
		return null;
	}

	@GetMapping("/test_pws/{name}")
	public ResponseEntity<Object> testAPI(@PathVariable("name") String name) {
		// String param = "hello word";
		String output = null;
		try (PowerShell psSession = PowerShell.open()) {
			// output = psSession.executeCommands(String.format("PowerShell -File
			// \"src/main/resources/test.ps1\" %s", name));
			System.out.println(psSession
					.executeCommands(String.format("PowerShell -File \"src/main/resources/get_ip.ps1\" %s", name)));
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/test")
	public ResponseEntity<Object> test() {
		return ResponseEntity.ok("Abc");
	}
}
