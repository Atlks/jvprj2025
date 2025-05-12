package util.model.openbank;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
@Data
public class OpenBankingEvent {
    private String eventId;
    private Instant eventTime;
    private OpenBankingEventType eventType;
    private String source;
    private Map<String, String> subject; // e.g., account_id, transaction_id
    private Map<String, Object> data;    // optional additional payload



    public static void main(String[] args) {
        OpenBankingEvent event = new OpenBankingEvent();
        event.setEventId("evt-001");
        event.setEventTime(Instant.now());
        event.setEventType(OpenBankingEventType.TRANSACTION_POSTED);
        event.setSource("bank-api.openbanking.uk");
        event.setSubject(Map.of("account_id", "123456", "transaction_id", "TX7890"));
        event.setData(Map.of("amount", 250.75, "currency", "GBP"));

        System.out.println(event);
    }

    // Getters and Setters

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public OpenBankingEventType getEventType() {
        return eventType;
    }

    public void setEventType(OpenBankingEventType eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, String> getSubject() {
        return subject;
    }

    public void setSubject(Map<String, String> subject) {
        this.subject = subject;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OpenBankingEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventTime=" + eventTime +
                ", eventType=" + eventType +
                ", source='" + source + '\'' +
                ", subject=" + subject +
                ", data=" + data +
                '}';
    }
}

