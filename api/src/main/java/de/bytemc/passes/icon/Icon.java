package de.bytemc.passes.icon;

import com.google.common.base.Objects;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * @author Nico_ND1
 */
public class Icon {

    private final FancyColorString name;
    private final FancyColorString[] description;
    private final Material material;
    private final short durability;
    private final Supplier<Integer> amount;

    public Icon(String name, String[] description, Material material, short durability, Supplier<Integer> amount) {
        this.name = new FancyColorString(name);
        this.description = Arrays.stream(description).map(FancyColorString::new).toArray(FancyColorString[]::new);
        this.material = material;
        this.durability = durability;
        this.amount = amount;
    }

    public String getName(char colorCode) {
        return name.getString(colorCode);
    }

    public String[] getDescription(char colorCode) {
        String[] array = new String[description.length];
        for (int i = 0; i < description.length; i++) {
            FancyColorString fancyColorString = description[i];
            array[i] = fancyColorString.getString(colorCode);
        }
        return array;
    }

    public Material getMaterial() {
        return material;
    }

    public short getDurability() {
        return durability;
    }

    public int getAmount() {
        return amount.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon = (Icon) o;
        return Objects.equal(getName('?'), icon.getName('?'));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName('?'));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Icon.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("description=" + Arrays.toString(description))
            .add("material=" + material)
            .add("durability=" + durability)
            .add("amount=" + amount)
            .toString();
    }
}
