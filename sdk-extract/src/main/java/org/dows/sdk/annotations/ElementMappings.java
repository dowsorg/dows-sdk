package org.dows.sdk.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElementMappings {
    ElementMapping[] value();
}
