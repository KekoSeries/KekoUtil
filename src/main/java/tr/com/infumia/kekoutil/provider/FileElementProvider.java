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
import java.io.File;
import java.util.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.KekoUtil;
import tr.com.infumia.kekoutil.element.FileElement;
import tr.com.infumia.kekoutil.util.PlaceType;

public final class FileElementProvider implements Provided<FileElement> {

    @Override
    public void set(@NotNull final FileElement fileElement, @NotNull final CfgSection section,
                    @NotNull final String path) {
        final String dot = GeneralUtilities.putDot(path);
        ((BkktSection) section).setItemStack(dot + "item", fileElement.itemStack());
        section.set(dot + "type", fileElement.type().name());
        section.remove(dot + "values");
        section.set(dot + "values", new ArrayList<>(fileElement.values().values()));
        section.remove(dot + "objects");
        final CfgSection objects = section.createSection(dot + "objects");
        fileElement.values().forEach(objects::set);
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
        final String typeString = typeOptional.get();
        final PlaceType type = PlaceType.fromString(typeString);
        if (type == PlaceType.NONE) {
            final File file = section.getParent().getFile();
            KekoUtil.getInstance().getLogger().severe(file.getName() + " file contains wrong PlaceType!");
            KekoUtil.getInstance().getLogger().severe(typeString + " is not a valid value for PlaceType!");
            return Optional.empty();
        }
        final Map<String, Object> objects = new HashMap<>();
        section.getSection("objects").ifPresent(objectSection ->
            objectSection.getKeys(false).forEach(s ->
                objectSection.get(s).ifPresent(o ->
                    objects.put(s, o))));
        final Map<String, Object> parse = type.parse(section.getListOrEmpty(dot + "values").toArray());
        if (type.control(parse.values().toArray())) {
            return Optional.of(FileElement.from(itemStackOptional.get(), type, objects, parse));
        }
        final List<Object> defaults = type.defaultValues();
        if (defaults.isEmpty()) {
            section.remove(dot + "values");
        } else {
            section.set(dot + "values", defaults);
        }
        return Optional.empty();
    }

}
