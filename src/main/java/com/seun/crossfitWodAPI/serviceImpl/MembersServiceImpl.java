package com.seun.crossfitWodAPI.serviceImpl;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.MembersRoles;
import com.seun.crossfitWodAPI.domain.Roles;
import com.seun.crossfitWodAPI.domain.dto.MembersDTO;
import com.seun.crossfitWodAPI.exception.BadRequestException;
import com.seun.crossfitWodAPI.exception.ResourceAlreadyExistsException;
import com.seun.crossfitWodAPI.exception.ResourceNotFoundException;
import com.seun.crossfitWodAPI.repository.MembersRepository;
import com.seun.crossfitWodAPI.repository.MembersRolesRepository;
import com.seun.crossfitWodAPI.repository.RolesRepository;
import com.seun.crossfitWodAPI.service.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembersServiceImpl implements MembersService, UserDetailsService {
    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final MembersRolesRepository membersRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members members = membersRepository.findByUsername(username);
        if(members == null) {
            log.error("Member not found in the database");
            throw new UsernameNotFoundException("Member not found in the database");
        } else {
            log.info("Member found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        members.getMembersRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoles().getName())));
        return new User(members.getUsername(),members.getPassword(), authorities);
    }

    @Cacheable(value = "Members")
    @Override
    public List<Members> getAllMembers(Integer pageNo, Integer elementPerPage) {
        Pageable membersPage = PageRequest.of(pageNo, elementPerPage);
        log.info("Fetching list of all members" );
        log.debug("its appearing twice");
        return membersRepository.findAll(membersPage).getContent();
    }

    @Cacheable(value = "Members", key = "#id")
    @Override
    public Optional<Members> getMemberById(Long id) {
        if (membersRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Member does not exist");
        }
        log.info("Fetching details of members with id {}", id );
        return membersRepository.findById(id);
    }

    @Override
    public Members getMemberByUsername(String username) {
        Members member = membersRepository.findByUsername(username);
        if(member == null) {
            log.error("Member with username {} does not exist", username);
            throw new ResourceNotFoundException();
        }
        log.info("Fetching details of member with username {}", username );
        return member;
    }

    @Transactional
    @Override
    public Members saveOneMember(MembersDTO membersDTO) {
        if(Objects.isNull(membersDTO)) {
            throw new BadRequestException("No request body found");
        }
        if (membersRepository.findByEmail(membersDTO.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Member you are trying to save already exists");
        }
        if(membersDTO.getName() == null || membersDTO.getGender() == null  || membersDTO.getDob() == null  || membersDTO.getEmail() == null  || membersDTO.getUsername() == null  || membersDTO.getPassword() == null) {
            throw new BadRequestException("Either one of name, gender, date of birth, email, username or password is missing");
        }
        log.info("Saving new member");
        return membersRepository.save(Members.builder()
                .name(membersDTO.getName())
                .gender(membersDTO.getGender())
                .dob(membersDTO.getDob())
                .email(membersDTO.getEmail())
                .username(membersDTO.getUsername())
                .password(passwordEncoder.encode(membersDTO.getPassword()))
                .createdAt(new Timestamp(new Date().getTime()))
                .updatedAt(new Timestamp(new Date().getTime()))
                .build());
    }

    @Override
    public Roles saveRole(Roles role) {
        log.info("Saving new Role");
        return rolesRepository.save(role);
    }

    @Override
    public void addRoleToMembers(String username, String roleName) {
        Roles roles = rolesRepository.findByName(roleName);
        Members members = membersRepository.findByUsername(username);
        members.getMembersRoles().add(membersRolesRepository.save(
                MembersRoles.builder()
                        .members(members)
                        .roles(roles).build()));
        log.info("Adding role to Member");
    }

    @CachePut(value = "Members", key = "#id")
    @Override
    @Transactional
    public Members updateOneMember(Long id, String name, String email, String username, String password) {
        Members members = membersRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (Objects.equals(members.getName(), name) || Objects.equals(members.getEmail(), email) || Objects.equals(members.getUsername(), username) || Objects.equals(members.getPassword(), password)) {
            throw new ResourceAlreadyExistsException("One of the fields you are trying to update already exists");
        }
        if ((name!= null) && !Objects.equals(members.getName(), name)) {
            members.setName(name);
            members.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((email!= null) && !Objects.equals(members.getEmail(), email)) {
            members.setEmail(email);
            members.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((username!= null) && !Objects.equals(members.getUsername(), username)) {
            members.setUsername(username);
            members.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((password!= null) && !Objects.equals(members.getPassword(), password)) {
            members.setPassword(passwordEncoder.encode(password));
            members.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        return membersRepository.save(members);
    }

    @CacheEvict(value = "Members", key = "#id")
    @Override
    public void deleteOneMember(Long id) {
        if (!membersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member you are trying to delete does not exist");
        } else {
            log.info("Deleting details of members with id {}", id );
            membersRepository.deleteById(id);
        }
    }
}
