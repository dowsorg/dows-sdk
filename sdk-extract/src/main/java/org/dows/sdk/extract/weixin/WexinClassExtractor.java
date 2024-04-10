package org.dows.sdk.extract.weixin;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.Extractable;
import org.dows.sdk.extract.ExtractPojo;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WexinClassExtractor implements Extractable {
    @Override
    public void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        String path = extractPojo.getPath();
        int methodIndex = path.lastIndexOf("@");
        int classIndex = path.lastIndexOf(".");

        String method = path.substring(methodIndex + 1);
        String clazz = path.substring(classIndex + 1, methodIndex);
        String pkg = "";
        if (classIndex != -1) {
            pkg = path.substring(0, classIndex);
        }

        // 替换中文
        String[] pkgItems = pkg.split("\\.");
        for (String item : pkgItems) {
            /*Object o = map.get(item);
            if (o != null) {
                pkg = pkg.replace(item, o.toString());
            }*/
        }
        /*Object co = map.get(clazz);
        if (co != null) {
            clazz = co.toString();
        }

        ClassElement classElement = classMap.get(pkg + "." + clazz);
        if (classElement == null) {
            classElement = new ClassElement();
            classElement.setCode(clazz);
            classElement.setPgk(pkg);
        }*/
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
    }
}
