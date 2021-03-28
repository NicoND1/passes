package de.bytemc.passes.spigot;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.bytemc.passes.icon.Icon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class BukkitIcon {

    public ItemStack createItem(Icon icon, Object... args) {
        ItemStack itemStack = new ItemStack(Material.valueOf(icon.getMaterial()), icon.getAmount(), icon.getDurability());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (icon.getRawName() != null) {
            itemMeta.setDisplayName(icon.getName(args));
        }
        if (icon.getRawDescription() != null) {
            itemMeta.setLore(Arrays.asList(icon.getDescription(args)));
        }
        String link = icon.getSkullTexture();
        if (link != null) {
            try {
                Field profileField = itemMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                String base64encoded = Base64.getEncoder().encodeToString(("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/" + link + "\"}}}").getBytes());
                profile.getProperties().put("textures", new Property("textures", base64encoded));
                profileField.set(itemMeta, profile);
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
