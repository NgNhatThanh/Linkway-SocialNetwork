package com.social_network.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {

    private JdbcOperations jdbcOperations;

    private PostService postService;

    @Transactional
    public void increasePostVote(int postId, String username, int voteType){
        String query = "INSERT INTO post_votes (post_id, voter_username, vote_type)\n" +
                "VALUES (" + postId + ", '" + username + "', " + voteType + ")" +
                "ON DUPLICATE KEY UPDATE\n" +
                "vote_type = VALUES(vote_type)";
        jdbcOperations.execute(query);
    }

    public void unvotePost(int postId, String username){
        String query = "delete from post_votes\n " +
                "where post_id = " + postId + " and voter_username = '" + username + "'";
        jdbcOperations.execute(query);
    }

    public int getUserPostVoteType(int postId, String username){
        String query = "select coalesce(vote_type, 0) from post_votes\n" +
                "where post_id = " + postId + " and voter_username = '" + username + "'";
        Integer type;
        try{
            type = jdbcOperations.queryForObject(query, Integer.class);
            return type;
        }
        catch (EmptyResultDataAccessException e){
            return 0;
        }
    }

}
