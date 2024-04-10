package org.dows.sdk.extract;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.annotations.Extract;

import java.lang.reflect.Field;

@Slf4j
@Data
public class ExtractPojo {

    private Object element;

    private String name;

    private Field field;

    private Extract extract;

    private String url;

    private String path;

    public void setValue(Object code) {
        field.setAccessible(true);
        try {
            field.set(element, code);
        } catch (IllegalAccessException e) {
            log.info("set value error :{}", JSONUtil.toJsonStr(this));
//            throw new RuntimeException(e);
        }
    }
}
