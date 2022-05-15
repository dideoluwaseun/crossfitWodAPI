package com.seun.crossfitWodAPI.service;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.Roles;
import com.seun.crossfitWodAPI.domain.dto.MembersDTO;

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
