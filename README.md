# leraikha

JSON schema generator via [Bean Validation 3.0](https://jakarta.ee/specifications/bean-validation/3.0/).

This library attempts to map classes with BV3.0 annotations into [JSON Schema 2020-12](https://json-schema.org/understanding-json-schema/reference/) schemas.

Available at Maven Central at the following coordinates:

```
io.vacco.leraikha:leraikha:<LATEST_VERSION>
```

Given the following class structure, a JSON schema document will be generated:

```java
package io.vacco.leraikha.pojo;

public enum Color { Red, Blue, Yellow, Green; }
```

```java
package io.vacco.leraikha.pojo;

import jakarta.validation.constraints.*;

public class MegaBlock {
  @NotNull @Positive public int rows;
  @NotNull @Positive public int cols;
  @NotNull public Color color;
}
```

```java
package io.vacco.leraikha.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;

public class BlockBucket {
  @NotNull @Size(max = 10)
  public List<@Valid MegaBlock> blocks = new ArrayList<>();
}
```

```java
System.out.println(new LkContext(BlockBucket.class).buildSchema())
```

```json
{
  "definitions": {
    "io.vacco.leraikha.pojo.BlockBucket": {
      "type": "object",
      "properties": {
        "blocks": {
          "type": "array",
          "items": {
            "$ref": "#/definitions/io.vacco.leraikha.pojo.MegaBlock"
          },
          "minItems": 0,
          "maxItems": 10
        }
      },
      "required": [
        "blocks"
      ]
    },
    "io.vacco.leraikha.pojo.Color": {
      "enum": [
        "Red",
        "Blue",
        "Yellow",
        "Green"
      ]
    },
    "io.vacco.leraikha.pojo.MegaBlock": {
      "type": "object",
      "properties": {
        "rows": {
          "type": "integer",
          "exclusiveMinimum": 0
        },
        "cols": {
          "type": "integer",
          "exclusiveMinimum": 0
        },
        "color": {
          "$ref": "#/definitions/io.vacco.leraikha.pojo.Color"
        }
      },
      "required": [
        "rows",
        "cols",
        "color"
      ]
    }
  },
  "type": "object",
  "oneOf": [
    {
      "$ref": "#/definitions/io.vacco.leraikha.pojo.BlockBucket"
    },
    {
      "$ref": "#/definitions/io.vacco.leraikha.pojo.Color"
    },
    {
      "$ref": "#/definitions/io.vacco.leraikha.pojo.MegaBlock"
    }
  ]
}
```

## Implementation caveats

- Primitive data types are mapped as defined by [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference#type)
- Only `public` fields will be mapped.
- Classes which extend from other classes will generate only child classes, but will include fields from all ancestor classes.
