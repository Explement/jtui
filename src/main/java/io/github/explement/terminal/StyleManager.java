package io.github.explement.terminal;

public class StyleManager {
    public String applyStyle(String text, Style... styles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Style style : styles) {
            if (style != null) stringBuilder.append(style.getCode());
        }

        stringBuilder.append(text);

        stringBuilder.append(Style.RESET.getCode());
        return stringBuilder.toString();
    }

}
