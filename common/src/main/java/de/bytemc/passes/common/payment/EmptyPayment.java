package de.bytemc.passes.common.payment;

import com.google.common.util.concurrent.ListenableFuture;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.user.PassUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nico_ND1
 */
public class EmptyPayment extends Payment {

    private final String originalType;
    private final Map<String, String> originalData;

    public EmptyPayment(Map<String, String> map) {
        super(map);
        this.originalType = map.remove("originalType");
        this.originalData = map;
    }

    @Override
    public void use(PassUser passUser) {
    }

    @Override
    public ListenableFuture<Boolean> canAfford(PassUser passUser) {
        return instantFuture(true);
    }

    @Override
    public Map<String, String> serialize() {
        return new HashMap<>();
    }

    @Override
    public String toString() {
        if (originalType != null && !originalType.isEmpty()) {
            return "Bruch (eig. " + originalType + ")";
        }
        return "Nichts";
    }

    public String getOriginalType() {
        return originalType;
    }

    public Map<String, String> getOriginalData() {
        return originalData;
    }
}
