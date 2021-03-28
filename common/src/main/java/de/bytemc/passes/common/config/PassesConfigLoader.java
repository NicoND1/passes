package de.bytemc.passes.common.config;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bytemc.passes.Pass;
import de.bytemc.passes.common.DefaultPass;
import de.bytemc.passes.common.EventPassImpl;
import de.bytemc.passes.common.payment.EmptyPayment;
import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;
import de.bytemc.passes.level.PassLevelState;
import de.bytemc.passes.milestone.DefaultMilestones;
import de.bytemc.passes.milestone.Milestone;
import de.bytemc.passes.payment.PaymentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class PassesConfigLoader {

    private static Gson gson;
    private static final Set<PassLevel> DEFAULT_LEVELS = Sets.newHashSet(new PassLevel(1, 2.0, Sets.newHashSet()), new PassLevel(2, 5.0, Sets.newHashSet()));
    private static final PassLevelConfiguration DEFAULT_LEVEL_CONFIGURATION = new PassLevelConfiguration(
        new HashMap<PassLevelState, Icon>() {{
            put(PassLevelState.OPEN, Icon.builder("STICK").build());
            put(PassLevelState.CLAIMABLE, Icon.builder("DIAMOND").build());
            put(PassLevelState.CLAIMED, Icon.builder("COAL").build());
        }}
    );
    private static final PassesConfig DEFAULT_CONFIG = new PassesConfig(
        false,
        Sets.newHashSet(
            new DefaultPass(0, Icon.builder("STONE").build(), DEFAULT_LEVELS, DEFAULT_LEVEL_CONFIGURATION, new EmptyPayment(new HashMap<>())),
            new EventPassImpl(1, Icon.builder("STONE").build(), new Date(0L), new Date(), DEFAULT_LEVELS, DEFAULT_LEVEL_CONFIGURATION, new EmptyPayment(new HashMap<>()))
        ),
        Sets.newHashSet(new Milestone(DefaultMilestones.ROUND, 1.0D))
    );

    public static void init(PaymentRepository paymentRepository) {
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Pass.class, new GsonPass(paymentRepository))
            .registerTypeAdapter(Icon.class, new GsonIcon())
            .create();
    }

    public static PassesConfig readConfig(File file) throws IOException {
        if (!file.exists()) {
            saveConfig(file, DEFAULT_CONFIG);
            return DEFAULT_CONFIG;
        }

        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        return gson.fromJson(reader, PassesConfig.class);
    }

    public static void saveConfig(File file, PassesConfig config) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        String jsonString = gson.toJson(config);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonString);
            fileWriter.flush();
        }
    }

}
