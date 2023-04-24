package com.nopermission.joineventshq.configs;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.utils.Messages;
import com.nopermission.joineventshq.utils.Text;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.kyori.adventure.text.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessagesConfig {

    private final JoinEventsHQ plugin;
    private YamlDocument config;

    public MessagesConfig(JoinEventsHQ joinEventsHQ) {
        this.plugin = joinEventsHQ;
    }

    private final Map<Messages, String> messages = new HashMap<>();

    public Optional<Component> getComponentMessage(Messages messages) {
        String msg = this.messages.get(messages);
        if (msg == null)
            return Optional.empty();
        if (msg.contains("<prefix>"))
            msg = msg.replace("<prefix>", getPrefix());
        return Optional.of(Text.formatComponent(msg));
    }

    public Optional<String> getMessage(Messages messages) {
        String msg = this.messages.get(messages);
        if (msg == null)
            return Optional.empty();
        if (msg.contains("<prefix>"))
            msg = msg.replace("<prefix>", getPrefix());
        return Optional.of(msg);
    }

    public void loadMessages() {
        Section configSection = config.getSection("messages");
        if (configSection == null || configSection.getKeys().size() == 0) {
            for (Messages value : Messages.values()) {
                insertMessage(value);
            }
        }

        for (Messages value : Messages.values()) {
            String key = ("messages." + value.getKey());
            String msg;
            if (config.contains(key)) {
                msg = config.getString(key);
            } else {
                insertMessage(value);
                msg = value.getMessage();
            }

            messages.put(value, msg);
        }
        plugin.log("&aLoaded " + messages.size() + " messages!");
    }

    public void insertMessage(Messages messages) {
        try {
            plugin.log("Inserting default message: &e" + messages.getKey());
            config.set("messages." + messages.getKey(), messages.getMessage());
            config.save();
        } catch (IOException e) {
            //DO nothing
        }
    }

    public String getPrefix() {
        Messages prefix = Messages.PREFIX;
        String s = messages.get(prefix);
        if (s == null || s.isBlank())
            return prefix.getMessage();
        return s;
    }

    public void reload() {
        try {
            messages.clear();
            config.reload();
            loadMessages();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public MessagesConfig init() {
        File dataFolder = new File(plugin.getDataFolder(), "messages.yml");
        InputStream resource = plugin.getResource("messages.yml");
        try {
            if (resource != null) {
                config = YamlDocument.create(dataFolder, resource);
            } else {
                config = YamlDocument.create(dataFolder);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        loadMessages();
        return this;
    }

    public YamlDocument getConfig() {
        return config;
    }

}
