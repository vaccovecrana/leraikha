package io.vacco.leraikha;

import io.vacco.leraikha.pojo.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class LkObjectTest {
  static {
    it("Describes a single POJO", () -> {
      var lko = LkObjects.objectOf(BlockBucket.class);
      System.out.println(lko);
      for (LkProperty p : lko.properties) {
        System.out.println(p);
      }
    });
    it("Describes all related classes for a base class list", () -> {
      var idx = LkObjects.indexOf(BucketShelf.class);
      for (LkObject o : idx.values()) {
        System.out.println(o);
      }
    });
    it("Scans an object's properties", () -> {
      var ctx = new LkContext(BucketShelf.class);
      var schema = ctx.buildSchema();
      System.out.println(schema);
    });
  }
}
