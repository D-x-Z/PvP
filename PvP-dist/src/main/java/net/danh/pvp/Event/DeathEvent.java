package net.danh.pvp.Event;

import net.danh.pvp.Manager.Files;
import net.danh.pvp.Manager.Points;
import net.danh.pvp.Manager.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(@NotNull PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (Points.getPvPPoints(p) >= Files.getconfigfile().getInt("PVP.POINTS_DEAD")) {
            Points.removePvPPoints(p, Files.getconfigfile().getInt("PVP.POINTS_DEAD"));
            Status.TogglePvP(p, false);
        } else {
            Status.TogglePvP(p, true);
        }
    }
}
