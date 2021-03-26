package de.bytemc.passes.payment;

import java.util.Map;

/**
 * @author Nico_ND1
 */
public interface PaymentRepository {

    void registerPayment(String type, Class<? extends Payment> clazz);

    Payment createPayment(String type, Map<String, String> data);

    String getType(Class<? extends Payment> clazz);

    default String getType(Payment payment) {
        return getType(payment.getClass());
    }

}
