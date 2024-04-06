package org.dows.sdk.extract;

import java.lang.annotation.Repeatable;


@Repeatable(ElementMappings.class)
public @interface ElementMapping {



    String channel();

    String name();
}
