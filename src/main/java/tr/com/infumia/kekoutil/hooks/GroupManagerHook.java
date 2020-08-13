package tr.com.infumia.kekoutil.hooks;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class GroupManagerHook implements Hook {

    public static final String GROUPMANAGER_ID = "GroupManager";

    private GroupManager groupManager;

    @NotNull
    @Override
    public String id() {
        return GroupManagerHook.GROUPMANAGER_ID;
    }

    @Override
    public boolean initiate() {
        return (this.groupManager = (GroupManager) Bukkit.getPluginManager().getPlugin("GroupManager")) != null;
    }

    @Override
    @NotNull
    public Wrapped create() {
        if (this.groupManager == null) {
            throw new IllegalStateException("GroupManager not initiated! Use GroupManagerHook#initiate method.");
        }

        return new GroupManagerWrapper(this.groupManager);
    }

}