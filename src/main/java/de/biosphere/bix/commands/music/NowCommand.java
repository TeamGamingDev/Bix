package de.biosphere.bix.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class NowCommand extends Command {

    public NowCommand() {
        super("now", "Show the name of the current track", Categorie.MUSIC, "np", "nowplaying");
    }

    @Override
    public void execute(String[] args, Message message) {
        if (Bix.getInstance().getMusicManager().getPlayingTrack(message.getGuild()) != null) {
            AudioTrack audioTrack = Bix.getInstance().getMusicManager().getPlayingTrack(message.getGuild());
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("**" + audioTrack.getInfo().title + "**").build()).queue();
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("No track playing").build()).queue();
        }
    }
}
