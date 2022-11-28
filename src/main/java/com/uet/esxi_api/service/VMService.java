package com.uet.esxi_api.service;

import java.util.List;

import com.uet.esxi_api.dto.vm.NewVM;
import com.uet.esxi_api.dto.vm.UpdateCpuRam;
import com.uet.esxi_api.dto.vm.UpdateHardDisk;
import com.uet.esxi_api.entity.VM;

public interface VMService {
	VM save(VM vm);
	VM createVM(NewVM vm);
	void deleteVM(String name);
	List<VM> getVMs();
	VM getByName(String name);
	VM changeState(String state, String name);
	VM updateHarDisk(UpdateHardDisk updateHardDisk, String name);
	VM updateCpuRam(UpdateCpuRam updateCpuRam, String name);
}
