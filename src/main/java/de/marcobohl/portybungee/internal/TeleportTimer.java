package de.marcobohl.portybungee.internal;

import de.marcobohl.portybungee.Porty;
import de.marcobohl.portybungee.PortyConfiguration;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportTimer {

    public int getTimeToWait(ProxiedPlayer player) {
        String server = player.getServer().getInfo().getName();
        if (player.hasPermission("porty.notimer") || player.hasPermission("porty.notimer." + server)) {
            return 0;
        }
        PortyConfiguration cfg = Porty.getInstance().getConfig();
        int ret = cfg.getGlobalTeleportTimer();
        int srv = cfg.getServerTeleportTimer().get(server);
        if (srv != -1) {
            ret = srv;
        }
        return ret;
    }
}
