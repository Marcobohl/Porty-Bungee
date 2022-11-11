package de.marcobohl.portybungee.cmds;

import de.marcobohl.portybungee.Messages;
import de.marcobohl.portybungee.Porty;
import de.marcobohl.portybungee.PortyUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpaHereCommand extends BasePortyCommand {

    public TpaHereCommand() {
        super("tpahere", "porty.cmd.tpahere", new String[]{"tpaskhere"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tpahere_help");
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
            return;
        }

        if (args.length == 1) {
            ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

            String targetName = args[0];
            ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

            if (targetPlayer == fromPlayer) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("tphere_request_self", "&7You can't send your self a tphere request.")));
                return;
            }

            if (targetPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            Porty.getApi().getTeleportRequestHandler().addTpaHereRequest(fromPlayer, targetPlayer);
            sendMessage(fromPlayer, Messages.getMessage("tphere_request_sent", "Your request has been sent to &e<player>").replace("<player>", targetName));



            TextComponent tpaccept = new TextComponent(Messages.getMessage("tpaccept_click", "/tpaccept"));
            tpaccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
            tpaccept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Messages.getMessage("tpaccept_hover_message", "Click to accept")).create()));

            TextComponent tpdeny= new TextComponent(Messages.getMessage("tpdeny_click", "/tpdeny"));
            tpdeny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
            tpdeny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Messages.getMessage("tpdeny_hover_message", "Click to deny")).create()));

            TextComponent accaptmesage = new TextComponent(Messages.getMessage("tpahere", "&7The player &e<player> &7asks you to teleport to him.").replace("<player>", fromPlayer.getName()));

            // sendMessages(targetPlayer, PortyUtil.applyTag("<player>", fromPlayer.getName(), Messages.getMessage("tpahere", "&7The player &e<player> &7asks you to teleport to him. Use &e/tpaccept&7 or &e/tpdeny&7 in order to respond to it.")));

            targetPlayer.sendMessage();
            targetPlayer.sendMessage(new ComponentBuilder(Messages.getMessage("prefixMain", "&e[TP]")).append(" ").append(accaptmesage).create());
            targetPlayer.sendMessage(new ComponentBuilder(Messages.getMessage("tpahere_secontline", "Use")).append(" ").append(tpaccept).append(" ").append(Messages.getMessage("tpahere_secontstroke","or")).append(" ").append(tpdeny).append(" ").append(Messages.getMessage("tpahere_secontbehind", "in order to respond to it")).create());
            targetPlayer.sendMessage();



        } else {
            sendWrongUsage(sender);
        }
    }
}
