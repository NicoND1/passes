package de.bytemc.passes.common.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.bytemc.passes.icon.Icon;

import java.lang.reflect.Type;

/**
 * @author Nico_ND1
 */
public class GsonIcon implements JsonSerializer<Icon>, JsonDeserializer<Icon> {

    @Override
    public JsonElement serialize(Icon icon, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("material", icon.getMaterial());
        result.addProperty("amount", icon.getAmount());
        result.addProperty("durability", icon.getDurability());
        if (icon.getRawName() != null) {
            result.addProperty("name", icon.getRawName());
        }
        if (icon.getRawDescription() != null) {
            String[] description = icon.getRawDescription();
            JsonArray descriptionArray = new JsonArray(description.length);
            for (String s : description) {
                descriptionArray.add(s);
            }
            result.add("description", descriptionArray);
        }
        if (icon.getSkullTexture() != null) {
            result.addProperty("skullTexture", icon.getSkullTexture());
        }
        return result;
    }

    @Override
    public Icon deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        Icon.Builder iconBuilder = Icon.builder(object.get("material").getAsString())
            .amount(object.get("amount").getAsInt())
            .durability(object.get("durability").getAsShort());
        if (object.has("name")) {
            iconBuilder.name(object.get("name").getAsString());
        }
        if (object.has("description")) {
            JsonArray descriptionArray = object.getAsJsonArray("description");
            String[] description = new String[descriptionArray.size()];
            int i = 0;
            for (JsonElement element : descriptionArray) {
                description[i++] = element.getAsString();
            }
            iconBuilder.description(description);
        }
        if (object.has("skullTexture")) {
            iconBuilder.skullTexture(object.get("skullTexture").getAsString());
        }
        return iconBuilder.build();
    }
}
