package de.biosphere.bix.commands.general;

import de.biosphere.bix.commands.Command;
import de.biosphere.bix.utils.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class ServerinfoCommand extends Command{

    public ServerinfoCommand() {
        super("serverinfo", "Show some information about a server", Categorie.GENERAL, "guildinfo");
    }

    @Override
    public void execute(String[] args, Message message) {
        Guild guild = message.getGuild();

        EmbedBuilder embedBuilder = getEmbed(guild, message.getAuthor());

        embedBuilder.setTitle(guild.getName(), "https://discord.gg/3By2ZPC");
        embedBuilder.setThumbnail(guild.getIconUrl());
        embedBuilder.setFooter(message.getAuthor().getName(), message.getAuthor().getEffectiveAvatarUrl());
        embedBuilder.addField("Created",  guild.getCreationTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), true);
        embedBuilder.addField("Region", guild.getRegionRaw(), true);
        embedBuilder.addField("Users", String.valueOf(guild.getMembers().size()), true);
        embedBuilder.addField("Text Channels", String.valueOf(guild.getTextChannels().size()), true);
        embedBuilder.addField("Voice Channels", String.valueOf(guild.getVoiceChannels().size()), true);
        embedBuilder.addField("Roles", String.valueOf(guild.getRoles().size()), true);
        embedBuilder.addField("Owner", StringUtils.replaceCharacter(guild.getOwner().getUser().getName()) + "#" + guild.getOwner().getUser().getDiscriminator(), true);
        embedBuilder.addField("Uptime", String.valueOf(ChronoUnit.DAYS.between(guild.getCreationTime(), LocalDateTime.now().atOffset(ZoneOffset.UTC))), true);

        message.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
