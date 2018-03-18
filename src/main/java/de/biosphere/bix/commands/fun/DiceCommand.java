package de.biosphere.bix.commands.fun;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class DiceCommand extends Command {

    public DiceCommand() {
        super("dice", "Roll a dice", Categorie.FUN);
    }

    @Override
    public void execute(String[] args, Message message) {
        message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You rolled a " + ((int) (1 + Math.random() * (6 - 1 + 1)))).build()).queue();
    }
}
