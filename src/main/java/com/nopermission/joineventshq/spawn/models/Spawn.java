package com.nopermission.joineventshq.spawn.models;

import com.nopermission.joineventshq.spawn.SpawnManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Spawn {
    private String spawnname;
    private String worldname;
    private Vector position;
    private float yaw;
    private float pitch;

    public Spawn(String spawnname, String worldname, Vector position, float yaw, float pitch){
        this.spawnname = spawnname;
        this.worldname = worldname;
        this.position = position;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Spawn(String spawnname, String worldname, double x, double y, double z, float yaw, float pitch){
        this.spawnname = spawnname;
        this.worldname = worldname;
        this.position = new Vector(x,y,z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location getLocation(){
        World world = Bukkit.getWorld(worldname);
        return new Location(world, position.getX(), position.getY(), position.getZ(), yaw, pitch);
    }

    public void setLocation(Location location) {
        this.position = location.toVector();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public boolean worldExists(){
        World world = Bukkit.getWorld(worldname);
        return world != null;
    }

    public void delete() {
        YamlDocument config = SpawnManager.get().getSpawnConfig().getConfig();
        config.remove("spawns."+this.spawnname);
        SpawnManager.get().getSpawnConfig().save();
        SpawnManager.get().loadSpawns();
    }


    public void save() {
        YamlDocument config = SpawnManager.get().getSpawnConfig().getConfig();
        config.set("spawns."+this.spawnname+".world", this.worldname);
        config.set("spawns."+this.spawnname+".x", this.position.getX());
        config.set("spawns."+this.spawnname+".y", this.position.getY());
        config.set("spawns."+this.spawnname+".z", this.position.getZ());
        config.set("spawns."+this.spawnname+".yaw", this.yaw);
        config.set("spawns."+this.spawnname+".pitch", this.pitch);
        SpawnManager.get().getSpawnConfig().save();
    }
}