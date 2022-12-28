package io.vacco.leraikha.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;

public class BlockBucket {
  @NotNull @Size(max = 10)
  public List<@Valid MegaBlock> blocks = new ArrayList<>();
}
