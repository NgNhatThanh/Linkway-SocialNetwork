package com.social_network.dao;

import com.social_network.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByName(String name);

    Set<Authority> findAllByHttpMethod(String method);

    Set<Authority> findAllByModule(String module);

    @Query(nativeQuery = true
    , value = "select module from authorities")
    Set<String> getAllModules();

}
