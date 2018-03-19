package de.biosphere.bix.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class SkipCommand extends Command {

    public SkipCommand() {
        super("skip", "Skip the current track", Categorie.MUSIC);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (!message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember()) && message.getGuild().getAudioManager().getConnectedChannel() != null) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
                return;
            }

            Bix.getInstance().getMusicManager().skip(message.getGuild());
            if (Bix.getInstance().getMusicManager().getPlayingTrack(message.getGuild()) != null) {
                AudioTrackInfo trackInfo = Bix.getInstance().getMusicManager().getPlayingTrack(message.getGuild()).getInfo();
                String length;
                if (TimeUnit.MILLISECONDS.toHours(trackInfo.length) >= 24) {
                    length = String.format("%dd %02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(trackInfo.length), TimeUnit.MILLISECONDS.toHours(trackInfo.length) % 24, TimeUnit.MILLISECONDS.toMinutes(trackInfo.length) % 60, TimeUnit.MILLISECONDS.toSeconds(trackInfo.length) % 60);
                } else {
                    length = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(trackInfo.length) % 24, TimeUnit.MILLISECONDS.toMinutes(trackInfo.length) % 60, TimeUnit.MILLISECONDS.toSeconds(trackInfo.length) % 60);
                }
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField(trackInfo.title, "`" + trackInfo.author + " - " + (trackInfo.isStream ? "STREAM" : length) + "`", false).build()).queue();
            } else {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("There is nothing to skip").build()).queue();
            }
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Your are not in a voicechannel").build()).queue();
        }
    }
}
