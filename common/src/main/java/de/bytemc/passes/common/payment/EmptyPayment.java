package de.bytemc.passes.common.payment;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.user.PassUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nico_ND1
 */
public class EmptyPayment extends Payment {
    public EmptyPayment() {
        super(new HashMap<>());
    }

    @Override
    public void use(PassUser passUser) {
    }

    @Override
    public ListenableFuture<Boolean> canAfford(PassUser passUser) {
        SettableFuture<Boolean> result = SettableFuture.create();
        result.set(true);
        return result;
    }

    @Override
    public Map<String, String> serialize() {
        return new HashMap<>();
    }
}
