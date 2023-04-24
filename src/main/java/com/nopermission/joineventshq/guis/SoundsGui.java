package com.nopermission.joineventshq.guis;

import com.nopermission.joineventshq.utils.AtomicFloat;
import com.nopermission.joineventshq.utils.Text;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SoundsGui {

    public SoundsGui(Player player) {
        PaginatedGui paginatedGui = Gui.paginated().rows(6).title(Text.formatComponent("List of Available Sounds")).create();

        paginatedGui.setDefaultClickAction(event -> event.setCancelled(true));
        paginatedGui.setCloseGuiAction(event -> player.stopAllSounds());
        paginatedGui.getFiller().fillBorder(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.empty()).asGuiItem());

        AtomicFloat pitch = new AtomicFloat(1F);
        AtomicFloat volume = new AtomicFloat(1F);

        for (Sound value : Sound.values()) {
            paginatedGui.addItem(new GuiItem(
                    ItemBuilder.from(Material.JUKEBOX)
                    .name(Text.formatComponent("&7Sound: &e" + value.name()))
                            .lore(Text.formatComponent("&a&lClick &fto hear the sound!"))
                            .build(), event -> {
                player.playSound(player.getLocation(), value, volume.get(), pitch.get());
            }));
        }

        paginatedGui.setItem(6, 5, new GuiItem(
                ItemBuilder.from(Material.PLAYER_HEAD)
                        .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWIzMDUwNzc4M2MzN2RiM2EzMDkyY2FkMDQzZTU3OTUxYWE4YjRjNmVhOWFjYzQ3ZDYwNGI3ZWI1YWVhMDI4In19fQ==")
                        .name(Text.formatComponent("&4Close Menu"))
                        .lore(Text.formatComponent("&cClick to close menu"))
                .build(), event -> {
            paginatedGui.close(player);
        }));

        paginatedGui.setItem(6, 1, new GuiItem(
                ItemBuilder.from(Material.BARRIER)
                .name(Text.formatComponent("&cStop all sounds"))
                        .build(), event ->
                player.stopAllSounds()));

        ItemStack pitchItem = ItemBuilder.from(Material.TORCH)
                .name(Text.formatComponent("&6Pitch"))
                .lore(Text.formatComponent("&aLeft-Click to increase pitch"),
                        Text.formatComponent("&aRight-Click to decrease pitch"))
                .build();
        paginatedGui.setItem(6, 2, new GuiItem(pitchItem, event -> {
                    if (event.isLeftClick()) {
                        if (pitch.floatValue() >= 1F) {
                            player.sendMessage(Text.formatComponent("&cYour pitch is already the maximum!"));
                            return;
                        }

                        pitch.getAndSet(pitch.floatValue() + 0.1F);
                    } else if (event.isRightClick()) {
                        if (pitch.floatValue() <= 0F) {
                            player.sendMessage(Text.formatComponent("&cYour pitch is already the minimum!"));
                            return;
                        }
                        pitch.getAndSet(pitch.floatValue() - 0.1F);
                    }
                    player.sendMessage(Text.formatComponent("Changed pitch to: &b" + pitch.get()));
        }));

        paginatedGui.setItem(6, 3, new GuiItem(
                ItemBuilder.from(Material.TORCH)
                        .name(Text.formatComponent("&6Volume"))
                        .lore(Text.formatComponent("&aLeft-Click to increase volume"),
                                Text.formatComponent("&aRight-Click to decrease volume"))
                        .build(), event -> {

            if (event.isLeftClick()) {
                if (volume.floatValue() >= 1F) {
                    player.sendMessage(Text.formatComponent("&cYour volume is already the maximum!"));
                    return;
                }

                volume.getAndSet(volume.floatValue() + 0.1F);
            } else if (event.isRightClick()) {
                if (pitch.floatValue() <= 0F) {
                    player.sendMessage(Text.formatComponent("&cYour volume is off!"));
                    return;
                }
                volume.getAndSet(volume.floatValue() - 0.1F);
            }
            player.sendMessage(Text.formatComponent("Changed volume to: &b" + volume.get()));
        }));

        // Previous item
        paginatedGui.setItem(6, 4, ItemBuilder.from(Material.ARROW)
                .name(Text.formatComponent("Previous"))
                .asGuiItem(event -> {
            paginatedGui.previous();
            paginatedGui.updateTitle(Text.color("&7Page: " + paginatedGui.getCurrentPageNum() + " Total: " + paginatedGui.getPagesNum()));
        }));
        // Next item
        paginatedGui.setItem(6, 6, ItemBuilder.from(Material.SPECTRAL_ARROW)
                .name(Text.formatComponent("Next")).asGuiItem(event -> {
            paginatedGui.next();
            paginatedGui.updateTitle(Text.color("&7Page: " + paginatedGui.getCurrentPageNum() + " Total: " + paginatedGui.getPagesNum()));
        }));

        paginatedGui.open(player);
    }
}
