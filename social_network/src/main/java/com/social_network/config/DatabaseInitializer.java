package com.social_network.config;

import com.social_network.entity.Authority;
import com.social_network.entity.Role;
import com.social_network.entity.User;
import com.social_network.dao.AuthorityRepository;
import com.social_network.dao.RoleRepository;
import com.social_network.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseInitializer implements CommandLineRunner {

        private final AuthorityRepository authorityRepository;
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public DatabaseInitializer(
                        AuthorityRepository authorityRepository,
                        RoleRepository roleRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                this.authorityRepository = authorityRepository;
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
                System.out.println(">>> START INIT DATABASE");
                long countPermissions = this.authorityRepository.count();
                long countRoles = this.roleRepository.count();
                long countUsers = this.userRepository.count();

                if (countPermissions == 0) {
                        ArrayList<Authority> arr = new ArrayList<>();
                        arr.add(new Authority("Create a permission", "/api/v1/permissions", "POST",
                                        "PERMISSIONS"));
                        arr.add(new Authority("Update a permission", "/api/v1/permissions", "PUT",
                                        "PERMISSIONS"));
                        arr.add(new Authority("Delete a permission", "/api/v1/permissions/{id}",
                                        "DELETE", "PERMISSIONS"));
                        arr.add(new Authority("Get a permission by id", "/api/v1/permissions/{id}",
                                        "GET", "PERMISSIONS"));
                        arr.add(new Authority("Get permissions with pagination",
                                        "/api/v1/permissions", "GET", "PERMISSIONS"));

                        arr.add(new Authority("Create a role", "/api/v1/roles", "POST", "ROLES"));
                        arr.add(new Authority("Update a role", "/api/v1/roles", "PUT", "ROLES"));
                        arr.add(new Authority("Delete a role", "/api/v1/roles/{id}", "DELETE",
                                        "ROLES"));
                        arr.add(new Authority("Get a role by id", "/api/v1/roles/{id}", "GET",
                                        "ROLES"));
                        arr.add(new Authority("Get roles with pagination", "/api/v1/roles", "GET",
                                        "ROLES"));

                        arr.add(new Authority("Create a user", "/api/v1/users/create", "POST",
                                        "USERS"));
                        arr.add(new Authority("Update a user", "/api/v1/users/update", "PUT",
                                        "USERS"));
                        arr.add(new Authority("Delete a user", "/api/v1/users/{id}", "DELETE",
                                        "USERS"));
                        arr.add(new Authority("Get a user by id", "/api/v1/users/{id}", "GET",
                                        "USERS"));
                        arr.add(new Authority("Get users with pagination", "/api/v1/users", "GET",
                                        "USERS"));

                        this.authorityRepository.saveAll(arr);
                }
                if (countRoles == 0 || countPermissions == 0) {
                        List<Authority> allPermissions = this.authorityRepository.findAll();

                        // Tạo role SUPER_ADMIN với toàn bộ quyền
                        Role adminRole = new Role();
                        adminRole.setName("SUPER_ADMIN");
                        adminRole.setAuthorities(allPermissions);
                        this.roleRepository.save(adminRole);

                        // Lọc quyền cho role USER dựa trên các permission liên quan
                        List<Authority> userPermissions = new ArrayList<>();

                        // Quyền về bài viết (Post)
                        // userPermissions.add(this.authorityRepository.findByName("Create a new
                        // post"));
                        // userPermissions.add(this.authorityRepository.findByName("Update a post"));
                        // userPermissions.add(this.authorityRepository.findByName("Delete a post"));
                        // userPermissions.add(this.authorityRepository
                        // .findByName("Fetch all posts with pagination and filtering"));

                        // // Quyền về Like/Unlike cho bài viết và bình luận
                        // userPermissions.add(this.authorityRepository.findByName("Like a post"));
                        // userPermissions.add(this.authorityRepository.findByName("Unlike a post"));
                        // userPermissions.add(this.authorityRepository.findByName("Like a comment"));
                        // userPermissions.add(this.authorityRepository.findByName("Unlike a comment"));

                        // // Quyền về Comment
                        // userPermissions.add(this.authorityRepository.findByName("Add a new comment to
                        // the post"));
                        // userPermissions.add(this.authorityRepository.findByName("Update a comment"));
                        // userPermissions.add(this.authorityRepository.findByName("Delete a comment"));

                        // // Quyền Follow/Unfollow
                        // userPermissions.add(this.authorityRepository.findByName("Follow a user"));
                        // userPermissions.add(this.authorityRepository.findByName("Unfollow a user"));

                        // // **Thêm các quyền về xem danh sách follower và following**
                        // userPermissions.add(this.authorityRepository.findByName("Get followers of a
                        // user"));
                        // userPermissions.add(this.authorityRepository.findByName("Get following list
                        // of a user"));

                        // // Quyền về xóa tài khoản user (nếu cần)
                        userPermissions.add(this.authorityRepository.findByName("Delete a user"));

                        // Tạo role USER và gán quyền đã lọc
                        Role userRole = new Role();
                        userRole.setName("USER");
                        userRole.setAuthorities(userPermissions); // Gán các quyền hạn phù hợp
                        this.roleRepository.save(userRole);
                }

                if (countUsers == 0) {
                        User adminUser = new User();
                        adminUser.setUsername("admin");
                        adminUser.setEmail("admin@gmail.com");
                        adminUser.setDisplayName("I'm super admin");
                        adminUser.setPassword(this.passwordEncoder.encode("123456"));

                        Role adminRole = this.roleRepository.findByName("SUPER_ADMIN");
                        if (adminRole != null) {
                                adminUser.setRole(adminRole);
                        }

                        this.userRepository.save(adminUser);
                }

                if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
                        System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
                } else
                        System.out.println(">>> END INIT DATABASE");
        }

}
