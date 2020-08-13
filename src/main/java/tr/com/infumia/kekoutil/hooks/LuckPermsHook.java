package tr.com.infumia.kekoutil.hooks;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public final class LuckPermsHook implements Hook {

    public static final String LUCKPERMS_ID = "LuckPerms";

    private LuckPerms luckPerms;

    @NotNull
    @Override
    public String id() {
        return LuckPermsHook.LUCKPERMS_ID;
    }

    @Override
    public boolean initiate() {
        final boolean check = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;
        if (check) {
            final RegisteredServiceProvider<LuckPerms> provider =
                Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                this.luckPerms = provider.getProvider();
            }
        }
        return this.luckPerms != null;
    }

    @Override
    @NotNull
    public Wrapped create() {
        if (this.luckPerms == null) {
            throw new IllegalStateException("LuckPerms not initiated! Use LuckPermsHook#initiate method.");
        }
        return new LuckPermsWrapper(this.luckPerms);
    }

}