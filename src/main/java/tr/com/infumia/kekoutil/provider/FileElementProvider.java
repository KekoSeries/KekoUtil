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

package tr.com.infumia.kekoutil.provider;

import io.github.portlek.configs.CfgSection;
import io.github.portlek.configs.Provided;
import io.github.portlek.configs.bukkit.BkktSection;
import io.github.portlek.configs.util.GeneralUtilities;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.FileElement;
import tr.com.infumia.kekoutil.util.PlaceType;

public final class FileElementProvider implements Provided<FileElement> {

    @Override
    public void set(@NotNull final FileElement fileElement, @NotNull final CfgSection section,
                    @NotNull final String path) {
        final String dot = GeneralUtilities.putDot(path);
        ((BkktSection) section).setItemStack(dot + "item", fileElement.itemStack());
        section.set(dot + "type", fileElement.type().name());
        section.remove(dot + "values");
        fileElement.values().forEach((s, o) ->
            section.set(dot + "values." + s, o));
    }

    @NotNull
    @Override
    public Optional<FileElement> getWithField(@NotNull final FileElement fileElement,
                                              @NotNull final CfgSection section, @NotNull final String path) {
        return this.get(section, path)
            .map(element -> element.changeEvent(fileElement.events()));
    }

    @NotNull
    @Override
    public Optional<FileElement> get(@NotNull final CfgSection section, @NotNull final String path) {
        final String dot = GeneralUtilities.putDot(path);
        final Optional<ItemStack> itemStackOptional = ((BkktSection) section).getItemStack(dot + "item");
        final Optional<String> typeOptional = section.getString(dot + "type");
        if (!itemStackOptional.isPresent() || !typeOptional.isPresent()) {
            return Optional.empty();
        }
        final PlaceType type = PlaceType.fromString(typeOptional.get());
        final Map<String, Object> values = section.getSection(dot + "values")
            .map(CfgSection::getConfigurationSection)
            .map(configurationSection -> configurationSection.getMapValues(false))
            .orElse(new HashMap<>());
        if (type.control(values)) {
            return Optional.of(FileElement.from(itemStackOptional.get(), type, values));
        }
        final Map<String, Object> defaults = type.defaultValues();
        if (defaults.isEmpty()) {
            section.remove(dot + "values");
        } else {
            defaults.forEach((s, o) ->
                section.set(dot + "values." + s, o));
        }
        return Optional.empty();
    }

}
