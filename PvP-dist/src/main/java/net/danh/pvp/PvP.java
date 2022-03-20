package net.danh.pvp;

import net.danh.pvp.Commands.PvPCommmands;
import net.danh.pvp.Event.*;
import net.danh.pvp.Hook.PlaceholderAPI;
import net.danh.pvp.Manager.Files;
import net.danh.pvp.Manager.ProtectTime;
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
        registerEventHandlers();
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new PvPCommmands());
        Objects.requireNonNull(getCommand("pvpadmin")).setExecutor(new PvPCommmands());
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            getLogger().log(Level.INFO, "Successfully hooked with WorldGuard v" + Objects.requireNonNull(getServer().getPluginManager().getPlugin("WorldGuard")).getDescription().getVersion());
        }
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPI().register();
            getLogger().log(Level.INFO, "Successfully hooked with PlaceholderAPI v" + Objects.requireNonNull(getServer().getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
        }
        Files.createfiles();
        Files.checkFiles();

        (new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (ProtectTime.getProtectTimes(p) > 0) {
                        int timeLeft = ProtectTime.getProtectTimes(p);
                        timeLeft--;
                        ProtectTime.setProtectTimes(p, timeLeft);
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new TranslatableComponent(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.PROTECT_PVP_TIMES"))
                                        .replaceAll("%time%", String.valueOf(ProtectTime.getProtectTimes(p))))));
                        if (timeLeft <= 0) {
                            ProtectTime.setProtectTimes(p, 0);
                            cancel();
                        }
                    }
                }
            }
        }).runTaskTimer(this, 20L, 20L);
    }


    @Override
    public void onDisable() {
        Files.saveconfig();
        Files.savelanguage();
        Files.savedata();
    }

    @Contract(" -> new")
    private @NotNull ArrayList<Listener> initializeListeners() {
        return new ArrayList<Listener>(Arrays.asList(
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
