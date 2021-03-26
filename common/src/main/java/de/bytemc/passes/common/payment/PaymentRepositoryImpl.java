package de.bytemc.passes.common.payment;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.payment.PaymentRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Nico_ND1
 */
public class PaymentRepositoryImpl implements PaymentRepository {

    private final BiMap<String, Class<? extends Payment>> paymentClasses = HashBiMap.create();

    public PaymentRepositoryImpl() {
        registerPayment("unknown", EmptyPayment.class);
    }

    @Override
    public void registerPayment(String type, Class<? extends Payment> clazz) {
        paymentClasses.put(type, clazz);
    }

    @Override
    public Payment createPayment(String type, Map<String, String> data) {
        Class<? extends Payment> clazz = paymentClasses.get(type);
        if (clazz == null) {
            return createPayment("unknown", data);
        }

        try {
            return clazz.getConstructor(data.getClass()).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return createPayment("unknown", data);
    }

    @Override
    public String getType(Class<? extends Payment> clazz) {
        return paymentClasses.inverse().get(clazz);
    }
}
