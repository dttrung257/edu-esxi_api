package com.uet.esxi_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uet.esxi_api.entity.WebUser;

public interface UserRepository extends JpaRepository<WebUser, UUID> {
	@Query("SELECT u FROM WebUser u WHERE u.username = :username")
	Optional<WebUser> findByUsername(@Param("username") String username);
}
