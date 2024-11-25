package com.social_network.service;

import com.social_network.dao.PostRepository;
import com.social_network.entity.Notification;
import com.social_network.entity.Post;

import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.util.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {

    private final int POST_PER_PAGE = 10;

    private final TagService tagService;

    private final FollowService followService;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    private final NotificationService notificationService;

    private PostRepository postRepository;

    public Post save(Post post) {
        boolean isNewPost = post.getId() == 0;
        Post newPost = postRepository.save(post);
        if(isNewPost){
            List<User> followers = followService.getFollowers(post.getAuthor());
            for(User follower : followers){
                System.out.println("EFG: " + follower.getDisplayName());
                Notification notification = new Notification();
                notification.setSender(post.getAuthor());
                notification.setReceiver(follower);
                notification.setContent(post.getAuthor().getDisplayName() + " đã đăng một bài viết mới.");
                notification.setCreatedAt(Date.from(Instant.now()));
                notification.setRedirectUrl("/post/" + newPost.getId());
                notificationService.sendNotification(notification);
            }
        }
        return newPost;
    }

    public Page<Post> getAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findAll(pageable);
    }

    public Page<Post> getAllPostByFollowingTags(User user, int page) {
        List<Tag> tags = user.getFollowingTags();
        if (tags.isEmpty()) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findPostByTags(tags, pageable);
    }

    public Page<Post> getPostByFollowingTagsOrFollowingUsers(User user, int page) {
        List<Tag> tags = user.getFollowingTags();
        List<User> users = followService.getFollowers(user);
        String username = securityUtil.getCurrentUser().getUsername();
        User Currentuser = userService.findByUsername(username).get();
        List<Post> authors = postRepository.findByAuthor(Currentuser);
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        System.out.println("tags: " + tags);
        System.out.println("users: " + users);
        // if (tags.isEmpty() && users.isEmpty()) {
        // return getAll(page);
        // }

        List<Post> postsByTags = tags.isEmpty() ? Collections.emptyList()
                : postRepository.findPostByTags(tags, pageable).getContent();
        List<Post> postsByAuthors = users.isEmpty() ? Collections.emptyList()
                : postRepository.findPostsByAuthors(users, pageable).getContent();

        Set<Post> combinedPosts = new LinkedHashSet<>(postsByTags);
        combinedPosts.addAll(postsByAuthors);
        // những post có trong authors thì ko lấy nữa
        combinedPosts.removeAll(authors);

        return new PageImpl<>(new ArrayList<>(combinedPosts), pageable, combinedPosts.size());
    }

    public Page<Post> getByUser(User user, int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findByAuthor(user, pageable);
    }

    public Page<Post> findByAuthor(User author, int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findByAuthor(author, pageable);
    }

    public Post findById(int id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void increaseView(int postId) {
        Post post = findById(postId);
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
    }

    public List<Post> getRandowPostsByAuthor(User author, int maxAmount) {
        List<Post> posts = postRepository.findByAuthor(author);
        if (posts.size() <= maxAmount)
            return posts;
        Set<Post> result = new HashSet<>();
        int idx = 0;
        Random rand = new Random();
        while (result.size() < maxAmount) {
            idx = rand.nextInt(posts.size());
            result.add(posts.get(idx));
        }
        return result.stream().toList();
    }

    public Page<Post> findByTags(List<Tag> tags, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE, sort);
        return postRepository.findPostByTags(tags, pageable);
    }

    public int countPostsByAuthor(User author) {
        return postRepository.countByAuthor(author);
    }

    public Page<Post> search(String query,
            int page,
            String sortBy,
            String date,
            List<String> tagNames) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        if (sortBy.equals("hot")) {
            sort = Sort.by(Sort.Direction.DESC, "views");
        }

        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE, sort);

        LocalDate today = LocalDate.now();
        LocalDateTime dateFrom = LocalDateTime.of(1900, 1, 1, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(3000, 1, 1, 0, 0);

        switch (date) {
            case "today":
                dateFrom = today.atStartOfDay();
                dateTo = today.atTime(23, 59, 59);
                break;
            case "this_week":
                dateFrom = today.with(DayOfWeek.MONDAY).atStartOfDay();
                dateTo = today.atTime(23, 59, 59);
                break;
            case "this_month":
                dateFrom = today.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                dateTo = today.atTime(23, 59, 59);
                break;
            case "this_year":
                dateFrom = today.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
                dateTo = today.atTime(23, 59, 59);
        }

        if (tagNames.isEmpty())
            return postRepository.findPostsByTitleContainingIgnoreCaseAndCreatedAtBetween(query,
                    Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(dateTo.atZone(ZoneId.systemDefault()).toInstant()),
                    pageable);

        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            tags.add(tagService.findByName(tagName));
        }

        return postRepository.findPostsByTitleContainingIgnoreCaseAndCreatedAtBetweenAndTagsIn(query,
                Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(dateTo.atZone(ZoneId.systemDefault()).toInstant()),
                tags,
                pageable);
    }

}