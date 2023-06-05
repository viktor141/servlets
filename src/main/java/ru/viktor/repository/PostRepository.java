package ru.viktor.repository;

import ru.viktor.exception.NotFoundException;
import ru.viktor.model.Post;

import java.util.*;

// Stub
public class PostRepository {

  private long id = 0;
  private final IdentityHashMap<Long, Post> postMap = new IdentityHashMap<>();


  public List<Post> all() {
    return new ArrayList<>(postMap.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postMap.get(id));
  }

  public Post save(Post post) {
    if(post.getId() == 0){
      post.setId(++id);
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
