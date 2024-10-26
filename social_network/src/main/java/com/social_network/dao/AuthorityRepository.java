package com.social_network.dao;

import com.social_network.entity.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByName(String name);

    Page<Authority> findAllByHttpMethod(String method, Pageable pageable);

    Page<Authority> findAllByModule(String module, Pageable pageable);

    Page<Authority> findAll(Pageable pageable);

    @Query(nativeQuery = true
    , value = "select module from authorities")
    List<String> getAllModules();

}
