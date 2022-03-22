package net.danh.pvp.Event;

import net.danh.Contest.Killing.Killing;
import net.danh.Manager.Event;
import net.danh.pvp.Manager.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class KillEvent implements Listener {

    @EventHandler
    public void onKilling(@NotNull PlayerDeathEvent e) {
        Player k = e.getEntity();
        Player p = k.getKiller();
        if (Killing.getStart()) {
            Status.TogglePvP(k, false);
            Event.addKillEvent(p, 2);
        }
    }
}
