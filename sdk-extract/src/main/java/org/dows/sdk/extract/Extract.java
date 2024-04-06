package org.dows.sdk.extract;

import java.lang.annotation.Repeatable;


@Repeatable(Extracts.class)
public @interface Extract {
//    String value();

    String channel() default "";

    String xpath();

    String keywords() default "";

    String mapping() default "";

    String link() default "";

    //Class<? extends ExtractHandler> handler() default ExtractHandler.class;
    String handler() default "";


}
