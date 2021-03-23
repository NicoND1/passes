package de.bytemc.passes.icon;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class Icon {

    private final FancyColorString name;
    private final FancyColorString[] description;
    private final String material;
    private final short durability;
    private final int amount;
    private final String skullTexture;

    private Icon(String material, short durability, int amount, String name, String[] description, String skullTexture) {
        if (name == null) {
            this.name = null;
        } else {
            this.name = new FancyColorString(name);
        }
        if (description == null) {
            this.description = null;
        } else {
            this.description = Arrays.stream(description).map(FancyColorString::new).toArray(FancyColorString[]::new);
        }
        this.material = material;
        this.durability = durability;
        this.amount = amount;
        this.skullTexture = skullTexture;
    }

    public String getName(char colorCode) {
        if (name == null) {
            return null;
        }
        return name.getString(colorCode);
    }

    public String getRawName() {
        if (name == null) {
            return null;
        }
        return name.getRawString();
    }

    public String[] getDescription(char colorCode) {
        if (description == null) {
            return null;
        }
        String[] array = new String[description.length];
        for (int i = 0; i < description.length; i++) {
            FancyColorString fancyColorString = description[i];
            array[i] = fancyColorString.getString(colorCode);
        }
        return array;
    }

    public String[] getRawDescription() {
        if (description == null) {
            return null;
        }
        String[] array = new String[description.length];
        for (int i = 0; i < description.length; i++) {
            array[i] = description[i].getRawString();
        }
        return array;
    }

    public String getMaterial() {
        return material;
    }

    public short getDurability() {
        return durability;
    }

    public int getAmount() {
        return amount;
    }

    public String getSkullTexture() {
        return skullTexture;
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
            .add("material=" + material)
            .add("name='" + name + "'")
            .add("description=" + Arrays.toString(description))
            .add("durability=" + durability)
            .add("amount=" + amount)
            .toString();
    }

    public static Builder builder(String material) {
        return new Builder(material);
    }

    public static class Builder {

        private final String material;
        private short durability;
        private int amount;
        private String name;
        private String[] description;
        private String skullTexture;

        private Builder(String material) {
            this.material = material;
        }

        public Builder durability(short durability) {
            this.durability = durability;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String... description) {
            this.description = description;
            return this;
        }

        public Builder skullTexture(String skullTexture) {
            this.skullTexture = skullTexture;
            return this;
        }

        public Icon build() {
            return new Icon(material, durability, amount, name, description, skullTexture);
        }

    }

}
