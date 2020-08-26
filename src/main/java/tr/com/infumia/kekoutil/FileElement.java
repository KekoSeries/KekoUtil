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
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.event.abs.ClickEvent;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.kekoutil.element.FileElementBasic;
import tr.com.infumia.kekoutil.util.PlaceType;
import tr.com.infumia.kekoutil.util.Placeholder;

public interface FileElement {

    @NotNull
    static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                            @NotNull final Map<String, Object> objects,
                            @NotNull final List<Consumer<ClickEvent>> events) {
        return new FileElementBasic(itemStack, placeType, objects, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                            @NotNull final Map<String, Object> objects,
                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, placeType, objects, Arrays.asList(events));
    }

    @NotNull
    static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                            @NotNull final List<Consumer<ClickEvent>> events) {
        return FileElement.from(itemStack, placeType, new HashMap<>(), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, placeType, Arrays.asList(events));
    }

    @SafeVarargs
    @NotNull
    static FileElement none(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.NONE, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement none(@NotNull final ItemStackBuilder builder, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.none(builder.itemStack(), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement none(@NotNull final Material material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.none(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement none(@NotNull final XMaterial material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.none(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement slots(@NotNull final ItemStack itemStack, @NotNull final List<Integer> slots,
                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.SLOTS, PlaceType.SLOTS.parse(slots), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement slots(@NotNull final ItemStackBuilder builder, @NotNull final List<Integer> slots,
                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.slots(builder.itemStack(), slots, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement slots(@NotNull final Material material, @NotNull final List<Integer> slots,
                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.slots(ItemStackBuilder.from(material), slots, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement slots(@NotNull final XMaterial material, @NotNull final List<Integer> slots,
                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.slots(ItemStackBuilder.from(material), slots, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insertIndex(@NotNull final ItemStack itemStack, final int index,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.INSERT, PlaceType.INSERT.parse(index), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insertIndex(@NotNull final ItemStackBuilder builder, final int index,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insertIndex(builder.itemStack(), index, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insertIndex(@NotNull final Material material, final int index,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insertIndex(ItemStackBuilder.from(material), index, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insertIndex(@NotNull final XMaterial material, final int index,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insertIndex(ItemStackBuilder.from(material), index, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insert(@NotNull final ItemStack itemStack, final int row, final int column,
                              @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.INSERT, PlaceType.INSERT.parse(row, column), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insert(@NotNull final ItemStackBuilder builder, final int row, final int column,
                              @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insert(builder.itemStack(), row, column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insert(@NotNull final Material material, final int row, final int column,
                              @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insert(ItemStackBuilder.from(material), row, column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement insert(@NotNull final XMaterial material, final int row, final int column,
                              @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.insert(ItemStackBuilder.from(material), row, column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fill(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fill(@NotNull final ItemStackBuilder builder,
                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fill(builder.itemStack(), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fill(@NotNull final Material material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fill(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fill(@NotNull final XMaterial material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fill(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRow(@NotNull final ItemStack itemStack, final int row,
                               @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_ROW, PlaceType.FILL_ROW.parse(row), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRow(@NotNull final ItemStackBuilder builder, final int row,
                               @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRow(builder.itemStack(), row, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRow(@NotNull final Material material, final int row,
                               @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRow(ItemStackBuilder.from(material), row, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRow(@NotNull final XMaterial material, final int row,
                               @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRow(ItemStackBuilder.from(material), row, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillColumn(@NotNull final ItemStack itemStack, final int column,
                                  @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_COLUMN, PlaceType.FILL_COLUMN.parse(column), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillColumn(@NotNull final ItemStackBuilder builder, final int column,
                                  @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillColumn(builder.itemStack(), column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillColumn(@NotNull final Material material, final int column,
                                  @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillColumn(ItemStackBuilder.from(material), column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillColumn(@NotNull final XMaterial material, final int column,
                                  @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillColumn(ItemStackBuilder.from(material), column, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillBorders(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_BORDERS, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillBorders(@NotNull final ItemStackBuilder builder,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillBorders(builder.itemStack(), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillBorders(@NotNull final Material material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillBorders(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillBorders(@NotNull final XMaterial material, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillBorders(ItemStackBuilder.from(material), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectIndex(@NotNull final ItemStack itemStack, final int fromIndex, final int toIndex,
                                     @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_RECT_INDEX,
            PlaceType.FILL_RECT_INDEX.parse(fromIndex, toIndex), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectIndex(@NotNull final ItemStackBuilder builder, final int fromIndex, final int toIndex,
                                     @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectIndex(builder.itemStack(), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectIndex(@NotNull final Material material, final int fromIndex, final int toIndex,
                                     @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectIndex(@NotNull final XMaterial material, final int fromIndex, final int toIndex,
                                     @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectFromTo(@NotNull final ItemStack itemStack, final int fromRow, final int fromColumn,
                                      final int toRow, final int toColumn,
                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_RECT_FROM_TO,
            PlaceType.FILL_RECT_FROM_TO.parse(fromRow, fromColumn, toRow, toColumn), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectFromTo(@NotNull final ItemStackBuilder builder, final int fromRow, final int fromColumn,
                                      final int toRow, final int toColumn,
                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectFromTo(builder.itemStack(), fromRow, fromColumn, toRow, toColumn, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectFromTo(@NotNull final Material material, final int fromRow, final int fromColumn,
                                      final int toRow, final int toColumn,
                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRectFromTo(@NotNull final XMaterial material, final int fromRow, final int fromColumn,
                                      final int toRow, final int toColumn,
                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRectFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareIndex(@NotNull final ItemStack itemStack, final int fromIndex, final int toIndex,
                                       @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_SQUARE_INDEX,
            PlaceType.FILL_SQUARE_INDEX.parse(fromIndex, toIndex), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareIndex(@NotNull final ItemStackBuilder builder, final int fromIndex, final int toIndex,
                                       @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareIndex(builder.itemStack(), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareIndex(@NotNull final Material material, final int fromIndex, final int toIndex,
                                       @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareIndex(@NotNull final XMaterial material, final int fromIndex, final int toIndex,
                                       @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareFromTo(@NotNull final ItemStack itemStack, final int fromRow, final int fromColumn,
                                        final int toRow, final int toColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_SQUARE_FROM_TO,
            PlaceType.FILL_SQUARE_FROM_TO.parse(fromRow, fromColumn, toRow, toColumn), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareFromTo(@NotNull final ItemStackBuilder builder, final int fromRow,
                                        final int fromColumn, final int toRow, final int toColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareFromTo(builder.itemStack(), fromRow, fromColumn, toRow, toColumn, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareFromTo(@NotNull final Material material, final int fromRow, final int fromColumn,
                                        final int toRow, final int toColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillSquareFromTo(@NotNull final XMaterial material, final int fromRow, final int fromColumn,
                                        final int toRow, final int toColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillSquareFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                   @NotNull final String[] pattern,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_PATTERN,
            PlaceType.FILL_PATTERN.parse(wrapAround, pattern), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                   @NotNull final String[] pattern, @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPattern(builder.itemStack(), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPattern(@NotNull final Material material, final boolean wrapAround,
                                   @NotNull final String[] pattern,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                   @NotNull final String[] pattern,
                                   @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStartIndex(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startIndex,
                                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START_INDEX,
            PlaceType.FILL_PATTERN_START_INDEX.parse(wrapAround, pattern, startIndex), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStartIndex(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startIndex,
                                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStartIndex(builder.itemStack(), wrapAround, pattern, startIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startIndex,
                                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern, startIndex,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startIndex,
                                             @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern, startIndex,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                        @NotNull final String[] pattern, final int startRow, final int startColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START,
            PlaceType.FILL_PATTERN_START.parse(wrapAround, pattern, startRow, startColumn), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                        @NotNull final String[] pattern, final int startRow, final int startColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStart(builder.itemStack(), wrapAround, pattern, startRow, startColumn, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStart(@NotNull final Material material, final boolean wrapAround,
                                        @NotNull final String[] pattern, final int startRow, final int startColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                        @NotNull final String[] pattern, final int startRow, final int startColumn,
                                        @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                            @NotNull final String[] pattern,
                                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN,
            PlaceType.FILL_REPEATING_PATTERN.parse(wrapAround, pattern), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                            @NotNull final String[] pattern,
                                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPattern(builder.itemStack(), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPattern(@NotNull final Material material, final boolean wrapAround,
                                            @NotNull final String[] pattern,
                                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                            @NotNull final String[] pattern,
                                            @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startIndex,
                                                      final int endIndex,
                                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START_INDEX,
            PlaceType.FILL_REPEATING_PATTERN_START_INDEX.parse(wrapAround, pattern, startIndex, endIndex), events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startIndex,
                                                      final int endIndex,
                                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStartIndex(builder.itemStack(), wrapAround, pattern, startIndex,
            endIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startIndex,
                                                      final int endIndex,
                                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
            startIndex, endIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startIndex,
                                                      final int endIndex,
                                                      @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
            startIndex, endIndex, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                 @NotNull final String[] pattern, final int startRow,
                                                 final int startColumn, final int endRow, final int endColumn,
                                                 @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START,
            PlaceType.FILL_REPEATING_PATTERN_START.parse(wrapAround, pattern, startRow, startColumn, endRow, endColumn),
            events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                 @NotNull final String[] pattern, final int startRow,
                                                 final int startColumn, final int endRow, final int endColumn,
                                                 @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStart(builder.itemStack(), wrapAround, pattern, startRow, startColumn,
            endRow, endColumn, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStart(@NotNull final Material material, final boolean wrapAround,
                                                 @NotNull final String[] pattern, final int startRow,
                                                 final int startColumn, final int endRow, final int endColumn,
                                                 @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
            startRow, startColumn, endRow, endColumn, events);
    }

    @SafeVarargs
    @NotNull
    static FileElement fillRepeatingPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                                 @NotNull final String[] pattern, final int startRow,
                                                 final int startColumn, final int endRow, final int endColumn,
                                                 @NotNull final Consumer<ClickEvent>... events) {
        return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
            startRow, startColumn, endRow, endColumn, events);
    }

    default void place(@NotNull final InventoryContents contents) {
        this.type().place(this.clickableItem(), contents, this.objects().values().toArray());
    }

    default void set(@NotNull final InventoryContents contents, final int row, final int column) {
        contents.set(row, column, this.clickableItem());
    }

    @NotNull
    default Optional<Object> object(@NotNull final String key) {
        return Optional.ofNullable(this.objects().get(key));
    }

    @NotNull
    default Icon clickableItem() {
        final Icon icon = Icon.from(this.itemStack());
        this.events().forEach(icon::whenClick);
        return icon;
    }

    default FileElement addEvent(@NotNull final Consumer<ClickEvent> event) {
        final List<Consumer<ClickEvent>> events = new ArrayList<>(this.events());
        events.add(event);
        return this.changeEvent(events);
    }

    @NotNull
    default FileElement changeMaterial(@NotNull final Material material) {
        final ItemStack clone = this.itemStack().clone();
        clone.setType(material);
        return this.changeItemStack(clone);
    }

    @NotNull
    default FileElement changeMaterial(@NotNull final XMaterial xmaterial) {
        final ItemStack clone = this.itemStack().clone();
        Optional.ofNullable(xmaterial.parseMaterial()).ifPresent(clone::setType);
        return this.changeItemStack(clone);
    }

    @NotNull
    default FileElement changeName(final boolean colored, @NotNull final String name) {
        return this.changeItemStack(ItemStackBuilder.from(this.itemStack())
            .name(name, colored)
            .itemStack());
    }

    @NotNull
    default FileElement changeName(@NotNull final String name) {
        return this.changeName(true, name);
    }

    @NotNull
    default FileElement changeLore(final boolean colored, @NotNull final List<String> lore) {
        return this.changeItemStack(ItemStackBuilder.from(this.itemStack())
            .lore(lore, colored)
            .itemStack());
    }

    @NotNull
    default FileElement changeLore(final boolean colored, @NotNull final String... lore) {
        return this.changeLore(colored, Arrays.asList(lore));
    }

    @NotNull
    default FileElement changeLore(@NotNull final List<String> lore) {
        return this.changeLore(true, lore);
    }

    @NotNull
    default FileElement changeLore(@NotNull final String... lore) {
        return this.changeLore(true, lore);
    }

    @NotNull
    default FileElement replace(@NotNull final String regex, @NotNull final Object replace) {
        return this.replace(true, true, regex, replace);
    }

    @NotNull
    default FileElement replace(final boolean name, final boolean lore, @NotNull final String regex,
                                @NotNull final Object replace) {
        return this.replace(name, lore, Placeholder.from(regex, replace));
    }

    @NotNull
    default FileElement replace(@NotNull final Placeholder... placeholders) {
        return this.replace(true, true, placeholders);
    }

    @NotNull
    default FileElement replace(final boolean name, final boolean lore, @NotNull final Placeholder... placeholders) {
        return this.replace(name, lore, Arrays.asList(placeholders));
    }

    @NotNull
    default FileElement replace(@NotNull final Iterable<Placeholder> placeholders) {
        return this.replace(true, true, placeholders);
    }

    @NotNull
    default FileElement replace(final boolean name, final boolean lore,
                                @NotNull final Iterable<Placeholder> placeholders) {
        final ItemStack clone = this.itemStack().clone();
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

    @NotNull
    ItemStack itemStack();

    @NotNull
    List<Consumer<ClickEvent>> events();

    @NotNull
    PlaceType type();

    @NotNull
    Map<String, Object> objects();

    @NotNull
    FileElement changeItemStack(@NotNull ItemStack itemStack);

    @NotNull
    FileElement changeType(@NotNull PlaceType type);

    @NotNull
    FileElement changeObjects(@NotNull Map<String, Object> objects);

    FileElement changeEvent(@NotNull List<Consumer<ClickEvent>> event);

}
