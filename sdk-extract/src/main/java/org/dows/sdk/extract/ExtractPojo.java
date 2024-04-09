package org.dows.sdk.extract;

import lombok.Data;
import org.dows.sdk.extract.Extract;

import java.lang.reflect.Field;

@Data
public class ExtractPojo {

    private Object element;

    private String name;

    private Field field;

    private Extract extract;
}
