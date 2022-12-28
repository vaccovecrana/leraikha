package io.vacco.leraikha;

import com.google.gson.*;
import jakarta.validation.constraints.NotNull;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static io.vacco.sabnock.SkJson.*;

public class LkContext {

  public static final String pType = "type", pObject = "object", pRef = "$ref";

  private final Gson g = new GsonBuilder().setPrettyPrinting().create();
  private final Map<Type, LkObject> classIdx;

  // TODO add initialization parameters (i.e. output dir, prefix, etc.).

  public LkContext(Class<?> ... classes) {
    this.classIdx = LkObjects.indexOf(classes);
  }

  private Map<String, Object> typeRefOf(String name) {
    return obj(kv(pRef, String.format("#/definitions/%s", name)));
  }

  private Map<String, Object> mapEnum(Class<?> e) {
    return obj(
      kv("enum", Arrays.stream(e.getEnumConstants())
        .map(Object::toString)
        .toArray())
    );
  }

  private Map<String, Object> mapScalarProperty(Type t) {
    var pTypeName = t.getTypeName();
    if (classIdx.containsKey(t)) {
      return typeRefOf(pTypeName);
    }
    var type = LkJsonTypes.json.get((Class<?>) t);
    if (type != null) {
      return obj(kv(pType, type));
    }
    throw new IllegalStateException("Unable to map scalar property of type " + t);
  }

  private Map<String, Object> mapCollectionProperty(LkProperty p) {
    var pcc = (Class<?>) p.collectionType;
    if (List.class.isAssignableFrom(pcc)) {
      return obj(
        kv(pType, "array"),
        kv("items", mapScalarProperty(p.type0))
      );
    } else if (Map.class.isAssignableFrom(pcc)) {
      return obj(
        kv(pType, pObject),
        kv("additionalProperties", mapScalarProperty(p.type1))
      );
    }
    return obj();
  }

  private Map<String, Object> mapProperty(LkProperty p) {
    var pn = p.collectionType != null
      ? mapCollectionProperty(p)
      : mapScalarProperty(p.type0);
    for (Annotation an : p.constraints) {
      pn.putAll(LkConstraints.apply(an));
    }
    return pn;
  }

  private Object mapSingle(LkObject o) {
    if (Enum.class.isAssignableFrom(o.clazz)) {
      return mapEnum(o.clazz);
    }
    var reqProps = o.properties.stream()
      .filter(p -> p.constraints.stream().anyMatch(a -> a instanceof NotNull))
      .map(p -> p.field.getName())
      .collect(Collectors.toList());
    return obj(
      kv(pType, pObject),
      kv("properties", mapOn(
        o.properties.stream()
          .map(p -> kv(p.field.getName(), mapProperty(p)))
      )),
      reqProps.isEmpty() ? kv("", "") : kv("required", reqProps)
    );
  }

  private Object mapDefinitions() {
    return mapOn(
      classIdx.values().stream()
        .map(sc -> kv(sc.clazz.getCanonicalName(), mapSingle(sc)))
    );
  }

  private Object mapOneOf() {
    return classIdx.values().stream()
      .map(sc -> typeRefOf(sc.clazz.getCanonicalName()))
      .toArray();
  }

  private Object buildRoot() {
    return obj(
      kv("definitions", mapDefinitions()),
      kv(pType, pObject),
      kv("oneOf", mapOneOf())
    );
  }

  public String buildSchema() {
    return g.toJson(buildRoot());
  }

  public void buildSchema(Writer w) {
    g.toJson(buildRoot(), w);
  }

}
