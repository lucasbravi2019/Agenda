package com.bravi.agenda.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtils {

    private ListUtils() {}

    public static <T> List<T> list(T obj) {
        if (obj == null) return new ArrayList<>();

        List<T> l = new ArrayList<>();
        l.add(obj);

        return l;
    }

    public static <T> List<T> addAll(List<T> list, Collection<T> values) {
        if (CollectionUtils.isEmpty(values)) return list;

        list.addAll(values);
        return list;
    }

}
