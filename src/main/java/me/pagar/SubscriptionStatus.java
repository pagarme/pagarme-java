package me.pagar;


public enum SubscriptionStatus {

    trialing("trialing"),
    paid("paid"),
    pending_payment("pending_payment"),
    unpaid("unpaid"),
    canceled("canceled"),
    ended("ended");

    private String status;
    SubscriptionStatus(String status){
        this.status=status;
    }

    @Override
    public String toString() {
        return status;
    }

}
