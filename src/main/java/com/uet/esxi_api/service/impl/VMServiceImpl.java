package com.uet.esxi_api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;
import com.uet.esxi_api.dto.vm.NewVM;
import com.uet.esxi_api.dto.vm.UpdateCpuRam;
import com.uet.esxi_api.dto.vm.UpdateHardDisk;
import com.uet.esxi_api.entity.OSName;
import com.uet.esxi_api.entity.VM;
import com.uet.esxi_api.entity.VMState;
import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.exception.vm.CannotSuspendVMException;
import com.uet.esxi_api.exception.vm.CannotUpdateStorageException;
import com.uet.esxi_api.exception.vm.CannotUpdateVMException;
import com.uet.esxi_api.exception.vm.FailToCreateVMException;
import com.uet.esxi_api.exception.vm.FailToDeleteVMException;
import com.uet.esxi_api.exception.vm.FailToStartVMException;
import com.uet.esxi_api.exception.vm.FailToStopVMException;
import com.uet.esxi_api.exception.vm.FailToSuspendVMException;
import com.uet.esxi_api.exception.vm.FailToUpdateVMException;
import com.uet.esxi_api.exception.vm.InsufficientConfigurationParametersException;
import com.uet.esxi_api.exception.vm.InvalidOSNameException;
import com.uet.esxi_api.exception.vm.NotFoundVMException;
import com.uet.esxi_api.exception.vm.NotFoundVMStateException;
import com.uet.esxi_api.exception.vm.VMAlreadyExistsException;
import com.uet.esxi_api.exception.vm.VMAlreadyInStateException;
import com.uet.esxi_api.repository.VMRepository;
import com.uet.esxi_api.service.VMService;

@Service
public class VMServiceImpl implements VMService {
	@Value("${esxi_server.ip}")
	private String serverIp;
	@Value("${esxi_server.username}")
	private String serverUsername;
	@Value("${esxi_server.password}")
	private String serverPassword;
	@Autowired
	private VMRepository vmRepository;

	@Override
	public VM save(VM vm) {
		return vmRepository.save(vm);
	}

	@Override
	public VM createVM(NewVM newVM) {
		final String name = newVM.getName().trim();
		VM check = vmRepository.findByName(name).orElse(null);
		if (check != null) {
			throw new VMAlreadyExistsException("VM with name: " + name + " already exists");
		}
		final String os = newVM.getOs().trim();
		final Integer numCPU = newVM.getNumCPU();
		final Integer ramGB = newVM.getRamGB();
		final Integer storage = newVM.getStorage();
		final String publicKey = "\"" + newVM.getPublicKey() + "\"";

		if (!(os.equalsIgnoreCase(OSName.OS_UBUNTU) || os.equalsIgnoreCase(OSName.OS_WINDOW))) {
			throw new InvalidOSNameException("OS name " + os.toUpperCase() + " is invalid");
		}
		if (os.equalsIgnoreCase(OSName.OS_UBUNTU)) {
			if (numCPU < 1 || ramGB < 1 || storage < 4) {
				throw new InsufficientConfigurationParametersException("Configuration parameters are not enough");
			}
		}
		if (os.equalsIgnoreCase(OSName.OS_WINDOW)) {
			if (numCPU < 1 || ramGB < 2 || storage < 32) {
				throw new InsufficientConfigurationParametersException("Configuration parameters are not enough");
			}
		}
		WebUser user = (WebUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		VM vm = new VM();
		vm.setName(name);
		vm.setOs(os.toUpperCase());
		vm.setNumCPU(numCPU);
		vm.setRamGB(ramGB);
		vm.setStorage(storage);
		vm.setUser(user);

		String createVMCmd = String.format(
				"PowerShell -File \"src/main/resources/create_VM.ps1\" %s %s %s %s %s %d %d %d %s", serverIp,
				serverUsername, serverPassword, name, os.toUpperCase(), numCPU, ramGB, storage, publicKey);
		try (PowerShell psSession = PowerShell.open()) {
			String ip = psSession.executeCommands(createVMCmd);
			vm.setState(VMState.STATE_POWERED_ON);
			vm.setIp(ip.replace("\n", ""));
			vmRepository.save(vm);
			return vm;
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToCreateVMException("Fail to create VM");
		}
	}

	@Override
	public void deleteVM(String name) {
		VM vm = vmRepository.findByName(name.trim()).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		String deleteVMCmd = String.format("PowerShell -File \"src/main/resources/delete_VM.ps1\" %s %s %s %s",
				serverIp, serverUsername, serverPassword, name);
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(deleteVMCmd);
			vmRepository.delete(vm);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToDeleteVMException("Fail to delete VM " + name);
		}
	}

