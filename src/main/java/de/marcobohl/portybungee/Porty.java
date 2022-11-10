package de.marcobohl.portybungee;

import de.marcobohl.portybungee.api.PortyAPI;
import de.marcobohl.portybungee.api.TaskHandler;
import de.marcobohl.portybungee.api.TeleportRequestHandler;
import de.marcobohl.portybungee.cmds.*;
import de.marcobohl.portybungee.internal.IPortyAPI;
import de.marcobohl.portybungee.internal.ITaskHandler;
import de.marcobohl.portybungee.internal.ITeleportRequestHandler;
import de.marcobohl.portybungee.internal.TeleportTimer;
import de.marcobohl.portybungee.internal.io.IOStatics;
import de.marcobohl.portybungee.internal.io.InputHandler;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class Porty extends Plugin {

    private static Porty instance;

    public static Porty getInstance() {
        return instance;
    }

    public static PortyAPI getApi() {
        return getInstance().api;
    }

    private PortyAPI api;
    private TeleportRequestHandler tpaHandler;
    private PortyConfiguration config;
    private TaskHandler taskHandler;
    private TeleportTimer timer;

    public TeleportRequestHandler getTpaHandler() {
        return tpaHandler;
    }

    public PortyConfiguration getConfig() {
        return config;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }

    public TeleportTimer getTimer() {
        return timer;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.config = new PortyConfiguration();

        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        File configFile = new File(dataFolder, "config.yml");
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File messagesFile = new File(dataFolder, "messages.yml");
        try {
            Messages.load(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.api = new IPortyAPI();
        this.tpaHandler = (TeleportRequestHandler) new ITeleportRequestHandler();
        this.taskHandler = new ITaskHandler();
        this.timer = new TeleportTimer();

        getProxy().registerChannel(IOStatics.CHANNEL);

        new InputHandler();

        registerCommands();
    }

    public void reload() {
        getConfig().reload();
        Messages.reload();
        PluginManager pm = getProxy().getPluginManager();
        pm.unregisterCommands(this);
        registerCommands();
    }

    private void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new TeleportCommand());
        pm.registerCommand(this, new TpHereCommand());
        pm.registerCommand(this, new TpAllCommand());
        pm.registerCommand(this, new TpaCommand());
        pm.registerCommand(this, new TpaHereCommand());
        pm.registerCommand(this, new TpDenyCommand());
        pm.registerCommand(this, new TpAcceptCommand());
        pm.registerCommand(this, new PortyReloadCommand());
    }
}
