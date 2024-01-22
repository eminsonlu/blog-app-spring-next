package com.eiben.Blog.App.controllers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

  @NotEmpty
  private String name;
  @NotEmpty
  @Size(min = 5, max = 50)
  private String password;
  @NotEmpty
  @Size(min = 5, max = 50)
  @Email
  private String email;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
