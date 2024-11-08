package com.social_network.service;

import com.social_network.entity.Post;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {

    private JdbcOperations jdbcOperations;

    private PostService postService;

    @Transactional
    public void updatePostVote(int postId, String username, int voteType){
        String query = "INSERT INTO post_votes (post_id, voter_username, vote_type)\n" +
                "VALUES (" + postId + ", '" + username + "', " + voteType + ")" +
                "ON DUPLICATE KEY UPDATE\n" +
                "vote_type = VALUES(vote_type)";

        Post post = postService.findById(postId);
        if(voteType == 1) post.setUpvotes(post.getUpvotes() + 1);
        else post.setDownvotes(post.getDownvotes() + 1);
        jdbcOperations.execute(query);
    }

}
