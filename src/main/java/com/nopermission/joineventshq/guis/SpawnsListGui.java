package com.nopermission.joineventshq.guis;

import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.spawn.models.Spawn;
import com.nopermission.joineventshq.utils.Text;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SpawnsListGui {

    public SpawnsListGui(Player player) {
        Gui gui = Gui.gui().rows(4).title(Text.formatComponent("Spawns List")).create();

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.getFiller().fillBorder(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.empty()).asGuiItem());

        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();

        if (spawnHashMap.isEmpty()) {
            gui.setItem(2, 4,
                    ItemBuilder.from(Material.BARRIER)
                            .name(Text.formatComponent("&c&lNo spawn found!"))
                            .lore(Text.formatComponent("&7Use &b/je addspawn <spawnName> &7to add a new spawn!"))
                            .asGuiItem());
        }

        spawnHashMap.forEach((s, spawn) -> {
            gui.addItem(ItemBuilder.from(Material.ENDER_PEARL)
                    .name(Text.formatComponent("&a&l" + s))
                    .lore(
                            Component.empty(),
                            Text.formatComponent("&7Location: &e" + spawn.getLocation()),
                            Text.formatComponent("&7World Exist: " + (spawn.worldExists() ? "&aYes" : "&cNo")),
                            Component.empty(),
                            Text.formatComponent("&aLeft-Click&7 to teleport to location"),
                            Text.formatComponent("&aRight-Click&7 to delete spawn")
                    )
                    .asGuiItem(event -> {
                        if (event.isRightClick()) {
                            player.performCommand("je removespawn" + s);
                        } else if (event.isLeftClick()) {
                            if (spawn.worldExists())
                                player.teleport(spawn.getLocation());
                            else player.sendMessage(Text.formatComponent("&cWorld does not exist!"));
                        }
                    }));
        });

        gui.open(player);
    }
}
