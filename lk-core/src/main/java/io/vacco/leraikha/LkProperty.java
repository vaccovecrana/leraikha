package io.vacco.leraikha;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class LkProperty {

  public Field field;
  public List<Annotation> constraints = new ArrayList<>();

  public LkProperty withField(Field f) {
    this.field = Objects.requireNonNull(f);
    return this;
  }

  @Override public String toString() {
    return String.format("%s [%s]",
        field == null ? "?" : field.getName(),
        field == null ? "?" : field.getType()
    );
  }
}
