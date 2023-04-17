package com.nopermission.joineventshq.utils;


import com.google.common.collect.ImmutableMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Text {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    /*private static final MiniMessage miniMessage = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.rainbow())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.decorations())
                    .resolver(StandardTags.reset())
                    .resolver(StandardTags.newline())
                    .build())
            .build();*/

    private static final Map<String, String> legacyColors = new ImmutableMap.Builder<String, String>()
            .put("\u00A70", "<reset><black><!italic>")
            .put("\u00A71", "<reset><dark_blue><!italic>")
            .put("\u00A72", "<reset><dark_green><!italic>")
            .put("\u00A73", "<reset><dark_aqua><!italic>")
            .put("\u00A74", "<reset><dark_red><!italic>")
            .put("\u00A75", "<reset><dark_purple><!italic>")
            .put("\u00A76", "<reset><gold><!italic>")
            .put("\u00A77", "<reset><gray><!italic>")
            .put("\u00A78", "<reset><dark_gray><!italic>")
            .put("\u00A79", "<reset><blue><!italic>")
            .put("\u00A7a", "<reset><green><!italic>")
            .put("\u00A7b", "<reset><aqua><!italic>")
            .put("\u00A7c", "<reset><red><!italic>")
            .put("\u00A7d", "<reset><light_purple><!italic>")
            .put("\u00A7e", "<reset><yellow><!italic>")
            .put("\u00A7f", "<reset><white><!italic>")
            .put("\u00A7r", "<reset><!italic>")
            .put("\u00A7l", "<bold>")
            .put("\u00A7k", "<obfuscated>")
            .put("\u00A7o", "<italic>")
            .put("\u00A7m", "<strikethrough>")
            .put("\u00A7n", "<underline>")
            .build();

    public static Component formatComponent(String message) {
        message = color(message);

        for (Map.Entry<String, String> entry : legacyColors.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        return miniMessage.deserialize(message);
    }

    /**
     * Color the provided {@link Collection<String>} using {@link ChatColor}
     *
     * @param lines the lines to color
     * @return the colored lines
     */
    public static List<String> colorLines(Collection<String> lines) {
        List<String> newLines = new ArrayList<>();
        for (String line : lines)
            newLines.add(color(line));
        return newLines;
    }

    /**
     * Color the provided message using {@link ChatColor}
     *
     * @param message the message to color
     * @return the colored message
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("§", "&"));
    }

    /**
     * Interpolates two colors into a new color.
     * @param hexStart the start color
     * @param hexStop the stop color
     * @param text the text to interpolate
     * @return the interpolated color in {@link BaseComponent}
     */
    public static BaseComponent[] gradient(String hexStart, String hexStop, String text) {
        net.md_5.bungee.api.ChatColor start = net.md_5.bungee.api.ChatColor.of(hexStart);
        net.md_5.bungee.api.ChatColor stop = net.md_5.bungee.api.ChatColor.of(hexStop);

        ComponentBuilder builder = new ComponentBuilder();
        for (double i = 0; i < text.toCharArray().length; i++) {
            double percentage = ((i + 1) / text.toCharArray().length);
            builder.append(String.valueOf(text.charAt((int) i))).color(net.md_5.bungee.api.ChatColor.of(introp(start, stop, percentage)));
        }

        return builder.create();
    }

    /**
     * Interpolates two colors into a new color.
     * This is the mathematical way to create gradients.
     * See:
     * <a href="https://en.wikipedia.org/wiki/Linear_interpolation">Wikipedia - Linear Interpolation</a>
     * @param start the start color
     * @param stop the stop color
     * @param pos the position of the interpolation
     * @return the interpolated color in hex (e.g. #ff0000)
     */
    public static String introp(net.md_5.bungee.api.ChatColor start, net.md_5.bungee.api.ChatColor stop, double pos) {
        double r = start.getColor().getRed() * pos + stop.getColor().getRed() * (1 - pos);
        double g = start.getColor().getGreen() * pos + stop.getColor().getGreen() * (1 - pos);
        double b = start.getColor().getBlue() * pos + stop.getColor().getBlue() * (1 - pos);

        return String.format("#%02x%02x%02x", (int) r, (int) g, (int) b);
    }
}
