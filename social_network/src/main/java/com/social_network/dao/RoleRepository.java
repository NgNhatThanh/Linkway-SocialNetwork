package com.social_network.dao;

import com.social_network.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findById(int id);

    Role findByName(String name);

    @Query(nativeQuery = true
            , value = "select * from roles as ro " +
            "join (select a.role_id  from (\n" +
            "\tselect id from authorities\n" +
            "    where name = 'get_post'\n" +
            ") as r\n" +
            "join roles_authorities a \n" +
            "on r.id = a.authority_id\n" +
            ") as t\n" +
            "on ro.id = t.role_id ")
    Set<Role> findAllByAuthority(String authority);

}
