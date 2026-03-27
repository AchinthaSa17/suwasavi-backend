package lk.acx.suwasavi.dto;

public class QuotationRequest {
    private Long bookingId; // Changed to Long to match Entity ID
    private double price;
    private String status; // "READY" or "READY_FOR_DELIVERY"

    public QuotationRequest() {}

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}