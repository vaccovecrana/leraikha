package io.vacco.leraikha;

import jakarta.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.util.Map;

import static io.vacco.sabnock.SkJson.*;

public class LkConstraints {

  public static boolean isMappable(Annotation a) {
    Class<?> ac = a.getClass();
    return Positive.class.isAssignableFrom(ac)        // ok
      || PositiveOrZero.class.isAssignableFrom(ac)    // ok
      || Negative.class.isAssignableFrom(ac)          // ok
      || NegativeOrZero.class.isAssignableFrom(ac)    // ok
      || Min.class.isAssignableFrom(ac)               // ok
      || Max.class.isAssignableFrom(ac)               // ok
      || DecimalMin.class.isAssignableFrom(ac)        // ok
      || DecimalMax.class.isAssignableFrom(ac)        // ok
      || Pattern.class.isAssignableFrom(ac)           // ok
      // || PastOrPresent.class.isAssignableFrom(ac)     // looks like these annotations cannot be mapped.
      // || Past.class.isAssignableFrom(ac)
      // || FutureOrPresent.class.isAssignableFrom(ac)
      // || Future.class.isAssignableFrom(ac)
      || NotNull.class.isAssignableFrom(ac)           // special case
      || NotEmpty.class.isAssignableFrom(ac)          // ok
      || NotBlank.class.isAssignableFrom(ac)          // ok
      || Size.class.isAssignableFrom(ac)              // ok
      || Email.class.isAssignableFrom(ac)             // ok
      // || Digits.class.isAssignableFrom(ac)            // not yet supported: https://github.com/json-schema-org/json-schema-spec/issues/1123
      || LkFormat.class.isAssignableFrom(ac)
      ;
  }

  public static Map<String, Object> map(Positive an) { return obj(kv("exclusiveMinimum", 0)); }
  public static Map<String, Object> map(PositiveOrZero an) { return obj(kv("minimum", 0)); }

  public static Map<String, Object> map(Negative an) { return obj(kv("exclusiveMaximum", 0)); }
  public static Map<String, Object> map(NegativeOrZero an) { return obj(kv("maximum", 0)); }

  public static Map<String, Object> map(Min an) { return obj(kv("minimum", an.value())); }
  public static Map<String, Object> map(Max an) { return obj(kv("maximum", an.value())); }

  public static Map<String, Object> map(DecimalMin an) { return obj(kv("minimum", an.value())); }
  public static Map<String, Object> map(DecimalMax an) { return obj(kv("maximum", an.value())); }

  public static Map<String, Object> map(Pattern an) { return obj(kv("pattern", an.regexp())); }
  public static Map<String, Object> map(NotEmpty an) { return obj(kv("minLength", 1)); }
  public static Map<String, Object> map(NotBlank an) {
    return obj(kv("minLength", 1));
  }
  public static Map<String, Object> map(Size an) {
    return obj(
        kv("minItems", an.min()),
        kv("maxItems", an.max())
    );
  }

  public static Map<String, Object> map(Email an) { return obj(kv("format", "email")); }
  public static Map<String, Object> map(LkFormat an) {
    if (an.custom().length() > 0) {
      return obj(kv("format", an.custom()));
    }
    return obj(kv("format", an.value().format));
  }

  public static Map<String, Object> apply(Annotation an) {
    if (an instanceof Positive) return map((Positive) an);
    if (an instanceof PositiveOrZero) return map((PositiveOrZero) an);
    if (an instanceof Negative) return map((Negative) an);
    if (an instanceof NegativeOrZero) return map((NegativeOrZero) an);
    if (an instanceof Min) return map((Min) an);
    if (an instanceof Max) return map((Max) an);
    if (an instanceof DecimalMin) return map((DecimalMin) an);
    if (an instanceof DecimalMax) return map((DecimalMax) an);
    if (an instanceof Pattern) return map((Pattern) an);
    if (an instanceof NotNull) return obj();
    if (an instanceof NotEmpty) return map((NotEmpty) an);
    if (an instanceof NotBlank) return map((NotBlank) an);
    if (an instanceof Size) return map((Size) an);
    if (an instanceof Email) return map((Email) an);
    if (an instanceof LkFormat) return map((LkFormat) an);
    throw new IllegalArgumentException("Invalid annotation: " + an);
  }

}
