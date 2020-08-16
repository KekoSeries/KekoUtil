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

import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.configs.CfgSection;
import io.github.portlek.configs.Provided;
import io.github.portlek.configs.bukkit.BkktSection;
import io.github.portlek.configs.util.GeneralUtilities;
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.event.abs.ClickEvent;
import io.github.portlek.smartinventory.util.SlotPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public final class FileElement {

    @NotNull
    private final ItemStack itemStack;

    @NotNull
    private final SlotPos position;

    @NotNull
    private final Consumer<ClickEvent> clickEvent;

    public FileElement(@NotNull final ItemStack itemStack, @NotNull final SlotPos position) {
        this(itemStack, position, event -> {
        });
    }

    public FileElement(@NotNull final ItemStack itemStack, final int row, final int column) {
        this(itemStack, SlotPos.of(row, column));
    }

    public FileElement(@NotNull final ItemStackBuilder builder, final int row, final int column) {
        this(builder.itemStack(), row, column);
    }

    public FileElement(@NotNull final FileElement fileElement) {
        this(fileElement.itemStack, fileElement.position);
    }

    public int row() {
        return this.position.getRow();
    }

    public int column() {
        return this.position.getColumn();
    }

    public void insert(@NotNull final InventoryContents contents) {
        contents.set(this.row(), this.column(), this.clickableItem());
    }

    @NotNull
    public Icon clickableItem() {
        return Icon.from(this.itemStack).whenClick(this.clickEvent);
    }

    public void fill(@NotNull final InventoryContents contents) {
        contents.fill(this.clickableItem());
    }

    @NotNull
    public FileElement changeColumn(final int column) {
        return new FileElement(this.itemStack, this.row(), column);
    }

    @NotNull
    public FileElement changeRow(final int row) {
        return new FileElement(this.itemStack, row, this.column());
    }

    @NotNull
    public FileElement changeItemStack(@NotNull final ItemStack itemStack) {
        return new FileElement(itemStack, this.position);
    }

    public FileElement changeClickEvent(@NotNull final Consumer<ClickEvent> consumer) {
        return new FileElement(this.itemStack, this.position, consumer);
    }

    @NotNull
    public FileElement changeMaterial(@NotNull final Material material) {
        final ItemStack clone = this.itemStack.clone();
        clone.setType(material);
        return new FileElement(clone, this.position);
    }

    @NotNull
    public FileElement replace(final boolean name, final boolean lore, @NotNull final Placeholder... placeholders) {
        return this.replace(name, lore, Arrays.asList(placeholders));
    }

    @NotNull
    public FileElement replace(final boolean displayName, final boolean lore,
                               @NotNull final Iterable<Placeholder> placeholders) {
        final ItemStack clone = this.itemStack.clone();
        final ItemMeta itemMeta = clone.getItemMeta();
        if (itemMeta == null) {
            return this;
        }
        if (displayName && itemMeta.hasDisplayName()) {
            for (final Placeholder placeholder : placeholders) {
                itemMeta.setDisplayName(placeholder.replace(itemMeta.getDisplayName()));
            }
        }
        if (lore && itemMeta.getLore() != null && itemMeta.hasLore()) {
            final List<String> finalLore = new ArrayList<>();
            itemMeta.getLore().forEach(s -> {
                final AtomicReference<String> finalString = new AtomicReference<>(s);
                placeholders.forEach(placeholder ->
                    finalString.set(placeholder.replace(finalString.get()))
                );
                finalLore.add(finalString.get());
            });
            itemMeta.setLore(finalLore);
        }
        clone.setItemMeta(itemMeta);
        return this.changeItemStack(clone);
    }

    public static class Provider implements Provided<FileElement> {

        @Override
        public void set(@NotNull final FileElement fileElement, @NotNull final CfgSection section, @NotNull final String path) {
            final String dot = GeneralUtilities.putDot(path);
            section.set(dot + "row", fileElement.row());
            section.set(dot + "column", fileElement.column());
            ((BkktSection) section).setItemStack(dot + "item", fileElement.getItemStack());
        }

        @NotNull
        @Override
        public Optional<FileElement> get(@NotNull final CfgSection section, @NotNull final String path) {
            final String dot = GeneralUtilities.putDot(path);
            final Optional<ItemStack> itemStackOptional = ((BkktSection) section).getItemStack(dot + "item");
            final Optional<Integer> rowOptional = section.getInteger(dot + "row");
            final Optional<Integer> columnOptional = section.getInteger(dot + "column");
            if (!itemStackOptional.isPresent() || !rowOptional.isPresent() || !columnOptional.isPresent()) {
                return Optional.empty();
            }
            return Optional.of(
                new FileElement(itemStackOptional.get(), SlotPos.of(rowOptional.get(), columnOptional.get())));
        }

    }

}
