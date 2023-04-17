package com.nopermission.joineventshq.commands;

import com.nopermission.joineventshq.spawn.SpawnManager;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("spawn") @Alias("worldspawn")
public class SpawnCommand extends CommandBase {

    @Default
    public void spawnCommand(Player player) {
        player.teleport(SpawnManager.get().nextSpawn());
    }
}
