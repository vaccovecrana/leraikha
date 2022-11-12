package io.vacco.leraikha;

import jakarta.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Optional;

public class LkObjects {

  public static boolean isMappable(Annotation a) {
    Class<?> ac = a.getClass();
    return Positive.class.isAssignableFrom(ac)
        || PositiveOrZero.class.isAssignableFrom(ac)
        || Pattern.class.isAssignableFrom(ac)
        || PastOrPresent.class.isAssignableFrom(ac)
        || Past.class.isAssignableFrom(ac)
        || NotNull.class.isAssignableFrom(ac)
        || NotEmpty.class.isAssignableFrom(ac)
        || NotBlank.class.isAssignableFrom(ac)
        || NegativeOrZero.class.isAssignableFrom(ac)
        || Negative.class.isAssignableFrom(ac)
        || Min.class.isAssignableFrom(ac)
        || Max.class.isAssignableFrom(ac)
        || FutureOrPresent.class.isAssignableFrom(ac)
        || Future.class.isAssignableFrom(ac)
        || Email.class.isAssignableFrom(ac)
        || Digits.class.isAssignableFrom(ac)
        || DecimalMin.class.isAssignableFrom(ac)
        || DecimalMax.class.isAssignableFrom(ac);
  }

  public static Optional<Class<?>> genericTypeOf(Field f) {
    var t = f.getGenericType();
    if (t instanceof Class) {
      return Optional.of((Class<?>) t);
    } else if (t instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) t;
      if (pt.getRawType() instanceof Class) {
        Class<?> prc = (Class<?>) pt.getActualTypeArguments()[0];
        return Optional.of(prc);
      }
    }
    return Optional.empty();
  }

  public static LkObject from(Class<?> clazz) {
    var obj = new LkObject().withClass(clazz);
    var cN = clazz;
    while (cN != null) {
      for (var field : cN.getDeclaredFields()) {
        int mods = field.getModifiers();
        if (Modifier.isPublic(mods) && !Modifier.isStatic(mods)) {
          var prop = new LkProperty().withField(field);

          field.setAccessible(true);
          obj.properties.add(prop);

          genericTypeOf(field); // TODO map fields and generate validation constraints.

          for (Annotation an : field.getDeclaredAnnotations()) {
            if (isMappable(an)) {
              prop.constraints.add(an);
            }
          }
        }
      }
      cN = cN.getSuperclass();
    }
    return obj;
  }

}
