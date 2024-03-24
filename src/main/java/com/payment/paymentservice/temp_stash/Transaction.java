package com.payment.paymentservice.temp_stash;

import com.payment.paymentservice.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class Transaction {
    /*TODO
     * add more security info, such as userAgent, ip address,
     * related transactions
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @OneToOne
    @JoinColumn(name = "user_source_id")
    private User sourceAccountId;

    @Column(name = "created_at")
    private Instant created_at = Instant.now();

    @OneToOne
    @JoinColumn(name = "user_destination_id")
    private User destinationAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
