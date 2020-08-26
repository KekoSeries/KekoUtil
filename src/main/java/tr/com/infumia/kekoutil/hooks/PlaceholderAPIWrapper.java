package tr.com.infumia.kekoutil.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.Wrapped;

public final class PlaceholderAPIWrapper implements Wrapped {

    @NotNull
    public String apply(@NotNull final Player player, @NotNull final String string) {
        return PlaceholderAPI.setPlaceholders(player, string);
    }

    // TODO Add new PlaceholderAPI methods as you want.

}
