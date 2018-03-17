package de.biosphere.bix.listener.other;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class ReadyListener extends ListenerAdapter{

    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        new Reflections("de.biosphere.bix.listener").getSubTypesOf(ListenerAdapter.class).forEach(listenerClass -> {
            if (!listenerClass.getName().equals(this.getClass().getName())) {
                try {
                    event.getJDA().addEventListener(listenerClass.newInstance());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        event.getJDA().getPresence().setPresence(OnlineStatus.ONLINE, Game.of(Game.GameType.DEFAULT, "-help | fee-hosting.com"));
    }
}
