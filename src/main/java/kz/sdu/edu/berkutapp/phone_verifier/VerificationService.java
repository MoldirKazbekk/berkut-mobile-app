package kz.sdu.edu.berkutapp.phone_verifier;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class VerificationService {
    private String sid = "VA37e6cf4c994186457d70c74568b70de8";

    public void generateOTP(String phoneNumber) {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Verification verification = Verification.creator(
                        sid, // this is your verification sid
                        "+" + phoneNumber, //this is your Twilio verified recipient phone number
                        "sms") // this is your channel type
                .create();

        System.out.println(verification.getStatus());
        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

    }

    public String verifyUserOTP(String code, String phoneNumber) {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            sid)
                    .setTo(phoneNumber)
                    .setCode(code)
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return "Verification failed.";
        }
        return "Verification is successfull";
    }
}
