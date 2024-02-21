package kz.sdu.edu.berkutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pin_verification")
@AllArgsConstructor
@NoArgsConstructor
public class PinVerification {
    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public PinVerification(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
        this.createdAt = LocalDateTime.now();
    }
}
