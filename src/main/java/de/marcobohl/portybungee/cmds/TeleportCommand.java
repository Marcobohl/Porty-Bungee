package de.marcobohl.portybungee.cmds;

import de.marcobohl.portybungee.Messages;
import de.marcobohl.portybungee.Porty;
import de.marcobohl.portybungee.PortyUtil;
import de.marcobohl.portybungee.api.Callback;
import de.marcobohl.portybungee.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportCommand extends BasePortyCommand {

    public TeleportCommand() {
        super("teleport", "porty.cmd.teleport", new String[]{"tp", "tp2p"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("teleport_help");
    }

    @Override
    public void executeCommand(final CommandSender sender, String[] args) {
        if (args.length == 1) { // /tp <target>
            if (!(sender instanceof ProxiedPlayer)) {
                sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
                return;
            }

            ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

            String targetName = args[0];
            ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

            if (targetPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            Callback callback = Porty.getApi().teleport(fromPlayer, targetPlayer);
            callback.setRunnable(new CallbackRunnable() {

                @Override
                public void success() {
                    sendMessage(sender, Messages.getMessage("teleport_success", "Teleported."));
                }

                @Override
                public void error(String errmsg) {
                    sendMessages(sender, PortyUtil.applyTag("<errmsg>", errmsg, Messages.getMessage("teleport_fail", "&7The teleport failed: <errmsg>")));
                }
            });
        } else if (args.length == 2) { // /tp <player> <target>
            String fromName = args[0];
            final ProxiedPlayer fromPlayer = ProxyServer.getInstance().getPlayer(fromName);

            if (fromPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", fromName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            String targetName = args[0];
            final ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(fromName);

            if (targetPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            Callback callback = Porty.getApi().teleport(fromPlayer, targetPlayer);
            callback.setRunnable(new CallbackRunnable() {

                @Override
                public void success() {
                    sendMessage(sender, Messages.getMessage("teleport_success", "&7Teleported."));
                }

                @Override
                public void error(String errmsg) {
                    sendMessages(sender, PortyUtil.applyTag("<errmsg>", errmsg, Messages.getMessage("teleport_fail", "&7The teleport failed: <errmsg>")));
                }
            });
        } else {
            sendWrongUsage(sender);
        }
    }
}
