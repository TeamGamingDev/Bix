package de.biosphere.bix.commands.general;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.entities.Message;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class UptimeCommand extends Command {

    public UptimeCommand() {
        super("uptime", "Show the uptime of the bot", Categorie.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("Uptime: " + String.valueOf(TimeUnit.MILLISECONDS.toDays(uptime) + "d " + TimeUnit.MILLISECONDS.toHours(uptime) % 24 + "h " + TimeUnit.MILLISECONDS.toMinutes(uptime) % 60 + "m " + TimeUnit.MILLISECONDS.toSeconds(uptime) % 60 + "s")).build()).queue();
    }
}
