package de.biosphere.bix.commands.music;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import de.biosphere.bix.utils.StringUtils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @author Biosphere
 * @date 18.03.18
 */
public class PlayCommand extends Command {

    public PlayCommand() {
        super("play", "Play some music", Categorie.MUSIC);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            VoiceChannel channel = message.getMember().getVoiceState().getChannel();
            if (!message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember()) && message.getGuild().getAudioManager().getConnectedChannel() != null) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
                return;
            }

            if (message.getMember().getVoiceState().getChannel().getUserLimit() == message.getMember().getVoiceState().getChannel().getMembers().size()) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Cannot play in a full channel").build()).queue();
                return;
            }
            if (message.getGuild().getAudioManager().getConnectedChannel() == null) {
                if (PermissionUtil.checkPermission(channel, message.getGuild().getSelfMember(), Permission.VOICE_CONNECT)) {
                    message.getGuild().getAudioManager().openAudioConnection(message.getMember().getVoiceState().getChannel());
                } else {
                    message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Missing permission", Permission.VOICE_CONNECT.getName(), false).build()).queue();
                    return;
                }
            }
            if (args.length < 1) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("!play <URL|Titel").build()).queue();
            } else {
                Bix.getInstance().getMusicManager().setPaused(message.getGuild(), false);
                if (StringUtils.extractUrls(args[0]).isEmpty()) {
                    Bix.getInstance().getMusicManager().loadTrack(message.getTextChannel(), message.getMember().getUser(), "ytsearch:" + String.join(" ", args));
                } else {
                    Bix.getInstance().getMusicManager().loadTrack(message.getTextChannel(), message.getMember().getUser(), args[0]);
                }
            }
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Your are not in a voicechannel").build()).queue();
        }
    }
}
