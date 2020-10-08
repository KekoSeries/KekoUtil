package tr.com.infumia.kekoutil;

import io.github.portlek.configs.CfgSection;
import io.github.portlek.configs.bukkit.BukkitExtensions;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import org.bukkit.event.player.PlayerJoinEvent;
import tr.com.infumia.kekoutil.hooks.Hooks;
import tr.com.infumia.kekoutil.provider.FileElementProvider;
import tr.com.infumia.kekoutil.util.ListenerUtilities;
import tr.com.infumia.kekoutil.util.TaskUtilities;

public final class KekoUtilPlugin extends KekoUtil {

  @Override
  public void onLoad() {
    KekoUtil.setInstance(this);
    KekoUtil.setInventory(new BasicSmartInventory(this));
    TaskUtilities.init(this);
    BukkitExtensions.registerExtensions();
    CfgSection.addProvidedClass(FileElement.class, new FileElementProvider());
  }

  @Override
  public void onEnable() {
    Hooks.loadHooks();
    KekoUtil.getInventory().init();
    this.getServer().getScheduler().runTask(this, () ->
      this.getServer().getScheduler().runTaskAsynchronously(this, (Runnable) this::checkForUpdate));
    ListenerUtilities.register(
      PlayerJoinEvent.class,
      event -> event.getPlayer().hasPermission("kekoutil.version"),
      event -> this.checkForUpdate(event.getPlayer()),
      this);
  }
}
