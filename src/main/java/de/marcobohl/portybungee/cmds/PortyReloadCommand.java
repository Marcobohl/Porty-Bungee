package de.marcobohl.portybungee.cmds;

import de.marcobohl.portybungee.Messages;
import de.marcobohl.portybungee.Porty;
import net.md_5.bungee.api.CommandSender;

public class PortyReloadCommand extends BasePortyCommand {

    public PortyReloadCommand() {
        super("portyreload", "porty.cmd.reload", new String[]{"preload", "porty-reload"});
    }

    @Override
    public String[] getHelpText() {
        return new String[]{Messages.getMessage("reload_help", "&7Reloads the configuration")};
    }

    @Override
    public void executeCommand(final CommandSender sender, String[] args) {
        if (args.length == 0) {
            Porty.getInstance().reload();

            sendMessage(sender, Messages.getMessage("reload_done", "&7The configuration has been reloaded"));
        } else {
            sendWrongUsage(sender);
        }
    }
}
