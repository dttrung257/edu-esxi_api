package com.uet.esxi_api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uet.esxi_api.entity.VM;
import com.uet.esxi_api.exception.vm.VMAlreadyExistsException;
import com.uet.esxi_api.repository.VMRepository;
import com.uet.esxi_api.service.VMService;

@Service
public class VMServiceImpl implements VMService {
	@Autowired
	private VMRepository vmRepository;
	
	@Override
	public VM findByName(String name) {
		return vmRepository.findByName(name).orElse(null);
	}
	
	@Override
	public VM save(VM vm) {
		return vmRepository.save(vm);
	}

	@Override
	public VM createVM(VM vm) {
		VM checkVM = vmRepository.findByName(vm.getName()).orElse(null);
		if (checkVM != null) {
			throw new VMAlreadyExistsException("VM name: " + vm.getName() + " already exists");
		}
		return vmRepository.save(vm);
	}

	@Override
	public void deleteVM(VM vm) {
		vmRepository.delete(vm);
	}

	@Override
	public List<VM> getListVMName(UUID userId) {
		return vmRepository.getListVMName(userId);
	}

	
}
