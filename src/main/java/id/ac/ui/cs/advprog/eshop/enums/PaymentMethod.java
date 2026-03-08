package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentMethod {
    VOUCHER("VOUCHER"),
    BANK_TRANSFER("BANK_TRANSFER"),
    CASH_ON_DELIVERY("CASH_ON_DELIVERY");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String param) {
        for (PaymentMethod m : PaymentMethod.values()) {
            if (m.name().equals(param) || m.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}