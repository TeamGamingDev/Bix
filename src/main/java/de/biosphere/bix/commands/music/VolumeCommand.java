package de.biosphere.bix.commands.music;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Biosphere
 * @date 23.02.18
 */
public class VolumeCommand extends Command {

    public VolumeCommand() {
        super("volume", "Change the volume of the bot", Categorie.MUSIC, "v");
    }

    private static String getVolume(int volume) {
        String s = "";
        for (int i = 10; i > 0; i--) {
            if (i > (volume / 10)) {
                s += ":black_large_square:";
            } else {
                s += ":white_large_square:";
            }
        }
        return s;
    }

    @Override
    public void execute(String[] args, Message message) {
        if (message.getMember().getVoiceState() != null && message.getMember().getVoiceState().inVoiceChannel()) {
            if (!message.getMember().getVoiceState().getChannel().getMembers().contains(message.getGuild().getSelfMember()) && message.getGuild().getAudioManager().getConnectedChannel() != null) {
                message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("You are not in my voice channel").build()).queue();
                return;
            }

            if (args.length == 1) {
                int volume;
                try {
                    volume = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Invalid Argument", "*" + args[0] + "* is not a number.", false).build()).queue();
                    return;
                }
                if (volume < 0 || volume > 100) {
                    message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Invalid Volume", "Value can just be between 0 and 100.", false).build()).queue();
                    return;
                }
                Bix.getInstance().getMusicManager().setVolume(message.getGuild(), volume);
            }
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Volume: " + Bix.getInstance().getMusicManager().getVolume(message.getGuild()), getVolume(Bix.getInstance().getMusicManager().getVolume(message.getGuild())), false).build()).queue();
        } else {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Your are not in a voicechannel").build()).queue();
        }
    }
}
