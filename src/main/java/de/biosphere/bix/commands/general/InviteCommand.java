package de.biosphere.bix.commands.general;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class InviteCommand extends Command {

    public InviteCommand() {
        super("invite", "Send a invite link for the bot", Categorie.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        message.getTextChannel().sendMessage("https://discordapp.com/oauth2/authorize?client_id=361027969570832384&scope=bot&permissions=1031007319&response_type=code&redirect_uri=https://discord.gg/3By2ZPC").queue();
    }
}
