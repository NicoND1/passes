package de.bytemc.passes.common.config;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.bytemc.passes.EventPass;
import de.bytemc.passes.Pass;
import de.bytemc.passes.common.AbstractPass;
import de.bytemc.passes.common.DefaultPass;
import de.bytemc.passes.common.EventPassImpl;
import de.bytemc.passes.common.PassType;
import de.bytemc.passes.common.UnknownPass;
import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;
import de.bytemc.passes.level.PassLevelState;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.payment.PaymentRepository;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class GsonPass implements JsonSerializer<Pass>, JsonDeserializer<Pass> {

    private final PaymentRepository payments;

    public GsonPass(PaymentRepository payments) {
        this.payments = payments;
    }

    @Override
    public JsonElement serialize(Pass pass, Type type1, JsonSerializationContext serializationContext) {
        JsonObject result = new JsonObject();
        if (!(pass instanceof AbstractPass)) {
            result.addProperty("type", PassType.DEFAULT.name());
            return result;
        }

        AbstractPass abstractPass = (AbstractPass) pass;
        PassType type = abstractPass.getType();
        result.addProperty("type", type.name());
        result.addProperty("id", abstractPass.getID());
        result.add("icon", serializationContext.serialize(pass.getIcon()));
        result.add("levels", writeLevelsJsonArray(pass));
        result.add("price", writePayment(pass.getPrice()));
        result.add("levelConfiguration", writeLevelConfiguration(pass.levelConfiguration(), serializationContext));
        if (type == PassType.EVENT) {
            writeEventPass(pass, result);
        }
        return result;
    }

    private JsonObject writePayment(Payment payment) {
        String type = payments.getType(payment);
        Map<String, String> data = payment.serialize();
        JsonObject dataObject = new JsonObject();
        for (String key : data.keySet()) {
            dataObject.addProperty(key, data.get(key));
        }

        JsonObject result = new JsonObject();
        result.addProperty("type", type);
        result.add("data", dataObject);
        return result;
    }

    private JsonArray writeLevelsJsonArray(Pass pass) {
        JsonArray array = new JsonArray();
        for (PassLevel level : pass.getLevels()) {
            JsonObject levelObject = new JsonObject();
            levelObject.addProperty("level", level.getLevel());
            levelObject.addProperty("neededExp", level.getNeededExp());

            JsonArray rewardsArray = new JsonArray();
            for (Payment reward : level.getRewards()) {
                rewardsArray.add(writePayment(reward));
            }
            levelObject.add("rewards", rewardsArray);

            array.add(levelObject);
        }
        return array;
    }

    private void writeEventPass(Pass pass, JsonObject result) {
        EventPass eventPass = (EventPass) pass;
        result.addProperty("start", eventPass.getStart().getTime());
        result.addProperty("end", eventPass.getEnd().getTime());
    }

    private JsonObject writeLevelConfiguration(PassLevelConfiguration levelConfiguration, JsonSerializationContext serializationContext) {
        JsonObject result = new JsonObject();
        for (PassLevelState value : PassLevelState.values()) {
            result.add(value.name(), serializationContext.serialize(levelConfiguration.getIcon(value), Icon.class));
        }
        return result;
    }

    @Override
    public Pass deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext deserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        PassType passType = PassType.valueOf(object.get("type").getAsString());
        if (passType == PassType.UNKNOWN) {
            return new UnknownPass();
        }

        int id = object.get("id").getAsInt();
        Icon icon = deserializationContext.deserialize(object.get("icon"), Icon.class);
        Set<PassLevel> levels = readLevelsJsonArray(object.getAsJsonArray("levels"));
        PassLevelConfiguration levelConfiguration = readLevelConfiguration(object.getAsJsonObject("levelConfiguration"), deserializationContext);
        Payment price = readPayment(object.getAsJsonObject("price"));

        switch (passType) {
            case DEFAULT:
                return new DefaultPass(id, icon, levels, levelConfiguration, price);
            case EVENT:
                Date start = new Date(object.get("start").getAsLong());
                Date end = new Date(object.get("end").getAsLong());
                return new EventPassImpl(id, icon, start, end, levels, levelConfiguration, price);
        }

        return new UnknownPass();
    }

    private Payment readPayment(JsonObject object) {
        String type = object.get("type").getAsString();

        JsonObject dataObject = object.getAsJsonObject("data");
        Map<String, String> data = new HashMap<>();
        for (String key : dataObject.keySet()) {
            data.put(key, dataObject.get(key).getAsString());
        }

        return payments.createPayment(type, data);
    }

    private Set<PassLevel> readLevelsJsonArray(JsonArray array) {
        Set<PassLevel> levels = Sets.newHashSet();
        for (JsonElement jsonElement : array) {
            JsonObject object = jsonElement.getAsJsonObject();
            int level = object.get("level").getAsInt();
            double neededExp = object.get("neededExp").getAsDouble();
            Set<Payment> rewards = new HashSet<>();
            JsonArray rewardsArray = object.getAsJsonArray("rewards");
            for (JsonElement element : rewardsArray) {
                rewards.add(readPayment(element.getAsJsonObject()));
            }

            levels.add(new PassLevel(level, neededExp, rewards));
        }
        return levels;
    }

    private PassLevelConfiguration readLevelConfiguration(JsonObject object, JsonDeserializationContext deserializationContext) {
        Map<PassLevelState, Icon> map = new HashMap<>();
        for (String stateName : object.keySet()) {
            PassLevelState state = PassLevelState.valueOf(stateName);
            map.put(state, deserializationContext.deserialize(object.get(stateName), Icon.class));
        }
        return new PassLevelConfiguration(map);
    }

}
