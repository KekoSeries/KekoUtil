package tr.com.infumia.kekoutil;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.CfgSection;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.io.IOException;
import org.bukkit.Bukkit;

public final class KekoUtilPlugin extends KekoUtil {

    @Override
    public void onLoad() {
        KekoUtil.setInstance(this);
        KekoUtil.setInventory(new BasicSmartInventory(this));
        TaskUtilities.init(this);
        CfgSection.addProvidedClass(FileElement.class, new FileElement.Provider());
    }

    @Override
    public void onEnable() {
        Hooks.loadHooks();
        KekoUtil.getInventory().init();
        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, this::checkForUpdate));
    }

    private void checkForUpdate() {
        final UpdateChecker updater = new UpdateChecker(this, 82718);
        try {
            if (updater.checkForUpdates()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[&eKekoutil&6] &eNew version found (v" + updater.getNewVersion() + ')'));
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[&eKekoutil&6] &aYou're using the latest version (v" + updater.getNewVersion() + ')'));
            }
        } catch (final IOException exception) {
            this.getLogger().warning("Update checker failed, could not connect to the API.");
        }
    }

}
