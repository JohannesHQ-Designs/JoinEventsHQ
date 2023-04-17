package com.nopermission.joineventshq.spawn;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.configs.SpawnConfig;
import com.nopermission.joineventshq.spawn.models.Spawn;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpawnManager implements Listener {

    private static SpawnManager spawnManager;
    private final JoinEventsHQ plugin;
    private final HashMap<String, Spawn> spawnHashMap = new HashMap<>();
    private SpawnConfig spawnConfig;
    public SpawnManager(JoinEventsHQ joinEventsHQ) {
        spawnManager = this;
        plugin = joinEventsHQ;
        spawnConfig = new SpawnConfig(joinEventsHQ).init();
        loadSpawns();
    }

    public void reload() {
        spawnConfig.reload();
        loadSpawns();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!spawnConfig.spawnOnRespawn())
            return;

        Location location = nextSpawn().clone();
        if (event.getPlayer().getAllowFlight())
            location.add(0, 1, 0);

        event.setRespawnLocation(location);
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        if (!spawnConfig.spawnOnJoin())
            return;

        Location location = nextSpawn().clone();
        if (event.getPlayer().getAllowFlight())
            location.add(0, 1, 0);

        event.setSpawnLocation(location);
    }

    @EventHandler
    public void onMoveSpawn(PlayerMoveEvent event) {
        if (!spawnConfig.spawnOnVoid())
            return;

        if (event.getTo().getBlockY() <= 0)
            event.getPlayer().teleport(nextSpawn().clone());
    }

    public void loadSpawns() {
        spawnHashMap.clear();
        Section spawns = spawnConfig.getConfig().getSection("spawns");
        if (spawns != null) {
            for (Object key : spawns.getKeys()) {
                plugin.log("Loading spawn: " + key);
                String worldname = spawnConfig.getConfig().getString("spawns." + key + ".world");
                double spawnx = spawnConfig.getConfig().getDouble("spawns." + key + ".x");
                double spawny = spawnConfig.getConfig().getDouble("spawns." + key + ".y");
                double spawnz = spawnConfig.getConfig().getDouble("spawns." + key + ".z");
                float spawnyaw = spawnConfig.getConfig().getInt("spawns." + key + ".yaw");
                float spawnpitch = spawnConfig.getConfig().getInt("spawns." + key + ".pitch");

                plugin.log("Loaded spawn: " + key);
                spawnHashMap.put(key.toString(), new Spawn(key.toString(), worldname, spawnx, spawny, spawnz, spawnyaw, spawnpitch));
            }
            plugin.log("Loaded " + spawnHashMap.size() + " spawns!");
        }
    }

    public Location nextSpawn() {
        List<Spawn> valuesList = new ArrayList<>(spawnHashMap.values());
        int randomIndex = new Random().nextInt(valuesList.size());
        Spawn randomValue = valuesList.get(randomIndex);
        return randomValue.getLocation();
    }

    public HashMap<String, Spawn> getSpawnHashMap() {
        return spawnHashMap;
    }

    public SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }

    public static SpawnManager get() {
        return spawnManager;
    }
}
