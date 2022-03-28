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
            if (Lucky.getLuckyChance(p) == 0) {
                Lucky.setLuckyChance(p, Data.getconfigfile().getInt("Default_Lucky_Chance"));
            }
            if (Power.getPower(p) == 0) {
                Power.setPower(p, Power.getMaxPower(p));
            }
            if (Weight.getWeight(p) == 0) {
                Weight.setWeight(p, 0);
            }
            if (Weight.getMaxWeight(p) == 0) {
                Weight.setMaxWeight(p, 5000);
            }
            if (Bank.getBankLevel(p) == 0) {
                Bank.setBankLevel(p, 1);
            }
            if (Event.getBlockEvent(p) == 0) {
                Event.setBlocksEvent(p, 0);
            }
            if (PlayerData.getPlayerName(p) == null) {
                PlayerData.setPlayerName(p);
            }
            if (Event.getBlockEvent(p) > 0 && !Mining.getStart()) {
                Event.setBlocksEvent(p, 0);
            }
            if (PlayerData.getBlocks(p) <= 0) {
                PlayerData.setBlocks(p, 0);
            }
            Points.setPvPPoints(p, Files.getconfigfile().getInt("PVP.DEFAULT_POINTS"));
            ProtectTime.setProtectTimes(p, Files.getconfigfile().getInt("PVP.FIRST_TIME_PROTECT"));
            Status.TogglePvP(p, false);
        }
        if (Credit.getCredit(p) == 0 && !Credit.getType(p)) {
            Credit.setCredit(p, 100D);
            Credit.setType(p, false);
        }
    }
}
