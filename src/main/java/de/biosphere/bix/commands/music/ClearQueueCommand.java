package de.biosphere.bix.commands.music;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 18.03.18
 */
public class ClearQueueCommand extends Command {

    public ClearQueueCommand() {
        super("clearqueue", "Clear the queue", Categorie.MUSIC, "cc");
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember())) {
                Bix.getInstance().getMusicManager().clearQueue(message.getGuild());
                Bix.getInstance().getMusicManager().stop(message.getGuild());
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Queue cleared").build()).queue();
            } else {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
            }
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in a voice channel").build()).queue();
        }
    }
}
