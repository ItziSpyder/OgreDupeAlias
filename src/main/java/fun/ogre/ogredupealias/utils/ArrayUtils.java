package fun.ogre.ogredupealias.utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class ArrayUtils {

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

    public static class Constants {
        public static final List<String> MATERIAL_NAMES = toNewList(Arrays.stream(Material.values()).toList(),m -> m.name().toLowerCase());
        public static final List<String> ENTITY_NAMES = toNewList(Arrays.stream(EntityType.values()).toList(),e -> e.name().toLowerCase());
    }

    public static <T> String joinFrom(int begin, T[] items, Function<T,String> function ) {
        List<String> list = Arrays.stream(items).filter(Objects::nonNull).map(function).toList();
        return String.join(" ", list.subList(begin,list.size()-1));
    }
}
