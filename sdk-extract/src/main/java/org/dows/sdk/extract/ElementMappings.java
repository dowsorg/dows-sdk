package org.dows.sdk.extract;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElementMappings {
    ElementMapping[] value();
}
