package com.eiben.Blog.App.controllers.dto.response;

public class AbstractBaseResponse<D> {
  private D data;
  private String error;

  public AbstractBaseResponse(D data, String error) {
    this.data = data;
    this.error = error;
  }

  public AbstractBaseResponse() {

  }
}
