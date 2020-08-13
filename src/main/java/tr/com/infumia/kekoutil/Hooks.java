/*
 * MIT License
 *
 * Copyright (c) 2020 Infumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.kekoutil;

import io.github.portlek.configs.bukkit.util.ColorUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.hooks.*;

@UtilityClass
public class Hooks {

    private final Map<String, Wrapped> WRAPPERS = new HashMap<>();

    @NotNull
    public Optional<VaultWrapper> getVault() {
        return Hooks.getWrapper(VaultHook.VAULT_ID);
    }

    @NotNull
    public Optional<PlaceholderAPIWrapper> getPlaceholderAPI() {
        return Hooks.getWrapper(PlaceholderAPIHook.PLACEHOLDER_API_ID);
    }

    @NotNull
    public Optional<LuckPermsWrapper> getLuckPerms() {
        return Hooks.getWrapper(LuckPermsHook.LUCK_PERMS_ID);
    }

    void loadHooks() {
        Stream.of(new LuckPermsHook(), new PlaceholderAPIHook(), new VaultHook())
            .filter(Hook::initiate)
            .forEach(hook -> Hooks.WRAPPERS.put(hook.id(), hook.create()));
        Hooks.WRAPPERS.keySet().forEach(Hooks::sendHookNotify);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private <T extends Wrapped> Optional<T> getWrapper(@NotNull final String wrappedId) {
        return Optional.ofNullable(Hooks.WRAPPERS.get(wrappedId))
            .map(o -> (T) o);
    }

    private void sendHookNotify(@NotNull final String id) {
        Bukkit.getConsoleSender().sendMessage(
            ColorUtil.colored(
                id + " is hooking"));
    }

}
