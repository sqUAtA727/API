package com.example.demo.repo;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String username, String password);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
