package de.biosphere.bix.commands.owner;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class StopCommand extends Command {

    public StopCommand() {
        super("shutdown", "Stop the bot", Categorie.OWNER);
    }

    @Override
    public void execute(String[] args, Message message) {
        message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Shutting down...").build()).complete();
        for (JDA jda : Bix.getInstance().getShardList()) {
            jda.shutdown();
        }
        System.exit(0);
    }
}
