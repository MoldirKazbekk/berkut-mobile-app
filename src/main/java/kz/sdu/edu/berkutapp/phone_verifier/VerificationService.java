package kz.sdu.edu.berkutapp.phone_verifier;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsResponse;
import com.infobip.model.SmsTextualMessage;
import kz.sdu.edu.berkutapp.model.PinVerification;
import kz.sdu.edu.berkutapp.repository.PinVerificationRepository;
import kz.sdu.edu.berkutapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService {
    private static String API_KEY = "97681c13a2fc01a479a5102f8b70b276-213c92de-8eb0-485f-beb2-5f3295e9bca7";

    private static String BASE_URL = "https://mmvpd6.api.infobip.com";

    private final PinVerificationRepository pinVerificationRepository;

    private final JwtUtil jwtUtil;

    private static Integer PINCODE_TTL_SECONDS = 60;

    public void generateOTP(String phoneNumber) {
        ApiClient apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
                .withBaseUrl(BaseUrl.from(BASE_URL))
                .build();
        SmsApi smsApi = new SmsApi(apiClient);
        PinVerification pinVerification = new PinVerification(phoneNumber, generatePinCode(), LocalDateTime.now());
        SmsTextualMessage smsMessage = new SmsTextualMessage()
                .from("BerkutApp")
                .addDestinationsItem(new SmsDestination().to("+7" + phoneNumber))
                .text("Your verification code number is: " + pinVerification.getPinCode());
        SmsAdvancedTextualRequest smsMessageRequest = new SmsAdvancedTextualRequest()
                .messages(List.of(smsMessage));
        try {
            pinVerificationRepository.save(pinVerification);
            SmsResponse smsResponse = smsApi.sendSmsMessage(smsMessageRequest).execute();
        } catch (ApiException apiException) {
            log.error(apiException.toString());
        }
    }

    public String verifyUserOTP(String code, String phoneNumber, String username) {
        var pinVerification = pinVerificationRepository.findById(phoneNumber).orElseThrow();
        if (pinVerification.getCreatedAt().plusSeconds(PINCODE_TTL_SECONDS).isAfter(LocalDateTime.now())) {
            pinVerificationRepository.delete(pinVerification);
            return jwtUtil.generateToken(username);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PIN-code has expired");
    }

    private static String generatePinCode() {
        Random random = new Random();
        // Generate a random integer between 100000 and 999999 (inclusive)
        int pinNumber = random.nextInt(900000) + 100000;
        return String.valueOf(pinNumber);
    }
}
