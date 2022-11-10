package de.marcobohl.portybungee.cmds;

import de.marcobohl.portybungee.Messages;
import de.marcobohl.portybungee.Porty;
import de.marcobohl.portybungee.PortyUtil;
import de.marcobohl.portybungee.api.Callback;
import de.marcobohl.portybungee.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpHereCommand extends BasePortyCommand {

    public TpHereCommand() {
        super("tphere", "porty.cmd.tphere", new String[]{"s", "tp2me"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tphere_help");
    }

    @Override
    public void executeCommand(final CommandSender sender, String[] args) {
        if (args.length == 1) { // /tphere <player>
            if (!(sender instanceof ProxiedPlayer)) {
                sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblock"));
                return;
            }

            String fromName = args[0];
            ProxiedPlayer fromPlayer = ProxyServer.getInstance().getPlayer(fromName);

            if (fromPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", fromName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            ProxiedPlayer targetPlayer = (ProxiedPlayer) sender;

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
