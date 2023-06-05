package ru.viktor.repository;

import ru.viktor.exception.NotFoundException;
import ru.viktor.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

  private final AtomicLong id = new AtomicLong(0);
  private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();


  public List<Post> all() {
    return new ArrayList<>(postMap.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postMap.get(id));
  }

  public Post save(Post post) {
    if(post.getId() == 0){
      post.setId(id.addAndGet(1));
      postMap.put(post.getId(), post);
      return post;
    }

    if(postMap.containsKey(post.getId())){
      postMap.get(post.getId()).setContent(post.getContent());
    }else {
      throw new NotFoundException();
    }
    return post;
  }

  public void removeById(long id) {
    postMap.remove(id);
  }
}
