package org.example.blog_manager.repository;

import org.example.blog_manager.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
