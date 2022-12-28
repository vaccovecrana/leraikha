package io.vacco.leraikha.pojo;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;

public class BucketShelf {
  @NotNull
  public UserProfile owner;
  @NotNull @Size(max = 4)
  public List<@NotNull BlockBucket> buckets;
  @NotNull @Size(max = 8)
  public Map<String, String> tags;
}
