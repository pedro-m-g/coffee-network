package com.coffee.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffee.network.models.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {

}
