package io.vacco.leraikha;

import java.lang.reflect.Type;
import java.math.*;
import java.util.*;

import static io.vacco.sabnock.SkJson.*;

public class LkJsonTypes {

  public static final String
      string = "string",
      number = "number",
      integer = "integer",
      bool = "boolean",
      array = "array",
      object = "object";

  public static Map<Class<?>, String> json = obj(
      kv(Character.class, string), kv(char.class, string),
      kv(String.class, string),

      kv(Byte.class, integer), kv(byte.class, integer),
      kv(Short.class, integer), kv(short.class, integer),
      kv(Integer.class, integer), kv(int.class, integer),
      kv(Long.class, integer), kv(long.class, integer),
      kv(BigInteger.class, integer),

      kv(Float.class, number), kv(float.class, number),
      kv(Double.class, number), kv(double.class, number),
      kv(BigDecimal.class, number),

      kv(Boolean.class, bool),

      kv(List.class, array),
      kv(Set.class, array),
      kv(Map.class, object)
  );

  public static boolean isJsonType(Class<?> c) {
    return json.containsKey(c);
  }

  public static boolean isJsonType(Type t) {
    if (t instanceof Class) {
      return isJsonType((Class<?>) t);
    }
    return false;
  }

}
