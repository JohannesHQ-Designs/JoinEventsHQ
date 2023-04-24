package com.nopermission.joineventshq.commands;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.spawn.models.Spawn;
import com.nopermission.joineventshq.utils.Messages;
import com.nopermission.joineventshq.utils.Text;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

@Command("joineventshq") @Alias({"joinevents", "je"})
public class JoinEventsHQCommand extends CommandBase {

    @Default @Permission("joineventsprohq.admin")
    public void defaultCommand(CommandSender commandSender) {
        JoinEventsHQ.sendMessage(commandSender, Messages.HELP);
    }

    @SubCommand("reload") @Permission("joineventsprohq.admin")
    public void reloadCommand(CommandSender commandSender) {
        JoinEventsHQ.get().reload();
        JoinEventsHQ.sendMessage(commandSender, Messages.RELOAD_COMMAND);
    }

    @SubCommand("spawns") @Permission("joineventsprohq.admin")
    public void listSpawnsCommand(CommandSender commandSender) {
        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();
        if (spawnHashMap.isEmpty()) {
            JoinEventsHQ.sendMessage(commandSender, Messages.ADMIN_SPAWN_NOT_FOUND);
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
            JoinEventsHQ.sendMessage(player, Messages.ADMIN_SPAWN_ALREADY_FOUND);
            return;
        }

        spawnManager.addSpawn(name, player.getLocation());
        JoinEventsHQ.getMessage(Messages.ADMIN_SPAWN_ADDED).ifPresent(s -> player.sendMessage(Text.formatComponent(s.replace("<name>", name))));
    }

    @SubCommand("remove") @Permission("joineventsprohq.admin")
    public void removeSpawnsCommand(Player player, final String name) {
        if (name.isEmpty())
            return;

        SpawnManager spawnManager = SpawnManager.get();
        Optional<Spawn> optionalSpawn = spawnManager.getSpawn(name);
        if (optionalSpawn.isEmpty()) {
            JoinEventsHQ.getMessage(Messages.ADMIN_SPAWN_TARGET_NOT_FOUND).ifPresent(s -> player.sendMessage(Text.formatComponent(s.replace("<name>", name))));
            return;
        }

        spawnManager.delSpawn(name);
        JoinEventsHQ.getMessage(Messages.ADMIN_SPAWN_DELETED).ifPresent(s -> player.sendMessage(Text.formatComponent(s.replace("<name>", name))));
    }
}
