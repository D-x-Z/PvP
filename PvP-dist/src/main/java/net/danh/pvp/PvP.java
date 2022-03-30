package net.danh.pvp;

import net.danh.pvp.Commands.PvPCommmands;
import net.danh.pvp.Event.*;
import net.danh.pvp.Hook.PlaceholderAPI;
import net.danh.pvp.Manager.Files;
import net.danh.pvp.Manager.ProtectTime;
import net.danh.pvp.Manager.Status;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import preponderous.ponder.minecraft.bukkit.abs.PonderBukkitPlugin;
import preponderous.ponder.minecraft.bukkit.tools.EventHandlerRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public final class PvP extends PonderBukkitPlugin implements Listener {

    private static PvP instance;

    public static PvP getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Metrics metrics = new Metrics(this, 14684);
        metrics.addCustomChart(new SimplePie("plugin_name", () -> getDescription().getName()));
        if (getServer().getPluginManager().getPlugin("bCore") != null) {
            getLogger().log(Level.INFO, "Successfully hooked with bCore v" + Objects.requireNonNull(getServer().getPluginManager().getPlugin("bCore")).getDescription().getVersion());
        } else {
            getLogger().log(Level.INFO, "Unsuccessfully hooked with bCore");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            getLogger().log(Level.INFO, "Successfully hooked with WorldGuard v" + Objects.requireNonNull(getServer().getPluginManager().getPlugin("WorldGuard")).getDescription().getVersion());
        } else {
            getLogger().log(Level.INFO, "Unsuccessfully hooked with WorldGuard");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI().register();
            getLogger().log(Level.INFO, "Successfully hooked with PlaceholderAPI v" + Objects.requireNonNull(getServer().getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
        }
        registerEventHandlers();
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new PvPCommmands());
        Objects.requireNonNull(getCommand("pvpadmin")).setExecutor(new PvPCommmands());
        Files.createfiles();
        Files.checkFiles();
    }


    @Override
    public void onDisable() {
        Files.saveconfig();
        Files.savelanguage();
        Files.savedata();
    }

    @Contract(" -> new")
    private @NotNull ArrayList<Listener> initializeListeners() {
        return new ArrayList<>(Arrays.asList(
                new DamagedByEntity(),
                new DamagedByArrow(),
                new DamagedByFishing(),
                new DeathEvent(),
                new JoinEvent()
        ));
    }

    /**
     * Registers the event handlers of the plugin using Ponder.
     */
    private void registerEventHandlers() {
        ArrayList<Listener> listeners = initializeListeners();
        EventHandlerRegistry eventHandlerRegistry = new EventHandlerRegistry();
        eventHandlerRegistry.registerEventHandlers(listeners, this);
    }


}
