package com.seun.crossfitwodapi.repository;

import com.seun.crossfitwodapi.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Members findByUsername(String username);
    Optional<Members> findByEmail(String username);

}
