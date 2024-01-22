package com.eiben.Blog.App.converter;

import com.eiben.Blog.App.controllers.dto.response.UserResponse;
import com.eiben.Blog.App.entities.User;

public class UserConverter {

  public static UserResponse toUserResponse(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setEmail(user.getEmail());
    return userResponse;
  }

}
