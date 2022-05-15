package com.seun.crossfitwodapi.service;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Roles;
import com.seun.crossfitwodapi.domain.dto.MembersDTO;

import java.util.List;
import java.util.Optional;

public interface MembersService {
    List<Members> getAllMembers(Integer pageNo, Integer elementPerPage);
    Optional<Members> getMemberById(Long id);
    Members getMemberByUsername(String username);
    Members saveOneMember(MembersDTO membersDTO);
    Roles saveRole(Roles role);
    void addRoleToMembers(String username, String roleName);
    Members updateOneMember(Long id, String name, String email, String username, String password);
    void deleteOneMember(Long id);
}
