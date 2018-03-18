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
    public void execute(String[] args, Message msg) {
        if (msg.getMember().getVoiceState() != null && msg.getMember().getVoiceState().inVoiceChannel()) {
            if (msg.getMember().getVoiceState().getChannel().getMembers().contains(msg.getGuild().getSelfMember())) {
                Bix.getInstance().getMusicManager().clearQueue(msg.getGuild());
                Bix.getInstance().getMusicManager().stop(msg.getGuild());
                msg.getTextChannel().sendMessage(getEmbed(msg.getGuild(), msg.getAuthor()).setDescription("Queue cleared").build()).queue();
            } else {
                msg.getTextChannel().sendMessage(getEmbed(msg.getGuild(), msg.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
            }
        } else {
            msg.getTextChannel().sendMessage(getEmbed(msg.getGuild(), msg.getAuthor()).setDescription("You are not in a voice channel").build()).queue();
        }
    }
}
