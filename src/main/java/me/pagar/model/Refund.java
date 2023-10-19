package me.pagar.model;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import me.pagar.util.JSONUtils;

/**
 *
 * @author jonatasmaxi
 */
public class Refund extends PagarMeModel<String> {


	@Expose
	private int amount;
	@Expose
	private String type;
	@Expose
	private String status;
	@Expose
	@SerializedName("charge_fee_recipient_id")
	private String chargeFeeRecipientId;
	@Expose
	@SerializedName("bank_account_id")
	private String bankAccountId;
	@Expose
	@SerializedName("transaction_id")
	private String transactionId;
	@Expose
	Map<String, Object> metadata;

	public int getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getChargeFeeRecipientId() {
		return chargeFeeRecipientId;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}



	public Collection<Refund> getAll(Integer totalPerPage, Integer page) throws PagarMeException {
		return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Refund>>() {
		}.getType());
	}

	public Collection<Refund> get() throws PagarMeException{
		return getAll(10,1);
	}

	public Collection<Refund> getByTransaction(Integer transactionId) throws PagarMeException {
		final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
				String.format("/%s", getClassName()));
		request.getParameters().put("transaction_id", transactionId);
		return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Refund>>() {
		}.getType());
	}

}
