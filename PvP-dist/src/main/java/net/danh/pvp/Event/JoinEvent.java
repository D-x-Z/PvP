package net.danh.pvp.Event;

import net.danh.Contest.Mining.Mining;
import net.danh.Manager.*;
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
            if (PlayerData.getXP(p) == 0) {
                PlayerData.setXP(p, 0);
            }
            if (Power.getRestorePower(p) == 0) {
                Power.setRestorePower(p, Data.getconfigfile().getInt("Restore_Power"));
            }
            if (Power.getMaxPower(p) == 0) {
                Power.setMaxPower(p, Data.getconfigfile().getInt("Max_Power"));
            }
            if (Power.getPower(p) == 0) {
                Power.setPower(p, Power.getMaxPower(p));
            }
            if (Bank.getBankLevel(p) == 0) {
                Bank.setBankLevel(p, 1);
            }
            if (Credit.getCredit(p) == 0) {
                Credit.setCredit(p, 100D);
            }
            Points.setPvPPoints(p, Files.getconfigfile().getInt("PVP.DEFAULT_POINTS"));
            ProtectTime.setProtectTimes(p, Files.getconfigfile().getInt("PVP.FIRST_TIME_PROTECT"));
            Status.TogglePvP(p, false);
        }
    }
}
