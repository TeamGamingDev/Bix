package de.biosphere.bix.commands.admin;

import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.Webhook;
import net.dv8tion.jda.core.utils.PermissionUtil;
import net.dv8tion.jda.webhook.WebhookClient;
import net.dv8tion.jda.webhook.WebhookClientBuilder;
import net.dv8tion.jda.webhook.WebhookMessage;
import net.dv8tion.jda.webhook.WebhookMessageBuilder;

import java.util.Arrays;

/**
 * @author Biosphere
 * @date 20.03.18
 */
public class ClydeCommand extends Command {

    public ClydeCommand() {
        super("clyde", "Send a webhook message", Categorie.STAFF);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (!PermissionUtil.checkPermission(message.getGuild().getSelfMember(), Permission.MANAGE_WEBHOOKS)) {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).addField("Missing permission", Permission.MANAGE_WEBHOOKS.getName(), false).build()).queue();
            return;
        }
        if (args.length < 1) {
            message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("-clyde [message]").build()).queue();
            return;
        }
        TextChannel otherChannel = message.getMentionedChannels().isEmpty() ? message.getTextChannel() : message.getMentionedChannels().get(0);
        Webhook webhook = null;
        for (Webhook hook : otherChannel.getWebhooks().complete()) {
            if (hook.getName().equals("Clydе")) {
                webhook = hook;
                break;
            }
        }
        if (webhook == null) {
            webhook = otherChannel.createWebhook("Clydе").complete();
        }
        WebhookClientBuilder clientBuilder = webhook.newClient();
        WebhookClient client = clientBuilder.build();

        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        if (message.getMentionedChannels().isEmpty()) {
            builder.setContent(String.join(" ", args));
        } else {
            builder.setContent(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        }
        builder.setAvatarUrl("https://discordapp.com/assets/f78426a064bc9dd24847519259bc42af.png");
        WebhookMessage webhookMessage = builder.build();
        client.send(webhookMessage);
        client.close();
    }
}
