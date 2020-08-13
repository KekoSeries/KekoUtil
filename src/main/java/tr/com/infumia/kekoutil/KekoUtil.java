package tr.com.infumia.kekoutil;

import io.github.portlek.smartinventory.SmartInventory;
import java.util.Optional;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

}
