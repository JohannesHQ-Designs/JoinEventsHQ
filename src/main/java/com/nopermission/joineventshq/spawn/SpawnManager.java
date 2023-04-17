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

import java.util.*;

public class SpawnManager implements Listener {

    private static SpawnManager spawnManager;
    private final JoinEventsHQ plugin;
    private final HashMap<String, Spawn> spawnHashMap = new HashMap<>();
    private final SpawnConfig spawnConfig;
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

    public void delSpawn(String name) {
        Optional<Spawn> spawn = getSpawn(name);

        if (spawn.isEmpty())
            return;

        spawn.get().deleteFromConfig();
        spawnHashMap.remove(name);
    }

    public void addSpawn(String name, Location location) {
        Optional<Spawn> optionalSpawn = getSpawn(name);

        if (optionalSpawn.isPresent())
            return;

        Spawn spawn = new Spawn(name, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        spawn.saveToConfig();
        spawnHashMap.put(name, spawn);
    }

    public Optional<Spawn> getSpawn(String name) {
        Optional<Map.Entry<String, Spawn>> optionalStringSpawnEntry = spawnHashMap.entrySet().stream().filter(stringSpawnEntry -> stringSpawnEntry.getKey().equalsIgnoreCase(name)).findFirst();
        return optionalStringSpawnEntry.map(Map.Entry::getValue);
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
                plugin.log("&cLoading spawn: " + key);
                String worldname = spawnConfig.getConfig().getString("spawns." + key + ".world");
                double spawnx = spawnConfig.getConfig().getDouble("spawns." + key + ".x");
                double spawny = spawnConfig.getConfig().getDouble("spawns." + key + ".y");
                double spawnz = spawnConfig.getConfig().getDouble("spawns." + key + ".z");
                float spawnyaw = spawnConfig.getConfig().getInt("spawns." + key + ".yaw");
                float spawnpitch = spawnConfig.getConfig().getInt("spawns." + key + ".pitch");

                plugin.log("&aLoaded spawn: " + key);
                spawnHashMap.put(key.toString(), new Spawn(key.toString(), worldname, spawnx, spawny, spawnz, spawnyaw, spawnpitch));
            }
            plugin.log("&aLoaded " + spawnHashMap.size() + " spawns!");
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
