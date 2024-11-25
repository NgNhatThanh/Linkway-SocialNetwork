package com.social_network.dao;

import com.social_network.entity.Tag;
import com.social_network.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

        @Query(nativeQuery = true, value = "select * from tags \n" +
                        "join (\n" +
                        "\tselect tag_id from users_tags\n" +
                        "    where user_id = ?1\n" +
                        ") as t\n" +
                        "on tags.id = t.tag_id")
        List<Tag> findFollowingTagsByUserId(int id);

        List<Tag> findAll();

        @Query(nativeQuery = true, value = "select * from tags " +
                        "where lower(name) = lower(?1)")
        Tag findByName(String name);

        @Query(nativeQuery = true, value = "select case when exists(" +
                        "select * from users_tags " +
                        "where user_id = ?1 and tag_id = ?2) " +
                        "then 'true' else 'false' end")
        boolean isFollowing(int userId, int tagId);

        @Query(nativeQuery = true, value = "insert into users_tags (user_id, tag_id) " +
                        "values(?1, ?2)")
        @Modifying
        void follow(int userId, int tagId);

        @Query(nativeQuery = true, value = "delete from users_tags " +
                        "where user_id = ?1 and tag_id = ?2")
        @Modifying
        void unfollow(int userId, int tagId);

        Page<Tag> findByNameIgnoreCaseContaining(String name, Pageable pageable);

        @Query("SELECT t FROM Tag t JOIN t.posts p WHERE p.id = :postId")
        List<Tag> findTagsByPostId(@Param("postId") Long postId);

}
