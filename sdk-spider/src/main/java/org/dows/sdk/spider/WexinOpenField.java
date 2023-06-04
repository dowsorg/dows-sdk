package org.dows.sdk.spider;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WexinOpenField {
    String value();
}
