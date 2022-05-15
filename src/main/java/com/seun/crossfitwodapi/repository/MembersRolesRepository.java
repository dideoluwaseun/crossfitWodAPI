package com.seun.crossfitwodapi.repository;

import com.seun.crossfitwodapi.domain.MembersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRolesRepository extends JpaRepository<MembersRoles, Long> {
}
