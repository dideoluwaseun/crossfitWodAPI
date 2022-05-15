package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName (String roleName);
}
