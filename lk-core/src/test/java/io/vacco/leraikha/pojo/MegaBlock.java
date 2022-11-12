package io.vacco.leraikha.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MegaBlock {
  @Positive public int rows;
  @Positive public int cols;
  @NotNull public Color color;
}
