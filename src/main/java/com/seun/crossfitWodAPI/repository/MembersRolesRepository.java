package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.MembersRoles;
import com.seun.crossfitWodAPI.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRolesRepository extends JpaRepository<MembersRoles, Long> {
}
