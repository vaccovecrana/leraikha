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
    if (t instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) t;
      if (pt.getRawType() instanceof Class) {
        Class<?> prc = (Class<?>) pt.getActualTypeArguments()[0];
        return Optional.of(prc);
      }
    }
    return Optional.empty();
  }

  public static Optional<LkProperty> propertyOf(Field field) {
    int mods = field.getModifiers();
    if (Modifier.isPublic(mods) && !Modifier.isStatic(mods)) {
      var lkp = new LkProperty().withField(field);
      var opg = genericTypeOf(field);
      if (opg.isPresent()) {
        lkp.withType(opg.get());
        lkp.withCollectionType(field.getType());
      } else {
        lkp.withType(field.getType());
      }
      for (Annotation an : field.getDeclaredAnnotations()) {
        if (isMappable(an)) {
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
      // TODO map fields and generate validation constraints.
      for (var field : cN.getDeclaredFields()) {
        propertyOf(field).ifPresent(lkp -> obj.properties.add(lkp));
      }
      cN = cN.getSuperclass();
    }
    return obj;
  }

}
