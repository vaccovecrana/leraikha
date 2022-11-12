package io.vacco.leraikha;

import java.util.*;

public class LkObject {

  public Class<?> clazz;
  public List<LkProperty> properties = new ArrayList<>();

  public LkObject withClass(Class<?> clazz) {
    this.clazz = Objects.requireNonNull(clazz);
    return this;
  }

  @Override public String toString() {
    return clazz == null ? "?" : clazz.getCanonicalName();
  }
}
