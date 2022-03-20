package net.danh.pvp.Hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.danh.pvp.Manager.Files;
import net.danh.pvp.Manager.Points;
import net.danh.pvp.Manager.Status;
import net.danh.pvp.PvP;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "pvp";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.valueOf(PvP.getInstance().getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return PvP.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (p == null) {
            return "Player not online";
        }
        if (identifier.equalsIgnoreCase("status")) {
            if (Status.getPvPStatus(p)) {
                return Files.getconfigfile().getString("PVP.STATUS_ON");
            }
            if (!Status.getPvPStatus(p)) {
                return Files.getconfigfile().getString("PVP.STATUS_OFF");
            }
            return "";
        }
        if (identifier.equalsIgnoreCase("points")) {
            return String.valueOf(Points.getPvPPoints(p));
        }
        return null;
    }
}
