package de.biosphere.bix.commands.general;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", "Show some information about the bot", Categorie.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        int guilds = 0;
        for (JDA jdas : Bix.getInstance().getShardList()) {
            guilds += jdas.getGuilds().size();
        }
        EmbedBuilder embedBuilder = getEmbed(message.getGuild(), message.getAuthor());
        embedBuilder.addField("Name", message.getJDA().getSelfUser().getName(), true);
        embedBuilder.addField("Developer", "'Niklas#0928", true);
        embedBuilder.addField("Library", "[JDA](https://github.com/DV8FromTheWorld/JDA)", true);
        embedBuilder.addField("Guilds", String.valueOf(guilds), true);
        message.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
