package de.biosphere.bix.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

/**
 * @author Biosphere
 * @date 17.03.18
 */
@Getter
public abstract class Command {

    private final String command;
    private final String[] aliases;
    private final String description;
    private final Categorie categorie;

    public Command(String command, String description, Categorie categorie, String... alias) {
        this.command = command;
        this.description = description;
        this.categorie = categorie;
        this.aliases = alias;
    }

    public abstract void execute(String[] args, Message message);

    protected EmbedBuilder getEmbed(Guild guild, User requester) {
        return new EmbedBuilder().setFooter("@" + requester.getName() + "#" + requester.getDiscriminator(), requester.getEffectiveAvatarUrl()).setColor(guild.getSelfMember().getColor());
    }

    @Getter
    @AllArgsConstructor
    public enum Categorie {
        GENERAL("General", "one", "Commands for everyone"),
        MUSIC("Music", "musical_note", "Commands to control the music"),
        FUN("Fun", "imp", "Fun commands"),

        STAFF("Team", "zap", "Just for members with Admin oder Moderation Permissions"),
        OWNER("Owner", "zap", "Commands for the owner of the bot :)");

        private String name;
        private String emote;
        private String description;
    }
}
