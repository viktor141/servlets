package ru.viktor.controller;

import com.google.gson.Gson;
import ru.viktor.exception.NotFoundException;
import ru.viktor.model.Post;
import ru.viktor.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

import static ru.viktor.servlet.MainServlet.APPLICATION_JSON;

public class PostController {

  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    response.getWriter().print(toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try{
      Post post = service.getById(id);
      response.getWriter().print(toJson(post));
    } catch (NotFoundException e){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    // TODO: deserialize request & serialize response
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    try{
      final var data = service.save(post);
      response.getWriter().print(gson.toJson(data));
    } catch (NotFoundException e){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try{
      Post post = service.getById(id);
      service.removeById(id);
      response.getWriter().print("Post deleted\n" + toJson(post));
    } catch (NotFoundException e){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    // TODO: deserialize request & serialize response
  }

  private String toJson(Object o){
    return new Gson().toJson(o);
  }
}
