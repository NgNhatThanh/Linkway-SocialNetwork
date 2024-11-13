package com.social_network.dao;

import com.social_network.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(nativeQuery = true,
        value = "select * from tags \n" +
                "join (\n" +
                "\tselect tag_id from users_tags\n" +
                "    where user_id = ?1\n" +
                ") as t\n" +
                "on tags.id = t.tag_id")
    List<Tag> findFollowingTagsByUserId(int id);

    List<Tag> findAll();

    Tag findByName(String name);

}
