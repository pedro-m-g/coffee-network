package com.coffee.network.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.coffee.network.models.Post;
import com.coffee.network.repositories.PostsRepository;
import com.coffee.network.requests.CreatePostRequest;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostsController {

  private PostsRepository postsRepository;

  public PostsController(
    @Autowired PostsRepository postsRepository
  ) {
    this.postsRepository = postsRepository;
  }

  @GetMapping("")
  public List<Post> getAllPosts() {
    return postsRepository.findAll();
  }

  @PostMapping("")
  public void createPost(@RequestBody CreatePostRequest request) {
    Post post = new Post();
    post.setContent(request.getContent());
    postsRepository.save(post);
  }

  @GetMapping("{id}")
  public Post getPost(@PathVariable("id") int id) {
    Post post = postsRepository.findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND)
      );
    return post;
  }

  @PutMapping("{id}")
  public void updatePost(
    @PathVariable("id") int id,
    @RequestBody CreatePostRequest request
  ) {
    Post post = postsRepository.findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND)
      );
    post.setContent(request.getContent());
    postsRepository.save(post);
  }

  @DeleteMapping("{id}")
  public void deletePost(@PathVariable("id") int id) {
    Post post = postsRepository.findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND)
      );
    postsRepository.delete(post);
  }

}
