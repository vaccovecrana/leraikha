package io.vacco.leraikha;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class LkObjects {

  public static Type[] genericTypesOf(Field f) {
    var t = f.getGenericType();
    if (t instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) t;
      if (pt.getRawType() instanceof Class) {
        return pt.getActualTypeArguments();
      }
    }
    return new Type[0];
  }

  public static Optional<LkProperty> propertyOf(Field field) {
    int mods = field.getModifiers();
    if (Modifier.isPublic(mods) && !Modifier.isStatic(mods)) {
      var lkp = new LkProperty().withField(field);
      var gta = genericTypesOf(field);
      if (gta.length > 0) {
        lkp = lkp
            .withType0(gta[0])
            .withCollectionType(field.getType());
        if (gta.length > 1) {
          lkp = lkp.withType1(gta[1]);
        }
      } else {
        lkp = lkp.withType0(field.getType());
      }
      for (Annotation an : field.getDeclaredAnnotations()) {
        if (LkConstraints.isMappable(an)) {
          lkp.constraints.add(an);
        }
      }
      return Optional.of(lkp);
    }
    return Optional.empty();
  }

  public static LkObject objectOf(Class<?> clazz) {
    var obj = new LkObject().withClass(clazz);
    var cN = clazz;
    while (cN != null) {
      for (var field : cN.getDeclaredFields()) {
        propertyOf(field).ifPresent(lkp -> obj.properties.add(lkp));
      }
      cN = cN.getSuperclass();
    }
    return obj;
  }

  public static LkObject objectOf(Type t) {
    if (t instanceof Class) {
      return objectOf((Class<?>) t);
    }
    throw new IllegalArgumentException("Not a class: " + t);
  }

  public static void indexOfTail(Map<Type, LkObject> idx, Type t) {
    if (!idx.containsKey(t)) {
      var lko = objectOf(t);
      idx.put(t, lko);
      for (LkProperty lkp : lko.properties) {
        var isJsonType = LkJsonTypes.isJsonType(lkp.type0);
        if (!isJsonType) {
          indexOfTail(idx, lkp.type0);
        }
      }
    }
  }

  public static Map<Type, LkObject> indexOf(Class<?> ... classes) {
    var out = new HashMap<Type, LkObject>();
    for (Class<?> c : classes) {
      indexOfTail(out, c);
    }
    return out;
  }

}
