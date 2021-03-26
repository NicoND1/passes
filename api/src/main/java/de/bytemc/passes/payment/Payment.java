package de.bytemc.passes.payment;

import com.google.common.util.concurrent.ListenableFuture;
import de.bytemc.passes.user.PassUser;

import java.util.Map;

/**
 * @author Nico_ND1
 */
public abstract class Payment {

    public Payment(Map<String, String> data) {
    }

    /**
     * Collect this to the given {@link PassUser}.
     *
     * @param passUser the user to give this to
     */
    public abstract void use(PassUser passUser);

    public abstract ListenableFuture<Boolean> canAfford(PassUser passUser);

    public abstract Map<String, String> serialize();

}
