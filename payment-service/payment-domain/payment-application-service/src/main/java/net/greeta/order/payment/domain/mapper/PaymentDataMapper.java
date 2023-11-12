package net.greeta.order.payment.domain.mapper;

import net.greeta.order.common.domain.valueobject.CustomerId;
import net.greeta.order.common.domain.valueobject.Money;
import net.greeta.order.common.domain.valueobject.OrderId;
import net.greeta.order.payment.domain.entity.Payment;
import net.greeta.order.payment.domain.event.PaymentEvent;
import net.greeta.order.payment.domain.dto.PaymentRequest;
import net.greeta.order.payment.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }

    public OrderEventPayload paymentEventToOrderEventPayload(PaymentEvent paymentEvent) {
        return OrderEventPayload.builder()
                .paymentId(paymentEvent.getPayment().getId().getValue().toString())
                .customerId(paymentEvent.getPayment().getCustomerId().getValue().toString())
                .orderId(paymentEvent.getPayment().getOrderId().getValue().toString())
                .price(paymentEvent.getPayment().getPrice().getAmount())
                .createdAt(paymentEvent.getCreatedAt())
                .paymentStatus(paymentEvent.getPayment().getPaymentStatus().name())
                .failureMessages(paymentEvent.getFailureMessages())
                .build();
    }
}
