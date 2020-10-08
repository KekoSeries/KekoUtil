package tr.com.infumia.kekoutil.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.Wrapped;

public final class VaultWrapper implements Wrapped {

  @NotNull
  private final Economy economy;

  public VaultWrapper(@NotNull final Economy economy) {
    this.economy = economy;
  }

  public void addMoney(@NotNull final Player player, final double money) {
    this.economy.depositPlayer(player, money);
  }

  public void removeMoney(@NotNull final Player player, final double money) {
    if (this.getMoney(player) < money) {
      return;
    }
    this.economy.withdrawPlayer(player, money);
  }

  public double getMoney(@NotNull final Player player) {
    return this.economy.getBalance(player);
  }
}
