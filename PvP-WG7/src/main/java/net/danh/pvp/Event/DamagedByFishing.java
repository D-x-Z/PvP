package net.danh.pvp.Event;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.danh.Manager.Credit;
import net.danh.pvp.Manager.ProtectTime;
import net.danh.pvp.Manager.Status;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

public class DamagedByFishing implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamageByFishing(@NotNull PlayerFishEvent e) {
        if (e.getCaught() instanceof Player) {
            Player attacked = (Player) e.getCaught();
            Player damager = e.getPlayer();
            boolean damagerState = Status.getPvPStatus(damager);
            boolean attackedState = Status.getPvPStatus(attacked);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(damager);
            LocalPlayer localPlayer1 = WorldGuardPlugin.inst().wrapPlayer(attacked);
            Location loc = new Location(localPlayer.getWorld(), localPlayer.getLocation().getBlockX(), localPlayer.getLocation().getBlockY(), localPlayer.getLocation().getBlockZ());
            Location loc1 = new Location(localPlayer1.getWorld(), localPlayer1.getLocation().getBlockX(), localPlayer1.getLocation().getBlockY(), localPlayer1.getLocation().getBlockZ());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            if (damager.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD || damager.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
                if (!damagerState || !attackedState
                        || ProtectTime.getProtectTimes(damager) > 0 || ProtectTime.getProtectTimes(attacked) > 0
                        || !query.testState(loc, localPlayer, Flags.PVP) || !query.testState(loc1, localPlayer1, Flags.PVP)
                        || Credit.getCredit(damager) < 50) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
