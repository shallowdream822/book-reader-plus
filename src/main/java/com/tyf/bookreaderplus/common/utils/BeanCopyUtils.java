package com.tyf.bookreaderplus.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanCopyUtils {

    public static <T> T copyObject(Object source, Class<T> target) {
        Object temp = null;

        try {
            temp = target.newInstance();
            if (null != source) {
                BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return (T) temp;
    }

    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList();
        if (null != source && source.size() > 0) {
            Iterator var3 = source.iterator();

            while(var3.hasNext()) {
                Object obj = var3.next();
                list.add(copyObject(obj, target));
            }
        }

        return list;
    }
}
