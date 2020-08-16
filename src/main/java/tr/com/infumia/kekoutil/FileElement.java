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

import com.cryptomorin.xseries.XMaterial;
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
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class FileElement {

    @NotNull
    private final ItemStack itemStack;

    @NotNull
    private final SlotPos position;

    @NotNull
    private final Consumer<ClickEvent> clickEvent;

    // With item.

    @NotNull
    public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final SlotPos position,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return new FileElement(itemStack, position, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStack itemStack, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return new FileElement(itemStack, SlotPos.of(row, column), clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final SlotPos position) {
        return FileElement.from(itemStack, position, event -> {
        });
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStack itemStack, final int row, final int column) {
        return FileElement.from(itemStack, row, column, event -> {
        });
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStack itemStack) {
        return FileElement.from(itemStack, SlotPos.of(0, 0));
    }

    // With builder.

    @NotNull
    public static FileElement from(@NotNull final ItemStackBuilder builder, @NotNull final SlotPos position,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(builder.itemStack(), position, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStackBuilder builder, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(builder.itemStack(), row, column, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStackBuilder builder, @NotNull final SlotPos position) {
        return FileElement.from(builder.itemStack(), position);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStackBuilder builder, final int row, final int column) {
        return FileElement.from(builder.itemStack(), row, column);
    }

    @NotNull
    public static FileElement from(@NotNull final ItemStackBuilder builder) {
        return FileElement.from(builder.itemStack());
    }

    // With material.

    @NotNull
    public static FileElement from(@NotNull final Material material, @NotNull final SlotPos position,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(ItemStackBuilder.from(material), position, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final Material material, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(ItemStackBuilder.from(material), row, column, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final Material material, @NotNull final SlotPos position) {
        return FileElement.from(ItemStackBuilder.from(material), position);
    }

    @NotNull
    public static FileElement from(@NotNull final Material material, final int row, final int column) {
        return FileElement.from(ItemStackBuilder.from(material), row, column);
    }

    @NotNull
    public static FileElement from(@NotNull final Material material) {
        return FileElement.from(ItemStackBuilder.from(material));
    }

    // With Xmaterial.

    @NotNull
    public static FileElement from(@NotNull final XMaterial material, @NotNull final SlotPos position,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(ItemStackBuilder.from(material), position, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final XMaterial material, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent> clickEvent) {
        return FileElement.from(ItemStackBuilder.from(material), row, column, clickEvent);
    }

    @NotNull
    public static FileElement from(@NotNull final XMaterial material, @NotNull final SlotPos position) {
        return FileElement.from(ItemStackBuilder.from(material), position);
    }

    @NotNull
    public static FileElement from(@NotNull final XMaterial material, final int row, final int column) {
        return FileElement.from(ItemStackBuilder.from(material), row, column);
    }

    @NotNull
    public static FileElement from(@NotNull final XMaterial material) {
        return FileElement.from(ItemStackBuilder.from(material));
    }

    @NotNull
    public ItemStack itemStack() {
        return this.itemStack;
    }

    @NotNull
    public SlotPos position() {
        return this.position;
    }

    public int row() {
        return this.position().getRow();
    }

    public int column() {
        return this.position().getColumn();
    }

    @NotNull
    public Consumer<ClickEvent> clickEvent() {
        return this.clickEvent;
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
        return FileElement.from(this.itemStack, this.row(), column, this.clickEvent);
    }

    @NotNull
    public FileElement changeRow(final int row) {
        return FileElement.from(this.itemStack, row, this.column(), this.clickEvent);
    }

    @NotNull
    public FileElement changeItemStack(@NotNull final ItemStack itemStack) {
        return FileElement.from(itemStack, this.position, this.clickEvent);
    }

    public FileElement changeClickEvent(@NotNull final Consumer<ClickEvent> consumer) {
        return FileElement.from(this.itemStack, this.position, consumer);
    }

    @NotNull
    public FileElement changeMaterial(@NotNull final Material material) {
        final ItemStack clone = this.itemStack.clone();
        clone.setType(material);
        return this.changeItemStack(clone);
    }

    @NotNull
    public FileElement changeMaterial(@NotNull final XMaterial xmaterial) {
        final ItemStack clone = this.itemStack.clone();
        Optional.ofNullable(xmaterial.parseMaterial()).ifPresent(clone::setType);
        return this.changeItemStack(clone);
    }

    @NotNull
    public FileElement changeName(@NotNull final String name) {
        return FileElement.from(ItemStackBuilder.from(this.itemStack).name(name), this.position, this.clickEvent);
    }

    @NotNull
    public FileElement changeLore(@NotNull final List<String> lore) {
        return FileElement.from(ItemStackBuilder.from(this.itemStack).lore(lore), this.position, this.clickEvent);
    }

    @NotNull
    public FileElement replace(final boolean name, final boolean lore, @NotNull final Placeholder... placeholders) {
        return this.replace(name, lore, Arrays.asList(placeholders));
    }

    @NotNull
    public FileElement replace(final boolean name, final boolean lore,
                               @NotNull final Iterable<Placeholder> placeholders) {
        final ItemStack clone = this.itemStack.clone();
        Optional.ofNullable(clone.getItemMeta()).ifPresent(itemMeta -> {
            if (name && itemMeta.hasDisplayName()) {
                placeholders.forEach(placeholder ->
                    itemMeta.setDisplayName(placeholder.replace(itemMeta.getDisplayName())));
            }
            if (lore && itemMeta.getLore() != null && itemMeta.hasLore()) {
                final List<String> finallore = new ArrayList<>();
                itemMeta.getLore().forEach(s -> {
                    final AtomicReference<String> finalstring = new AtomicReference<>(s);
                    placeholders.forEach(placeholder ->
                        finalstring.set(placeholder.replace(finalstring.get())));
                    finallore.add(finalstring.get());
                });
                itemMeta.setLore(finallore);
            }
            clone.setItemMeta(itemMeta);
        });
        return this.changeItemStack(clone);
    }

    public static class Provider implements Provided<FileElement> {

        @Override
        public void set(@NotNull final FileElement fileElement, @NotNull final CfgSection section, @NotNull final String path) {
            final String dot = GeneralUtilities.putDot(path);
            section.set(dot + "row", fileElement.row());
            section.set(dot + "column", fileElement.column());
            ((BkktSection) section).setItemStack(dot + "item", fileElement.itemStack());
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
                FileElement.from(itemStackOptional.get(), SlotPos.of(rowOptional.get(), columnOptional.get())));
        }

    }

}
