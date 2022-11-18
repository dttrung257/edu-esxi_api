package com.uet.esxi_api.service;

import java.util.List;
import java.util.UUID;

import com.uet.esxi_api.entity.VM;

public interface VMService {
	VM findByName(String name);
	VM save(VM vm);
	VM createVM(VM vm);
	void deleteVM(VM vm);
	List<VM> getVMs(UUID userId);
}
