package tr.com.infumia.kekoutil;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.smartinventory.SmartInventory;
import java.io.IOException;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.kekoutil.util.TaskUtilities;
import tr.com.infumia.kekoutil.util.UpdateChecker;

public abstract class KekoUtil extends JavaPlugin {

  private static final Object LOCK = new Object();

  @Nullable
  private static KekoUtil instance;

  @Nullable
  private static SmartInventory inventory;

  @NotNull
  public static KekoUtil getInstance() {
    return Optional.ofNullable(KekoUtil.instance).orElseThrow(() ->
      new IllegalStateException("You cannot be used KekoUtil plugin before its start!"));
  }

  static void setInstance(@NotNull final KekoUtil instance) {
    if (Optional.ofNullable(KekoUtil.instance).isPresent()) {
      throw new IllegalStateException("You can't use KekoUtil#setInstance method twice!");
    }
    synchronized (KekoUtil.LOCK) {
      KekoUtil.instance = instance;
    }
  }

  @NotNull
  public static SmartInventory getInventory() {
    return Optional.ofNullable(KekoUtil.inventory).orElseThrow(() ->
      new IllegalStateException("You cannot be used KekoUtil plugin before its start!"));
  }

  static void setInventory(@NotNull final SmartInventory inventory) {
    if (Optional.ofNullable(KekoUtil.inventory).isPresent()) {
      throw new IllegalStateException("You can't use KekoUtil#setInstance method twice!");
    }
    synchronized (KekoUtil.LOCK) {
      KekoUtil.inventory = inventory;
    }
  }

  public static void checkForUpdate(@NotNull final Plugin plugin) {
    KekoUtil.checkForUpdate(plugin, Bukkit.getConsoleSender());
  }

  public static void checkForUpdate(@NotNull final Plugin plugin, @NotNull final CommandSender sender) {
    TaskUtilities.async(() -> {
      final UpdateChecker updater = new UpdateChecker(plugin, 82718);
      try {
        if (updater.checkForUpdates()) {
          sender.sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &eNew version found (v" + updater.getNewVersion() + ')'));
          sender.sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &eYou can download the latest version here:"));
          sender.sendMessage(updater.getResourceURL());
        } else {
          sender.sendMessage(ColorUtil.colored("&6[&eKekoUtil&6] &aYou're using the latest version (v" + updater.getNewVersion() + ')'));
        }
      } catch (final IOException exception) {
        plugin.getLogger().warning("Update checker failed, could not connect to the API.");
      }
    });
  }

  protected void checkForUpdate() {
    KekoUtil.checkForUpdate(this);
  }

  protected void checkForUpdate(@NotNull final CommandSender sender) {
    KekoUtil.checkForUpdate(this, sender);
  }
}
