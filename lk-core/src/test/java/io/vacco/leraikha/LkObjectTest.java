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
    it("Scans an object's properties", () -> {
      var lol = LkObjects.objectOf(BlockBucket.class);
      var lel = LkObjects.objectOf(MegaBlock.class);
      System.out.println("lol");
    });
  }
}
