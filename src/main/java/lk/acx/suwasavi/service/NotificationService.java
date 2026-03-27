package lk.acx.suwasavi.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendBroadcast(String token, String title, String messageBody) {
        try {
            // Build the Firebase Cloud Message
            Message message = Message.builder()
                    .setToken(token) // The unique FCM token of the user's phone
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(messageBody)
                            .build())
                    .putData("click_action", "OPEN_REPORTS_FRAGMENT") // Custom data for the app
                    .build();

            // Send the message via Firebase
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent broadcast: " + response);

        } catch (Exception e) {
            System.err.println("Error sending FCM notification: " + e.getMessage());
        }
    }

    public void sendReportNotification(String token, String title, String body, Long reportId) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .putData("type", "NEW_REPORT")
                .putData("reportId", String.valueOf(reportId))
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}