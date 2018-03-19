package de.biosphere.bix.commands.music;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class PauseCommand extends Command {

    public PauseCommand() {
        super("pause", "Pause the current track", Categorie.MUSIC);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (!message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember())) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
                return;
            }
            boolean paused = Bix.getInstance().getMusicManager().isPaused(message.getGuild());
            Bix.getInstance().getMusicManager().setPaused(message.getGuild(), !paused);
            String pausedString = !paused ? "paused" : "resumed";
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("The Track is now " + pausedString).build()).queue();
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in a voice channel").build()).queue();
        }
    }
}
