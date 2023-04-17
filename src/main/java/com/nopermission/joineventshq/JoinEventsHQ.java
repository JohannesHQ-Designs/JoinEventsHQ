package com.nopermission.joineventshq;

import com.nopermission.joineventshq.commands.JoinEventsHQCommand;
import com.nopermission.joineventshq.commands.SpawnCommand;
import com.nopermission.joineventshq.configs.Configuration;
import com.nopermission.joineventshq.listeners.FirstJoinListener;
import com.nopermission.joineventshq.listeners.PlayerJoinListener;
import com.nopermission.joineventshq.listeners.PlayerQuitListener;
import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.utils.Text;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEventsHQ extends JavaPlugin {

    private static JoinEventsHQ plugin;
    private Configuration configuration;
    private ComponentLogger componentLogger;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;

        componentLogger = ComponentLogger.logger(this.getName());

        initConfigs();
        initManagers();

        registerCommands(
                new SpawnCommand(),
                new JoinEventsHQCommand()
        );
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void initConfigs() {
        configuration = new Configuration(this).init();
    }

    private void initManagers() {
        commandManager = new CommandManager(this, true);
        registerListener(new PlayerJoinListener(this));
        registerListener(new SpawnManager(this));
        registerListener(new PlayerJoinListener(this));
        registerListener(new FirstJoinListener());
        registerListener(new PlayerQuitListener());
    }

    public void reload() {
        configuration.reload();
        SpawnManager.get().reload();
    }

    public void registerCommands(CommandBase... commandBases) {
        commandManager.register(commandBases);
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static JoinEventsHQ get() {
        return plugin;
    }

    public void log(String message) {
        componentLogger.info(Text.formatComponent(message));
    }

    public boolean hasPlugin(String plugin) {
        return getServer().getPluginManager().getPlugin(plugin) != null;
    }
}