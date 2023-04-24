package com.nopermission.joineventshq.commands;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.utils.Messages;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("spawn") @Alias("worldspawn")
public class SpawnCommand extends CommandBase {

    @Default
    public void spawnCommand(Player player) {
        SpawnManager.get().nextSpawn().ifPresent(player::teleport);
        JoinEventsHQ.sendMessage(player, Messages.SPAWN_COMMAND);
    }
}
