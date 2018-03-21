package de.biosphere.bix.commands.admin;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @author Biosphere
 * @date 20.03.18
 */
public class MoveAllCommand extends Command {

    public MoveAllCommand() {
        super("moveall", "Move all users in you voicechannel", Categorie.STAFF, "ma");
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (PermissionUtil.checkPermission(message.getGuild().getSelfMember(), Permission.VOICE_MOVE_OTHERS)) {
                for (VoiceChannel voiceChannel : message.getGuild().getVoiceChannels()) {
                    voiceChannel.getMembers().forEach(action -> message.getGuild().getController().moveVoiceMember(action, message.getMember().getVoiceState().getChannel()).queue());
                }
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Moved all users to your channel").build()).queue();
            } else {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Missing permission", Permission.VOICE_MOVE_OTHERS.getName(), false).build()).queue();
            }
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in a voice channel").build()).queue();
        }
    }
}
