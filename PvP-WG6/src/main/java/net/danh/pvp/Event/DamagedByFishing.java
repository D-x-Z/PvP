package net.danh.pvp.Event;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.danh.pvp.Manager.ProtectTime;
import net.danh.pvp.Manager.Status;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

public class DamagedByFishing implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamagedByFishing(@NotNull PlayerFishEvent e) {
        if (e.getCaught() instanceof Player) {
            Player attacked = (Player) e.getCaught();
            Player damager = e.getPlayer();
            boolean damagerState = Status.getPvPStatus(damager);
            boolean attackedState = Status.getPvPStatus(attacked);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(damager);
            LocalPlayer localPlayer1 = WorldGuardPlugin.inst().wrapPlayer(attacked);
            Location loc = new Location(damager.getWorld(), damager.getLocation().getBlockX(), damager.getLocation().getBlockY(), damager.getLocation().getBlockZ());
            Location loc1 = new Location(attacked.getWorld(), attacked.getLocation().getBlockX(), attacked.getLocation().getBlockY(), attacked.getLocation().getBlockZ());
            RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
            RegionQuery query = container.createQuery();
            if (damager.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD || damager.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
                if (damagerState || attackedState
                        || ProtectTime.getProtectTimes(damager) > 0 || ProtectTime.getProtectTimes(attacked) > 0
                        || !query.testState(loc, localPlayer, DefaultFlag.PVP) || !query.testState(loc1, localPlayer1, DefaultFlag.PVP)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
