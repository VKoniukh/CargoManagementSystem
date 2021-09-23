package ua.koniukh.cargomanagementsystem.model;

public enum Route {
    KYIV(1), LVIV(2), DNIPRO(2), ODESSA(2), KHARKIV(2),
    STAROKONSTANTINOV(3), DUBNO(3), BRODY(3), KALUSH(3), UMAN(3);;

    private int deliveryZone;

    Route(int deliveryZone) {
        this.deliveryZone = deliveryZone;
    }

    public int getDeliveryZone() {
        return deliveryZone;
    }
}
