package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus s : PaymentStatus.values()) {
            if (s.name().equals(param) || s.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}