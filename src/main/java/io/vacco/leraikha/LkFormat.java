package io.vacco.leraikha;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.*;

@Target({FIELD})
@Retention(RUNTIME)
public @interface LkFormat {
  LkFormatStd value();
  String message() default "{io.vacco.leraikha.constraints.LkFormat.message}";
  String custom() default "";
}
