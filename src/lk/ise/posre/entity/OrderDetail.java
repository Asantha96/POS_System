package lk.ise.posre.entity;

public class OrderDetail {
    private String code;
    private String orderId;
    private double unitPrice;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(String code, String orderId, double unitPrice, int qty) {
        this.setCode(code);
        this.setOrderId(orderId);
        this.setUnitPrice(unitPrice);
        this.setQty(qty);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