	@Override
	public List<VM> getVMs() {
		WebUser user = (WebUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VM> vms = new ArrayList<>();
		vmRepository.getVMs(user.getId()).forEach(vm -> {
			if (vm.getState().equals(VMState.STATE_SUSPENDED)) {
				vm.setIp(null);
				vms.add(vm);
			} else {
				vms.add(vm);
			}
		});
		return vms;
	}

	@Override
	public VM getByName(String name) {
		WebUser user = (WebUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vmRepository.getVMs(user.getId()).stream().filter(vm -> vm.getName().equalsIgnoreCase(name.trim()))
				.collect(Collectors.toList()).get(0);
	}

	@Override
	public VM changeState(String state, String name) {
		if (state.equalsIgnoreCase(VMState.STATE_POWERED_ON)) {
			return startVM(name);
		} else if (state.equalsIgnoreCase(VMState.STATE_SUSPENDED)) {
			return suspendVM(name);
		} else if (state.equalsIgnoreCase(VMState.STATE_POWERED_OFF)) {
			return stopVM(name);
		} else {
			throw new NotFoundVMStateException("Not found VM state " + state);
		}
	}
	
	private VM startVM(String name) {
		VM vm = vmRepository.findByName(name).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		if (vm.getState().equals(VMState.STATE_POWERED_ON)) {
			throw new VMAlreadyInStateException("VM " + name + " already in power on state");
		}
		String startVMCmd = String.format(
				"PowerShell -File \"src/main/resources/start_VM.ps1\" %s %s %s %s", 
				serverIp,
				serverUsername, 
				serverPassword, 
				name);
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(startVMCmd);
			if (vm.getState().equals(VMState.STATE_POWERED_OFF)) {
				String getIpVMCmd = String.format(
						"PowerShell -File \"src/main/resources/get_ip_VM.ps1\" %s %s %s %s",
						serverIp, 
						serverUsername, 
						serverPassword, 
						name);
				String ip = psSession.executeCommands(getIpVMCmd).replace("\n", "");
				vm.setIp(ip);
				vm.setState(VMState.STATE_POWERED_ON);
				vmRepository.save(vm);
			}
			vm.setState(VMState.STATE_POWERED_ON);
			return vmRepository.save(vm);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToStartVMException("Fail to power on VM " + name);
		}
	}
	
	private VM suspendVM(String name) {
		VM vm = vmRepository.findByName(name).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		if (vm.getState().equals(VMState.STATE_SUSPENDED)) {
			throw new VMAlreadyInStateException("VM " + name + " already in suspended state");
		}
		if (vm.getState().equals(VMState.STATE_POWERED_OFF)) {
			throw new CannotSuspendVMException("Unable to go to suspend state while virtual machine is shutting down");
		}
		String suspendVMCmd = String.format(
				"PowerShell -File \"src/main/resources/suspend_VM.ps1\" %s %s %s %s",
				serverIp, 
				serverUsername, 
				serverPassword, 
				name);
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(suspendVMCmd);
			vm.setState(VMState.STATE_SUSPENDED);
			VM changedVM = vmRepository.save(vm);
			changedVM.setIp(null);
			return changedVM;
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToSuspendVMException("Fail to suspend VM " + name);
		}
	}
	
	private VM stopVM(String name) {
		VM vm = vmRepository.findByName(name).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		if (vm.getState().equals(VMState.STATE_POWERED_OFF)) {
			throw new VMAlreadyInStateException("VM " + name + " already in power off state");
		}
		String stopVMCmd = String.format(
				"PowerShell -File \"src/main/resources/stop_VM.ps1\" %s %s %s %s", 
				serverIp,
				serverUsername, 
				serverPassword, 
				name);
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(stopVMCmd);
			vm.setState(VMState.STATE_POWERED_OFF);
			vm.setIp(null);
			return vmRepository.save(vm);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToStopVMException("Fail to power off VM " + name);
		}
	}

	@Override
	public VM updateHarDisk(UpdateHardDisk updateHardDisk, String name) {
		VM vm = vmRepository.findByName(name).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		if (!vm.getState().equals(VMState.STATE_POWERED_OFF)) {
			throw new CannotUpdateVMException("you can only update the VM when it's turned off");
		}
		if (vm.getStorage() >= updateHardDisk.getStorage()) {
			throw new CannotUpdateStorageException("You can only increase the hard drive capacity of the virtual machine");
		}
		String updateHardDiskVMCmd = String.format(
				"PowerShell -File \"src/main/resources/update_hard_disk_VM.ps1\" %s %s %s %s %d",
				serverIp, 
				serverUsername, 
				serverPassword, 
				name, 
				updateHardDisk.getStorage());
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(updateHardDiskVMCmd);
			vm.setStorage(updateHardDisk.getStorage());
			return vmRepository.save(vm);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToUpdateVMException("Fail to update hard disk VM " + name);
		}
	}

	@Override
	public VM updateCpuRam(UpdateCpuRam updateCpuRam, String name) {
		VM vm = vmRepository.findByName(name).orElse(null);
		if (vm == null) {
			throw new NotFoundVMException("Not found VM with name: " + name);
		}
		if (vm.getNumCPU() == updateCpuRam.getNumCPU() && vm.getRamGB() == updateCpuRam.getRamGB()) {
			return vm;
		}
		if (vm.getOs().equals(OSName.OS_UBUNTU)) {
			if (updateCpuRam.getNumCPU() < 1 || updateCpuRam.getRamGB() < 1) {
				throw new InsufficientConfigurationParametersException("Configuration parameters are not enough");
			}
		}
		if (vm.getOs().equals(OSName.OS_WINDOW)) {
			if (updateCpuRam.getNumCPU() < 1 || updateCpuRam.getRamGB() < 2) {
				throw new InsufficientConfigurationParametersException("Configuration parameters are not enough");
			}
		}
		if (!vm.getState().equals(VMState.STATE_POWERED_OFF)) {
			throw new CannotUpdateVMException("you can only update the VM when it's turned off");
		}
		String updateCpuRamVMCmd = String.format(
				"PowerShell -File \"src/main/resources/update_CPU_RAM_VM.ps1\" %s %s %s %s %d %d",
				serverIp, 
				serverUsername, 
				serverPassword, 
				name, 
				updateCpuRam.getNumCPU(), 
				updateCpuRam.getRamGB());
		try (PowerShell psSession = PowerShell.open()) {
			psSession.executeCommands(updateCpuRamVMCmd);
			vm.setNumCPU(updateCpuRam.getNumCPU());
			vm.setRamGB(updateCpuRam.getRamGB());
			return vmRepository.save(vm);
		} catch (IOException | PowerShellExecutionException ex) {
			ex.printStackTrace();
			throw new FailToUpdateVMException("Fail to update CPU or RAM VM " + name);
		}
	}
}
