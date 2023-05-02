package io.github.itzispyder.ogredupealias.utils;

import io.github.itzispyder.ogredupealias.data.Config;

public class Text {

    public static String of(String s) {
        return s;
    }

    public static String ofAll(String s) {
        return builder(s).prefix().color().build();
    }

    /**
     * Replaces all & with § to color the text
     * @param s string
     * @return result
     */
    public static String color(String s) {
        return s.replaceAll("&","§");
    }

    public static String asPath(String s) {
        return s.toLowerCase()
                .replaceAll(" ","_")
                .replaceAll("[^.a-b0-9_-]","")
                .trim();
    }

    public static String asDirectory(String s) {
        return s.toLowerCase()
                .replaceAll(" ","_")
                .replaceAll("[^./a-b0-9_-]","")
                .trim();
    }

    public static String prefixed(String s) {
        return Config.Plugin.prefix() + s;
    }

    public static TextBuilder builder(String s) {
        return new TextBuilder(s);
    }

    public static TextBuilder builder() {
        return builder("");
    }

    public static class TextBuilder {

        private String s;

        public TextBuilder(String s) {
            this.s = s;
        }

        public TextBuilder message(String s) {
            this.s = s;
            return this;
        }

        public TextBuilder color() {
            s = Text.color(s);
            return this;
        }

        public TextBuilder prefix() {
            s = Text.prefixed(s);
            return this;
        }

        public TextBuilder asPath() {
            s = Text.asPath(s);
            return this;
        }

        public TextBuilder asDirectory() {
            s = Text.asDirectory(s);
            return this;
        }

        public String build() {
            return s;
        }
    }
}
