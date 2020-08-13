package tr.com.infumia.kekoutil.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VaultHook implements Hook {

    public static final String VAULT_ID = "Vault";

    @Nullable
    private Economy economy;

    @NotNull
    @Override
    public String id() {
        return VaultHook.VAULT_ID;
    }

    @Override
    public boolean initiate() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> economyProvider =
            Bukkit.getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }
        return this.economy != null;
    }

    @NotNull
    @Override
    public Wrapped create() {
        if (this.economy == null) {
            throw new IllegalStateException("Vault not initiated! Use VaultHook#initiate() method.");
        }
        return new VaultWrapper(this.economy);
    }

}
