package net.danh.pvp.Event;

import net.danh.pvp.Manager.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (FirstJoin.isFirstJoin(p)) {
            FirstJoin.setFirstJoin(p, true);
            Points.setPvPPoints(p, Files.getconfigfile().getInt("PVP.DEFAULT_POINTS"));
            ProtectTime.setProtectTimes(p, Files.getconfigfile().getInt("PVP.FIRST_TIME_PROTECT"));
            Status.TogglePvP(p, false);
        }
    }
}
