package org.dows.sdk.extract;

import java.lang.annotation.Repeatable;


@Repeatable(FieldMappings.class)
public @interface FieldMapping {



    String channel();

    String name();
}
