package me.pagar;

public enum TransactionStatus {
	local("local"),
	processing("processing"),
	waiting_payment("waiting_payment"),
	refused("refused"),
	paid("paid"),
	refunded("refunded"),
	pending_refund("pending_refund");
	
	private String status;
	TransactionStatus(String status){
		this.status=status;
	}
	
	@Override
	public String toString() {
	return status;
	}
	
}
