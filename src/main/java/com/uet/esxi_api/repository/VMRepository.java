package com.uet.esxi_api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uet.esxi_api.entity.VM;

public interface VMRepository extends JpaRepository<VM, UUID> {
	@Query("SELECT vm FROM VM vm WHERE vm.name = :name")
	Optional<VM> findByName(@Param("name") String name);
	
	@Query("SELECT vm FROM VM vm WHERE vm.user.id = :userId")
	List<VM> getVMs(@Param("userId") UUID userId);
}
