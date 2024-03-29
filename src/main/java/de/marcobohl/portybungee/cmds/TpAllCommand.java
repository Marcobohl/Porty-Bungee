package de.marcobohl.portybungee.cmds;

import de.marcobohl.portybungee.Messages;
import de.marcobohl.portybungee.Porty;
import de.marcobohl.portybungee.PortyUtil;
import de.marcobohl.portybungee.api.Callback;
import de.marcobohl.portybungee.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class TpAllCommand extends BasePortyCommand {

    public TpAllCommand() {
        super("tpall", "porty.cmd.tpall", new String[]{"teleportall"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tpall_help");
    }

    @Override
    public void executeCommand(final CommandSender sender, String[] args) {
        if (args.length == 0) { // /tpall
            if (!(sender instanceof ProxiedPlayer)) {
                sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
                return;
            }

            ProxiedPlayer targetPlayer = (ProxiedPlayer) sender;

            Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();

            for (final ProxiedPlayer player : allPlayers) {
                if (player.equals(targetPlayer)) {
                    continue;
                }
                Callback callback = Porty.getApi().teleport(player, targetPlayer);
                callback.setRunnable(new CallbackRunnable() {

                    @Override
                    public void success() {
                    }

                    @Override
                    public void error(String errmsg) {
                        sendMessages(sender, PortyUtil.applyTag("<errmsg>", errmsg, Messages.getMessage("teleport_fail", "&7The teleport failed: <errmsg>")));
                    }
                });
            }
            sendMessage(sender, Messages.getMessage("tpall_success_you", "&7Teleported all players to you"));

        } else if (args.length == 1) { // /tpall <target>
            String targetName = args[0];
            final ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

            if (targetPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();

            for (final ProxiedPlayer player : allPlayers) {
                if (player.equals(targetPlayer)) {
                    continue;
                }
                Callback callback = Porty.getApi().teleport(player, targetPlayer);
                callback.setRunnable(new CallbackRunnable() {

                    @Override
                    public void success() {
                    }

                    @Override
                    public void error(String errmsg) {
                        sendMessages(sender, PortyUtil.applyTag("<errmsg>", errmsg, Messages.getMessage("teleport_fail", "&7The teleport failed: <errmsg>")));
                    }
                });
            }
            sendMessages(sender, PortyUtil.applyTag("<player>", targetPlayer.getName(), Messages.getMessage("tpall_success", "&7Teleported all players to &e<player>&7")));
        } else {
            sendWrongUsage(sender);
        }
    }

}
