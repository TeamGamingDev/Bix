package de.biosphere.bix.commands.general;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class PingCommand extends Command{

    public PingCommand() {
        super("ping", "Show the ping of the bot", Categorie.GENERAL, "pong");
    }

    @Override
    public void execute(String[] args, Message message) {
        message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).appendDescription(":stopwatch: " + message.getJDA().getPing()).build()).queue();
    }
}
