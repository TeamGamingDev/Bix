package de.biosphere.bix.commands.music;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @author Biosphere
 * @date 18.03.18
 */
public class JoinCommand extends Command {

    public JoinCommand() {
        super("join", "Let the bot join your voice channel", Categorie.MUSIC, "summon");
    }

    @Override
    public void execute(String[] args, Message message) {
        AudioManager audioManager = message.getGuild().getAudioManager();

        if (audioManager.isConnected() || audioManager.isAttemptingToConnect()) {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("The bot is already connected to a voice channel").build()).queue();
        } else if (!message.getMember().getVoiceState().inVoiceChannel()) {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in a voice channel").build()).queue();
        } else {
            VoiceChannel channel = message.getMember().getVoiceState().getChannel();
            if (message.getMember().getVoiceState().getChannel().getUserLimit() == message.getMember().getVoiceState().getChannel().getMembers().size()) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Cannot play in a full channel").build()).queue();
                return;
            }
            if (PermissionUtil.checkPermission(channel, message.getGuild().getSelfMember(), Permission.VOICE_CONNECT)) {
                message.getGuild().getAudioManager().openAudioConnection(channel);
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Channel " + channel.getName() + " joined").build()).queue();
            } else {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Missing permission", Permission.VOICE_CONNECT.getName(), false).build()).queue();
            }
        }
    }
}
