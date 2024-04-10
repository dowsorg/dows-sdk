package org.dows.sdk.annotations;

import org.dows.sdk.extract.DefaultExtractable;
import org.dows.sdk.extract.Extractable;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Extracts.class)
public @interface Extract {
//    String value();

    String channel() default "";

    String name() default "";

    String xpath();

    String io() default "inputs";

    String keywords() default "";

    String mapping() default "";

    String link() default "";

    Class<? extends Extractable> handler() default DefaultExtractable.class;

}
