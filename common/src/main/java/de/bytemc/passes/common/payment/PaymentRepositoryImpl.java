package de.bytemc.passes.common.payment;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.bytemc.passes.Pass;
import de.bytemc.passes.Passes;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.payment.PaymentRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class PaymentRepositoryImpl implements PaymentRepository {

    private final BiMap<String, Class<? extends Payment>> paymentClasses = HashBiMap.create();
    private Passes passes;

    public PaymentRepositoryImpl() {
        registerPayment("unknown", EmptyPayment.class);
    }

    @Override
    public void registerPayment(String type, Class<? extends Payment> clazz) {
        paymentClasses.put(type, clazz);
        if (passes == null) {
            return;
        }

        for (Pass pass : passes.passRepository().getPasses()) {
            for (PassLevel level : pass.getLevels()) {
                Set<Payment> rewards = level.getRewards();
                Set<Payment> newRewards = new HashSet<>(rewards.size());

                for (Payment reward : rewards) {
                    if (reward instanceof EmptyPayment) {
                        EmptyPayment payment = (EmptyPayment) reward;
                        if (payment.getOriginalType().equals(type)) {
                            newRewards.add(createPayment(payment.getOriginalType(), payment.getOriginalData()));
                        }
                    } else {
                        newRewards.add(reward);
                    }
                }

                level.getRewards().clear();
                level.getRewards().addAll(newRewards);
            }
        }
    }

    @Override
    public Payment createPayment(String type, Map<String, String> data) {
        Class<? extends Payment> clazz = paymentClasses.get(type);
        if (clazz == null) {
            data.put("originalType", type);
            return createPayment("unknown", data);
        }

        try {
            return clazz.getConstructor(Map.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getType(Class<? extends Payment> clazz) {
        return paymentClasses.inverse().get(clazz);
    }

    public void setPasses(Passes passes) {
        this.passes = passes;
    }
}
