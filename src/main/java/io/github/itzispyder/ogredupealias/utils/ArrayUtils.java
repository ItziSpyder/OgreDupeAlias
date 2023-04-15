package io.github.itzispyder.ogredupealias.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class ArrayUtils {

    /**
     * Transforms an array to another one
     * @param e iterable list
     * @param a action
     * @return new transformed list
     * @param <I> input
     * @param <O> output
     */
    public static <I,O> List<O> toNewList(Iterable<I> e, Function<I,O> a) {
        List<O> list = new ArrayList<>();
        e.forEach(i -> list.add(a.apply(i)));
        return list;
    }

    public static <T> String list2string(List<T> list) {
        return Text.color("&7[&e" + String.join("&7, &e", ArrayUtils.toNewList(list, Object::toString)) + "&7]");
    }

    public static <T> List<T> bind(Iterable<T> tList, T... ts) {
        List<T> list = Arrays.asList(ts);
        tList.forEach(list::add);
        return list;
    }
}
