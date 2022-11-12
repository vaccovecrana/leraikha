package io.vacco.leraikha;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class LkProperty {

  public Field field;
  public Type type;
  public Type collectionType;
  public List<Annotation> constraints = new ArrayList<>();

  public LkProperty withField(Field f) {
    this.field = Objects.requireNonNull(f);
    return this;
  }

  public LkProperty withType(Type t) {
    this.type = Objects.requireNonNull(t);
    return this;
  }

  public LkProperty withCollectionType(Type t) {
    this.collectionType = Objects.requireNonNull(t);
    return this;
  }

  @Override public String toString() {
    return String.format("%s %s (%d)",
        field != null ? field.getName() : "?",
        collectionType != null
            ? String.format("%s<%s>", collectionType.getTypeName(), type.getTypeName())
            : field != null ? field.getType().getSimpleName() : "?",
        constraints.size()
    );
  }
}
