package com.nopermission.joineventshq.commands;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.spawn.models.Spawn;
import com.nopermission.joineventshq.utils.Text;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Command("joineventshq") @Alias({"joinevents", "je"})
public class JoinEventsHQCommand extends CommandBase {

    @Default @Permission("joineventsprohq.admin")
    public void defaultCommand(CommandSender commandSender) {
        List<String> strings = JoinEventsHQ.get().getMessage().helpMessage();
        if (strings.isEmpty())
            return;

        for (String string : strings) {
            commandSender.sendMessage(Text.formatComponent(string));
        }
    }

    @SubCommand("reload") @Permission("joineventsprohq.admin")
    public void reloadCommand(CommandSender commandSender) {
        JoinEventsHQ.get().reload();
        commandSender.sendMessage(Text.formatComponent("&aReloaded the plugin files and messages!"));
    }

    @SubCommand("spawns") @Permission("joineventsprohq.admin")
    public void listSpawnsCommand(CommandSender commandSender) {
        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();
        if (spawnHashMap.isEmpty()) {
            commandSender.sendMessage(Text.formatComponent(JoinEventsHQ.get().getMessage().noSpawnsSet()));
            return;
        }

        spawnHashMap.forEach((s, spawn) -> {
            commandSender.sendMessage(Text.formatComponent("&eSpawn: &f" + s + " &7&o(loc: " + spawn.getLocation() + ")"));
        });
    }

    @SubCommand("addspawn") @Permission("joineventsprohq.admin")
    public void addSpawnsCommand(Player player, final String name) {
        if (name.isEmpty())
            return;

        SpawnManager spawnManager = SpawnManager.get();
        Optional<Spawn> optionalSpawn = spawnManager.getSpawn(name);
        if (optionalSpawn.isPresent()) {
            player.sendMessage(Text.formatComponent("&cThis spawn already exists!"));
            return;
        }

        spawnManager.addSpawn(name, player.getLocation());
        player.sendMessage(Text.formatComponent("&aAdded the spawn: " + name));

    }

    @SubCommand("remove") @Permission("joineventsprohq.admin")
    public void removeSpawnsCommand(Player player, final String name) {
        if (name.isEmpty())
            return;

        SpawnManager spawnManager = SpawnManager.get();
        Optional<Spawn> optionalSpawn = spawnManager.getSpawn(name);
        if (optionalSpawn.isEmpty()) {
            player.sendMessage(Text.formatComponent("&cThere is no spawn with this name!"));
            return;
        }

        spawnManager.delSpawn(name);
        player.sendMessage(Text.formatComponent("&cDeleted the spawn: " + name));
    }
}
