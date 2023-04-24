package com.nopermission.joineventshq.utils;

public enum Messages {
    PREFIX("prefix", "&8[&2Join&eEvents&c&lHQ&8] <reset>"),
    HELP("help", "<reset><newline>&m-------------" +
            "<newline>&e&lJoinEventsHQ &fHelp Menu" +
            "<newline>&b/je &ffor help" +
            "<newline>&b/je reload &freload the configs" +
            "<newline>&b/je spawns &flist all spawns." +
            "<newline>&b/je addspawn <name> &fadd a spawn." +
            "<newline>&b/je removespawn <name> &fremove a spawn." +
            "<newline>&m-------------" +
            "<newline><reset>"),
    SPAWN_COMMAND("commands.spawn", "<prefix><white>Teleported you back to spawn!"),
    ADMIN_SPAWN_NOT_FOUND("commands.admin.spawn_not_found", "<prefix><red>No spawns has been found!"),
    ADMIN_SPAWN_ALREADY_FOUND("commands.admin.spawn_already_found", "<prefix><red>This spawn already exists!"),
    ADMIN_SPAWN_ADDED("commands.admin.spawn_added", "<prefix><green>Added the spawn: <aqua><name>"),
    ADMIN_SPAWN_TARGET_NOT_FOUND("commands.admin.spawn_target_not_found", "<prefix><red>There is no spawn with the name: <name>!"),
    ADMIN_SPAWN_DELETED("commands.admin.spawn_deleted", "<prefix><green>Deleted the spawn: <aqua><name>"),
    RELOAD_COMMAND("commands.admin.reload", "<prefix>&aReloaded the plugin files and messages!");
    private final String key;
    private final String message;

    Messages(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
