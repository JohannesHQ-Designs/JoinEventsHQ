package com.nopermission.joineventshq.configs;

import com.nopermission.joineventshq.JoinEventsHQ;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MessagesConfig {

    private final JoinEventsHQ joinEventsHQ;
    private YamlDocument config;

    public MessagesConfig(JoinEventsHQ joinEventsHQ) {
        this.joinEventsHQ = joinEventsHQ;
    }

    public List<String> helpMessage() {
        return config.getStringList("messages.helpCommand");
    }

    public String noSpawnsSet() {
        return config.getString("messages.spawnListEmpty");
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

    public MessagesConfig init() {
        File dataFolder = new File(joinEventsHQ.getDataFolder(), "messages.yml");
        InputStream resource = joinEventsHQ.getResource("messages.yml");
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
