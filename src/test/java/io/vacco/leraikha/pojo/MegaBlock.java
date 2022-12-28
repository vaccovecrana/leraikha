package io.vacco.leraikha.pojo;

import jakarta.validation.constraints.*;

public class MegaBlock {
  @NotNull @Positive public int rows;
  @NotNull @Positive public int cols;
  @NotNull public Color color;
}
