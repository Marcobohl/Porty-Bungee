package de.marcobohl.portybungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.util.CaseInsensitiveMap;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PortyConfiguration {
    private int globalTeleportTimer;
    private int timeout;
    private String commandPrefix;
    private Map<String, Integer> serverTeleportTimer;

    private File configFile;

    public void reload() {
        try {
            load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(File file) throws IOException {
        this.configFile = file;

        if (!file.exists()) {
            saveDefaultValues(file);
        }

        Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        Configuration timer = cfg.getSection("timer");
        globalTeleportTimer = timer.getInt("global", 0);
        serverTeleportTimer = new CaseInsensitiveMap<>();
        for (ServerInfo si : ProxyServer.getInstance().getServers().values()) {
            serverTeleportTimer.put(si.getName(), timer.getInt("server." + si.getName(), -1));
        }

        timeout = cfg.getInt("timeout", 5);
        commandPrefix = cfg.getString("commandprefix", "");
    }

    public void saveDefaultValues(File file) throws IOException {
        file.createNewFile();

        PortyUtil.writeDefaultFile("config", file);
    }

    public Map<String, Integer> getServerTeleportTimer() {
        return serverTeleportTimer;
    }

    public int getGlobalTeleportTimer() {
        return globalTeleportTimer;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }
}
