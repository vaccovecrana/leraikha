package io.vacco.leraikha;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class LkProperty {

  public Field field;
  public Type type0, type1;
  public Type collectionType;
  public List<Annotation> constraints = new ArrayList<>();

  public LkProperty withField(Field f) {
    this.field = Objects.requireNonNull(f);
    return this;
  }

  public LkProperty withType0(Type t) {
    this.type0 = Objects.requireNonNull(t);
    return this;
  }

  public LkProperty withType1(Type t) {
    this.type1 = Objects.requireNonNull(t);
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
            ? String.format("%s<%s>", collectionType.getTypeName(), type0.getTypeName())
            : field != null ? field.getType().getSimpleName() : "?",
        constraints.size()
    );
  }
}
