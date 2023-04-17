package com.nopermission.joineventshq.configs;

import com.nopermission.joineventshq.JoinEventsHQ;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SpawnConfig {

    private final JoinEventsHQ joinEventsHQ;
    private YamlDocument config;

    public SpawnConfig(JoinEventsHQ joinEventsHQ) {
        this.joinEventsHQ = joinEventsHQ;
    }

    public boolean spawnOnRespawn() {
        return config.getBoolean("options.spawnOnRespawn");
    }

    public boolean spawnOnJoin() {
        return config.getBoolean("options.spawnOnJoin");
    }

    public boolean spawnOnVoid() {
        return config.getBoolean("options.spawnOnVoid");
    }


    public void reload() {
        try {
            config.reload();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void save() {
        try {
            config.save();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public SpawnConfig init() {
        File dataFolder = new File(joinEventsHQ.getDataFolder(), "spawn.yml");
        InputStream resource = joinEventsHQ.getResource("spawn.yml");
        try {
            if (resource != null) {
                config = YamlDocument.create(dataFolder, resource);
            } else {
                config = YamlDocument.create(dataFolder);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        return this;
    }

    public YamlDocument getConfig() {
        return config;
    }

}
