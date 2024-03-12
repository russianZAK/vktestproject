package by.russianzak.vktestproject.user.role;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_POSTS_VIEWER("ROLE_POSTS_VIEWER"),
  ROLE_POSTS_EDITOR("ROLE_POSTS_EDITOR"),
  ROLE_USERS_VIEWER("ROLE_USERS_VIEWER"),
  ROLE_USERS_EDITOR("ROLE_USERS_EDITOR"),
  ROLE_ALBUMS_VIEWER("ROLE_ALBUMS_VIEWER"),
  ROLE_ALBUMS_EDITOR("ROLE_ALBUMS_EDITOR");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  @Override
  public String getAuthority() {
    return value;
  }
}
