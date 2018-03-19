package de.biosphere.bix.commands.music;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class LeaveCommand extends Command {

    public LeaveCommand() {
        super("leave", "Leave the current voice channel", Categorie.MUSIC, "disconnect");
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getGuild().getAudioManager().getConnectedChannel() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (!message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember())) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
                return;
            }
            message.getGuild().getAudioManager().closeAudioConnection();
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Disconnectet from your voice channel").build()).queue();
        } else if (!message.getMember().getVoiceState().inVoiceChannel()) {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Your are not in a voicechannel").build()).queue();
        }
    }
}
