package org.dows.sdk.mapping;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WexinOpenField {
    String value();
}
