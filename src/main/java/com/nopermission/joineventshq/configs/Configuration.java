package com.nopermission.joineventshq.configs;

import com.nopermission.joineventshq.JoinEventsHQ;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Configuration {

    private final JoinEventsHQ joinEventsHQ;
    private YamlDocument config;

    public Configuration(JoinEventsHQ joinEventsHQ) {
        this.joinEventsHQ = joinEventsHQ;
    }

    public boolean isTitleEnabled() {
        return config.getBoolean("join.title.enabled");
    }

    public boolean flyOnJoinEnabled() {
        return config.getBoolean("join.flyOnJoin");
    }

    public String flyOnJoinPermission() {
        return config.getString("join.flyOnJoinPermission");
    }

    public String getJoinMessage() {
        return config.getString("join.joinMessage");
    }

    public String getQuitMessage() {
        return config.getString("quit.quitMessage");
    }

    public String getJoinedTitle() {
        return config.getString("join.title.title");
    }

    public String getJoinedSubTitle() {
        return config.getString("join.title.sub");
    }

    public int motdDelay() {
        return config.getInt("join.motd.delay");
    }

    public boolean motdEnabled() {
        return config.getBoolean("join.motd.enabled");
    }

    public List<String> getJoinMotd() {
        return config.getStringList("join.motd.message");
    }

    public List<String> getJoinConsoleCommands() {
        return config.getStringList("join.consoleCommands");
    }

    public String getJoinSound() {
        return config.getString("join.motd.sound");
    }

    public List<String> getJoinCommands() {
        return config.getStringList("join.playerCommands");
    }

    public String getFirstJoinMessage() {
        return config.getString("firstJoin.broadcast");
    }

    public String getFirstJoinSound() {
        return config.getString("firstJoin.sound");
    }

    public List<String> getFirstJoinConsoleCommands() {
        return config.getStringList("firstJoin.consoleCommands");
    }

    public List<String> getFirstJoinCommands() {
        return config.getStringList("firstJoin.playerCommands");
    }

    public void reload() {
        try {
            config.reload();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public Configuration init() {
        File dataFolder = new File(joinEventsHQ.getDataFolder(), "configuration.yml");
        InputStream resource = joinEventsHQ.getResource("configuration.yml");
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
