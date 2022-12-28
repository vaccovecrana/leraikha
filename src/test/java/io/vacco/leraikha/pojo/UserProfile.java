package io.vacco.leraikha.pojo;

import io.vacco.leraikha.*;
import jakarta.validation.constraints.*;

import static io.vacco.leraikha.LkFormatStd.*;

public class UserProfile {
  @NotNull @NotEmpty public String username;
  @NotNull @LkFormat(Date) public String createdAt;
  @NotNull @LkFormat(URI) public String socialUri;
  @NotNull @LkFormat(Email) public String email;
}
