package tr.com.infumia.kekoutil;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.CfgSection;
import io.github.portlek.configs.bukkit.BukkitExtensions;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public final class KekoUtilPlugin extends KekoUtil {

    @Override
    public void onLoad() {
        KekoUtil.setInstance(this);
        KekoUtil.setInventory(new BasicSmartInventory(this));
        TaskUtilities.init(this);
        BukkitExtensions.registerExtensions();
        CfgSection.addProvidedClass(FileElement.class, new FileElement.Provider());
    }

    @Override
    public void onEnable() {
        Hooks.loadHooks();
        KekoUtil.getInventory().init();
        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, (@NotNull Runnable) this::checkForUpdate));
        ListenerUtilities.register(
            PlayerJoinEvent.class,
            event -> event.getPlayer().hasPermission("kekoutil.version"),
            event -> this.checkForUpdate(event.getPlayer()),
            this);
    }

    private void checkForUpdate() {
        this.checkForUpdate(Bukkit.getConsoleSender());
    }

    private void checkForUpdate(@NotNull final CommandSender sender) {
        final UpdateChecker updater = new UpdateChecker(this, 82718);
        try {
            if (updater.checkForUpdates()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &eNew version found (v" + updater.getNewVersion() + ')'));
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &eYou can download the latest version here:"));
                Bukkit.getConsoleSender().sendMessage(updater.getResourceURL());
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &aYou're using the latest version (v" + updater.getNewVersion() + ')'));
            }
        } catch (final IOException exception) {
            this.getLogger().warning("Update checker failed, could not connect to the API.");
        }
    }

}
