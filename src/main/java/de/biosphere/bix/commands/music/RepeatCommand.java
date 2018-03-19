package de.biosphere.bix.commands.music;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class RepeatCommand extends Command {

    public RepeatCommand() {
        super("repeat", "Repeat the current track", Categorie.MUSIC);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {

            if (message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember())) {
                boolean repeat = Bix.getInstance().getMusicManager().isRepeat(message.getGuild());
                Bix.getInstance().getMusicManager().setRepeat(message.getGuild(), !repeat);
                String pausedString = !repeat ? "on" : "off";
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Reapeat " + pausedString).build()).queue();
            } else {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
            }
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Your are not in a voicechannel").build()).queue();
        }
    }
}
