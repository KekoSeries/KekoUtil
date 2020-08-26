package tr.com.infumia.kekoutil.hooks;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tr.com.infumia.kekoutil.Hook;

public final class PermissionsExHook implements Hook {

    public static final String PERMISSONSEX_ID = "PermissionsEx";

    private PermissionsEx permissionsEx;

    @NotNull
    @Override
    public String id() {
        return PermissionsExHook.PERMISSONSEX_ID;
    }

    @Override
    public boolean initiate() {
        return (this.permissionsEx = (PermissionsEx) Bukkit.getPluginManager().getPlugin("PermissionsEx")) != null;
    }

    @Override
    @NotNull
    public PermissionsExWrapper create() {
        if (this.permissionsEx == null) {
            throw new IllegalStateException("PermissionsEx not initiated! Use PermissionsExHook#initiate method.");
        }

        return new PermissionsExWrapper(this.permissionsEx);
    }

}