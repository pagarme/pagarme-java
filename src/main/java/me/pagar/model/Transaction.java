package me.pagar.model;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import me.pagar.util.JSONUtils;

public class Transaction extends PagarMeModel<Integer> {

    @Expose(deserialize = false)
    private Boolean async;

    @Expose(deserialize = false)
    private Boolean capture;

    /**
     * Caso essa transação tenha sido originada na cobrança de uma assinatura, o
     * <code>id</code> desta será o valor dessa propriedade
     */
    @Expose(serialize = false)
    @SerializedName("subscription_id")
    private Integer subscriptionId;

    /**
     * Valor, em centavos, da transação
     */
    @Expose
    private Integer amount;

    /**
     * Valor refund, em centavos, da transação
     */
    @Expose
    @SerializedName("refunded_amount")
    private Integer refundedAmount;

    /**
     * Valor autorizado, em centavos, da transação
     */
    @Expose
    @SerializedName("authorized_amount")
    private Integer authorizedAmount;

    /**
     * Valor pago, em centavos, da transação
     */
    @Expose
    @SerializedName("paid_amount")
    private Integer paidAmount;

    /**
     * Número de parcelas/prestações a serem cobradas
     */
    @Expose
    private Integer installments;

    @Expose(deserialize = false)
    @SerializedName("card_id")
    private String cardId;

    @Expose(deserialize = false)
    @SerializedName("card_number")
    private String cardNumber;

    @Expose(deserialize = false)
    @SerializedName("card_holder_name")
    private String cardHolderName;

    @Expose(deserialize = false)
    @SerializedName("card_expiration_date")
    private String cardExpirationDate;

    @Expose(deserialize = false)
    @SerializedName("card_cvv")
    private String cardCvv;

    @Expose(deserialize = false)
    @SerializedName("card_emv_data")
    private String cardEmvData;

    @Expose
    @SerializedName("card_emv_response")
    private String cardEmvResponse;

    @Expose(deserialize = false)
    @SerializedName("card_pin_mode")
    private String cardPinMode;

    @Expose(deserialize = false)
    @SerializedName("card_track_1")
    private String cardTrack1;

    @Expose(deserialize = false)
    @SerializedName("card_track_2")
    private String cardTrack2;

    @Expose(deserialize = false)
    @SerializedName("card_track_3")
    private String cardTrack3;

    @Expose(deserialize = false)
    @SerializedName("card_pin")
    private String cardPin;

    @Expose(deserialize = false)
    @SerializedName("card_pin_kek")
    private String cardPinKek;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        addUnsavedProperty("cardNumber");
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        addUnsavedProperty("cardHolderName");
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
        addUnsavedProperty("cardExpirationDate");
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
        addUnsavedProperty("cardCvv");
    }

    public String getCardEmvData() {
        return cardEmvData;
    }

    public void setCardEmvData(String cardEmvData) {
        this.cardEmvData = cardEmvData;
        addUnsavedProperty("cardEmvData");
    }

    public String getCardEmvResponse() {
        return cardEmvResponse;
    }

    public void setCardEmvResponse(String cardEmvResponse) {
        this.cardEmvResponse = cardEmvResponse;
        addUnsavedProperty("cardEmvResponse");
    }

    public String getCardTrack1() {
        return cardTrack2;
    }

    public void setCardTrack1(String cardTrack1) {
        this.cardTrack1 = cardTrack1;
        addUnsavedProperty("cardTrack1");
    }

    public String getCardTrack2() {
        return cardTrack2;
    }

    public void setCardTrack2(String cardTrack2) {
        this.cardTrack2 = cardTrack2;
        addUnsavedProperty("cardTrack2");
    }

    public String getCardTrack3() {
        return cardTrack3;
    }

    public void setCardTrack3(String cardTrack3) {
        this.cardTrack3 = cardTrack3;
        addUnsavedProperty("cardTrack3");
    }

    public String getCardPinMode() {
        return cardPinMode;
    }

    public void setCardPinMode(String cardPinMode) {
        this.cardPinMode = cardPinMode;
        addUnsavedProperty("cardPinMode");
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
        addUnsavedProperty("cardPin");
    }

    public String getCardPinKek() {
        return cardPinKek;
    }

    public void setCardPinKek(String cardPinKek) {
        this.cardPinKek = cardPinKek;
        addUnsavedProperty("cardPinKek");
    }

    /**
     * Custo da transação para o lojista
     */

    @Expose(serialize = false)
    private Integer cost;

    /**
     * Mensagem de resposta do adquirente referente ao status da transação.
     */
    @Expose(serialize = false)
    @SerializedName("acquirer_response_code")
    private String acquirerResponseCode;

    /**
     * Código de autorização retornado pela bandeira.
     */
    @Expose(serialize = false)
    @SerializedName("authorization_code")
    private String authorizationCode;

    /**
     * Texto que irá aparecer na fatura do cliente depois do nome da loja.
     * <b>OBS:</b> Limite de 13 caracteres.
     */
    @Expose
    @SerializedName("soft_descriptor")
    private String softDescriptor;

    /**
     * Código que identifica a transação no adquirente.
     */
    @Expose(serialize = false)
    private String tid;

    /**
     * Código que identifica a transação no adquirente.
     */
    @Expose(serialize = false)
    private String nsu;

    /**
     * URL (endpoint) do sistema integrado a Pagar.me que receberá as respostas
     * a cada atualização do processamento da transação
     */
    @Expose
    @SerializedName("postback_url")
    private String postbackUrl;

    /**
     * URL do boleto para impressão
     */
    @Expose(serialize = false)
    @SerializedName("boleto_url")
    private String boletoUrl;

    /**
     * Código de barras do boleto gerado na transação
     */
    @Expose(serialize = false)
    @SerializedName("boleto_barcode")
    private String boletoBarcode;

    /**
     * Mostra se a transação foi criada utilizando a API Key ou Encryption Key.
     */
    @Expose(serialize = false)
    private String referer;

    /**
     * Mostra se a transação foi criada utilizando a API Key ou Encryption Key.
     */
    @Expose(serialize = false)
    private String ip;

    @Expose(deserialize = false)
    @SerializedName("card_hash")
    private String cardHash;

    /**
     * Adquirente responsável pelo processamento da transação.
     */
    @Expose(serialize = false)
    @SerializedName("acquirer_name")
    private AcquirerName acquirerName;

    /**
     * Métodos de pagamento possíveis: <code>credit_card</code>,
     * <code>boleto</code> e <code>debit_card</code>
     */
    @Expose
    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    /**
     * Métodos de captura possíveis: <code>emv</code>, <code>magstripe</code> e
     * <code>ecommerce</code>
     */
    @Expose
    @SerializedName("capture_method")
    private CaptureMethod captureMethod;

    /**
     * Para cada atualização no processamento da transação, esta propriedade
     * será alterada, e o objeto <code>transaction</code> retornado como
     * resposta através da sua URL de postback ou após o término do
     * processamento da ação atual.
     */
    @Expose(serialize = false)
    private Status status;

    /**
     * Motivo/agente responsável pela validação ou anulação da transação.
     */
    @Expose(serialize = false)
    @SerializedName(value = "status_reason")
    private StatusReason statusReason;

    /**
     * Data de expiração do boleto (em ISODate)
     */
    @Expose(deserialize = false)
    @SerializedName("boleto_expiration_date")
    private LocalDate boletoExpirationDate;

    /**
     * Data de atualização da transação no formato ISODate
     */
    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    /**
     * Objeto com dados do telefone do cliente
     */
    @Expose(serialize = false)
    private Phone phone;

    /**
     * Objeto com dados do endereço do cliente
     */
    @Expose(serialize = false)
    private Address address;

    /**
     * Objeto com dados do cliente
     */
    @Expose
    private Customer customer;

    /**
     * Objeto com dados do cartão do cliente
     */
    @Expose(serialize = false)
    private Card card;

    /**
     * Objeto com dados adicionais do cliente/produto/serviço vendido
     */
    @Expose
    private Map<String, Object> metadata;

    @Expose
    @SerializedName("antifraud_metadata")
    private Object antifraudMetadata;

    @Expose(serialize = false)
    @SerializedName("event")
    private Event event;

    @Expose(serialize = false)
    @SerializedName("old_status")
    private Status oldStatus;

    @Expose(serialize = false)
    @SerializedName("current_status")
    private Status currentStatus;

    @Expose(serialize = false)
    @SerializedName("desired_status")
    private Status desiredStatus;

    @SerializedName("refuse_reason")
    public String getRefuseReason() {
        return refuseReason;
    }

    @SerializedName("refuse_reason")
    private String refuseReason;

    @Expose(serialize = true)
    @SerializedName("split_rules")
    private Collection<SplitRule> splitRules;

    public Transaction() {
        super();
    }

    /**
     * <b>OBS:</b> Apenas para transações de <b>cartão de crédito</b> você deve
     * passar <b>ou</b> o <code>card_hash</code> <b>ou</b> o
     * <code>card_id</code>.
     *
     * @param amount
     *            Valor a ser cobrado. Deve ser passado em centavos.
     * @param cardHash
     *            Informações do cartão do cliente criptografadas no navegador.
     * @param cardId
     *            Ao realizar uma transação, retornamos o <code>card_id</code>
     *            do cartão para que nas próximas transações desse cartão possa
     *            ser utilizado esse identificador ao invés do
     *            <code>card_hash</code>
     * @param customer
     *            Dados do cliente a ser cadastrado
     */
    public Transaction(final Integer amount, final String cardHash, final String cardId, final Customer customer) {
        this();
        this.amount = amount;
        this.cardHash = cardHash;
        this.cardId = cardId;
        this.customer = customer;
    }

    public Transaction find(String id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", getClassName(), id));

        final Transaction other = JSONUtils.getAsObject((JsonObject) request.execute(), Transaction.class);
        copy(other);
        flush();

        return other;
    }

    public Transaction find(Integer id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", getClassName(), id));

        final Transaction other = JSONUtils.getAsObject((JsonObject) request.execute(), Transaction.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Transaction> findCollection(int totalPerPage, int page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Transaction>>() {
        }.getType());
    }

    /**
     * @return {@link #subscriptionId}
     */
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * @return {@link #amount}
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @return {@link #refundedAmount}
     */
    public Integer getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * @return {@link #authorizedAmount}
     */
    public Integer getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     * @return {@link #paidAmount}
     */
    public Integer getPaidAmount() {
        return paidAmount;
    }

    /**
     * @return {@link #installments}
     */
    public Integer getInstallments() {
        return installments;
    }

    /**
     * @return {@link #cost}
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * @return {@link #acquirerResponseCode}
     */
    public String getAcquirerResponseCode() {
        return acquirerResponseCode;
    }

    /**
     * @return {@link #authorizationCode}
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * @return {@link #softDescriptor}
     */
    public String getSoftDescriptor() {
        return softDescriptor;
    }

    /**
     * @return {@link #tid}
     */
    public String getTid() {
        return tid;
    }

    /**
     * @return {@link #nsu}
     */
    public String getNsu() {
        return nsu;
    }

    /**
     * @return {@link Transaction#postbackUrl}
     */
    public String getPostbackUrl() {
        return postbackUrl;
    }

    public String getBoletoUrl() {
        return boletoUrl;
    }

    /**
     * @return {@link #boletoBarcode}
     */
    public String getBoletoBarcode() {
        return boletoBarcode;
    }

    /**
     * @return {@link #referer}
     */
    public String getReferer() {
        return referer;
    }

    /**
     * @return {@link #ip}
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return {@link #acquirerName}
     */
    public AcquirerName getAcquirerName() {
        return acquirerName;
    }

    /**
     * @return {@link #paymentMethod}
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @return {@link #captureMethod}
     */
    public CaptureMethod getCaptureMethod() {
        return captureMethod;
    }

    /**
     * @return {@link #status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return {@link #statusReason}
     */
    public StatusReason getStatusReason() {
        return statusReason;
    }

    /**
     * @return {@link #updatedAt}
     */
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return {@link #metadata}
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * @return {@link #card}
     */
    public Card getCard() {
        return card;
    }

    /**
     * @return {@link #customer}
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return {@link #event}
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return {@link #oldStatus}
     */
    public Status getOldStatus() {
        return oldStatus;
    }

    /**
     * @return {@link #currentStatus}
     */
    public Status getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @return {@link #desiredStatus}
     */
    public Status getDesiredStatus() {
        return desiredStatus;
    }

    public Object getAntifraudMetadata() {
        return antifraudMetadata;
    }

    public void setAsync(final Boolean async) {
        this.async = async;
        addUnsavedProperty("async");
    }

    public void setCapture(final Boolean capture) {
        this.capture = capture;
        addUnsavedProperty("capture");
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
        addUnsavedProperty("amount");
    }

    public void setInstallments(final Integer installments) {
        this.installments = installments;
        addUnsavedProperty("installments");
    }

    public void setSoftDescriptor(final String softDescriptor) {
        this.softDescriptor = softDescriptor;
        addUnsavedProperty("softDescriptor");
    }

    public void setPostbackUrl(final String postbackUrl) {
        this.postbackUrl = postbackUrl;
        addUnsavedProperty("postbackUrl");
    }

    public void setCardHash(final String cardHash) {
        this.cardHash = cardHash;
        addUnsavedProperty("cardHash");
    }

    public void setPaymentMethod(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        addUnsavedProperty("paymentMethod");
    }

    public void setCaptureMethod(final CaptureMethod captureMethod) {
        this.captureMethod = captureMethod;
        addUnsavedProperty("captureMethod");
    }

    public void setCardId(final String cardId) {
        this.cardId = cardId;
        addUnsavedProperty("cardId");
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
        addUnsavedProperty("customer");
    }

    public void setBoletoExpirationDate(final LocalDate boletoExpirationDate) {
        this.boletoExpirationDate = boletoExpirationDate;
        addUnsavedProperty("boletoExpirationDate");
    }

    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
        addUnsavedProperty("metadata");
    }

    public void setSplitRules(final Collection<SplitRule> splitRules) {
        this.splitRules = splitRules;

        if (this.splitRules.size() != 0) {
            addUnsavedProperty("splitRules");
        }
    }

    public void setAntifraudMetadata(Object antifraudMetadata) {
        this.antifraudMetadata = antifraudMetadata;
        addUnsavedProperty("antifraud_metadata");
    }

    public Collection<SplitRule> getSplitRules() {
        return splitRules;
    }

    public Transaction save() throws PagarMeException {

        final Transaction saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    /**
     * @see #list(int, int)
     */
    public Collection<Transaction> list() throws PagarMeException {
        return list(100, 0);
    }

    /**
     * @param totalPerPage
     *            Retorna <code>n</code> objetos de transação
     * @param page
     *            Útil para implementação de uma paginação de resultados
     * @return Uma {@link Collection} contendo objetos de transações, ordenadas
     *         a partir da transação realizada mais recentemente.
     * @throws PagarMeException
     */
    public Collection<Transaction> list(int totalPerPage, int page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Transaction>>() {
        }.getType());
    }

    /**
     * Caso você queira/precise criar o card_hash manualmente, essa rota deverá
     * ser utilizada para obtenção de uma chave pública de encriptação dos dados
     * do cartão de seu cliente.
     *
     * @return Um {@link CardHashKey}
     * @throws PagarMeException
     */
    public CardHashKey cardHashKey() throws PagarMeException {
        final String cardHashKeyEndpoint = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                CardHashKey.class.getSimpleName());

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), cardHashKeyEndpoint));

        return JSONUtils.getAsObject((JsonObject) request.execute(), CardHashKey.class);
    }

    /**
     *
     * @param antifraudAnalysisId
     * @return
     * @throws PagarMeException
     */
    public AntifraudAnalysis antifraudAnalysis(final Integer antifraudAnalysisId) throws PagarMeException {
        validateId();

        final AntifraudAnalysis antifraudAnalysis = new AntifraudAnalysis();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s/%s/%s", getClassName(),
                getId(), antifraudAnalysis.getClassName(), antifraudAnalysisId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), AntifraudAnalysis.class);
    }

    /**
     * Retorna todas as {@link AntifraudAnalysis} realizadas em uma transação.
     *
     * @return Lista de {@link AntifraudAnalysis}
     * @throws PagarMeException
     */
    public Collection<AntifraudAnalysis> antifraudAnalysises() throws PagarMeException {
        validateId();

        final AntifraudAnalysis antifraudAnalysis = new AntifraudAnalysis();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), antifraudAnalysis.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<AntifraudAnalysis>>() {
        }.getType());
    }

    /**
     * Retorna um objeto {@link Payable} informando os dados de um pagamento
     * referente a uma determinada transação.
     *
     * @param payableId
     *            ID do {@link Payable}
     * @return Um {@link Payable}
     * @throws PagarMeException
     */
    public Payable payables(final Integer payableId) throws PagarMeException {
        validateId();

        final Payable splitRule = new Payable();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), splitRule.getClassName(), payableId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), Payable.class);
    }

    /**
     * Retorna um array com objetos {@link Payable} informando os dados dos
     * pagamentos referentes a uma transação.
     *
     * @return Lista de {@link Payable}
     * @throws PagarMeException
     */
    public Collection<Payable> payables() throws PagarMeException {
        validateId();

        final Payable payable = new Payable();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), payable.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Payable>>() {
        }.getType());
    }

    /**
     * Retorna um {@link Postback} específico relacionado a transação.
     *
     * @param postbackId
     *            ID do {@link Postback}
     * @return Um {@link Postback}
     * @throws PagarMeException
     */
    public Postback postbacks(final String postbackId) throws PagarMeException {
        validateId();

        final Postback postback = new Postback();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), postback.getClassName(), postbackId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), Postback.class);
    }

    /**
     * Com essa rota você pode reenviar qualquer {@link Postback} que já foi
     * enviado de uma transação. Lembrando que caso o envio de um
     * {@link Postback} falhe ou seu servidor não o receba, nós o retentamos
     * diversas vezes (com um total de 31 vezes).
     *
     * @param postbackId
     *            ID do {@link Postback}
     * @return Reenviando um {@link Postback}
     * @throws PagarMeException
     */
    public Postback postbackRedeliver(final String postbackId) throws PagarMeException {
        validateId();

        final Postback postback = new Postback();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/%s/%s/redeliver", getClassName(), getId(), postback.getClassName(), postbackId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), Postback.class);
    }

    /**
     * Retorna todos os {@link Postback} enviados relacionados a transação.
     *
     * @return Todos os {@link Postback}s
     * @throws PagarMeException
     */
    public Collection<Postback> postbacks() throws PagarMeException {
        validateId();

        final Postback postback = new Postback();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), postback.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Postback>>() {
        }.getType());
    }

    /**
     * Retorna os dados das {@link SplitRule} do valor transacionado.
     *
     * @param splitRuleId
     *            O ID da Regra de Split
     * @return Retornando uma {@link SplitRule} específica
     * @throws PagarMeException
     */
    public SplitRule splitRules(final String splitRuleId) throws PagarMeException {
        validateId();

        final SplitRule splitRule = new SplitRule();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), splitRule.getClassName(), splitRuleId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), SplitRule.class);
    }

    /**
     * Retorna os dados de uma {@link SplitRule} de uma determinada transação.
     *
     * @return Lista de {@link SplitRule} para a transação
     * @throws PagarMeException
     */
    public Collection<SplitRule> splitRules() throws PagarMeException {
        validateId();

        final SplitRule splitRule = new SplitRule();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), splitRule.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<SplitRule>>() {
        }.getType());
    }

    /**
     * Essa rota é utilizada quando se deseja estornar uma transação, realizada
     * por uma cobrança via cartão de crédito ou boleto bancário.
     * <p>
     * Em caso de estorno de uma transação realizada com cartão de crédito,
     * apenas o <code>id</code> da transação é necessário para efetivação do
     * estorno.
     * <p>
     * Caso a compra tenha sido feita por boleto bancário, você precisará passar
     * os dados da conta bancária que irá receber o valor estornado, ou o id
     * desta conta, que pode ser gerada com o modelo {@link BankAccount}.
     *
     * @throws PagarMeException
     */
    public Transaction refund(final Integer amount) throws PagarMeException {
        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/refund", getClassName(), getId()));
        request.getParameters().put("amount", amount);

        final Transaction other = JSONUtils.getAsObject((JsonObject) request.execute(), Transaction.class);
        copy(other);
        flush();

        return other;
    }

    /**
     * Você pode capturar o valor de uma transação após a autorização desta, no
     * prazo máximo de 5 dias após a autorização.
     *
     * @param amount
     * @return
     * @throws PagarMeException
     */
    public Transaction capture(final Integer amount) throws PagarMeException {

        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/capture", getClassName(), getId()));

        request.getParameters().put("amount", amount);
        request.getParameters().put("metadata", this.getMetadata());

        final Transaction other = JSONUtils.getAsObject((JsonObject) request.execute(), Transaction.class);
        copy(other);
        flush();

        return other;
    }

    /**
     * Atualiza a instância do objeto com os dados mais recentes do backend.
     *
     * @return Instância atualizada do Objeto.
     */
    public Transaction refresh() throws PagarMeException {
        final Transaction other = JSONUtils.getAsObject(refreshModel(), Transaction.class);
        copy(other);
        flush();
        return other;
    }

    private void copy(Transaction other) {
        setId(other.getId());
        this.subscriptionId = other.subscriptionId;
        this.amount = other.amount;
        this.installments = other.installments;
        this.cost = other.cost;
        this.status = other.status;
        this.statusReason = other.statusReason;
        this.acquirerName = other.acquirerName;
        this.acquirerResponseCode = other.acquirerResponseCode;
        this.authorizationCode = other.authorizationCode;
        this.softDescriptor = other.softDescriptor;
        this.tid = other.tid;
        this.nsu = other.nsu;
        this.postbackUrl = other.postbackUrl;
        this.paymentMethod = other.paymentMethod;
        this.boletoUrl = other.boletoUrl;
        this.boletoBarcode = other.boletoBarcode;
        this.referer = other.referer;
        this.ip = other.ip;
        this.cardId = other.cardId;
        this.metadata = other.metadata;
        this.card = other.card;
        this.paidAmount = other.paidAmount;
        this.refundedAmount = other.refundedAmount;
        this.authorizedAmount = other.authorizedAmount;
        this.refuseReason = other.refuseReason;
        this.antifraudMetadata = other.antifraudMetadata;
        this.setCreatedAt(other.getCreatedAt());
        this.splitRules = other.splitRules;
        this.cardEmvResponse = other.cardEmvResponse;
    }

    /**
     * Adquirente responsável pelo processamento da transação.
     */
    public enum AcquirerName {

        /**
         * em ambiente de testes
         */
        @SerializedName("development")
        DEVELOPMENT,

        /**
         * adquirente Pagar.me
         */
        @SerializedName("pagarme")
        PAGARME,

        @SerializedName("stone")
        STONE,

        @SerializedName("cielo")
        CIELO,

        @SerializedName("rede")
        REDE,

        @SerializedName("mundipagg")
        MUNDIPAGG

    }

    public enum Event {

        @SerializedName("transaction_status_changed")
        TRANSACTION_STATUS_CHANGED

    }

    /**
     * Método de pagamento
     */
    public enum PaymentMethod {

        @SerializedName("credit_card")
        CREDIT_CARD,

        @SerializedName("boleto")
        BOLETO,

        @SerializedName("debit_card")
        DEBIT_CARD
    }

    public enum CaptureMethod {

        @SerializedName("emv")
        EMV,

        @SerializedName("magstripe")
        MAGSTRIPE,

        @SerializedName("ecommerce")
        ECOMMERCE
    }

    /**
     * Quando uma transação é criada, ela inicialmente é retornada com o status
     * {@link #PROCESSING}.
     */
    public enum Status {

        /**
         * Transação sendo processada.
         */
        @SerializedName("processing")
        PROCESSING,

        /**
         * Transação autorizada. Cliente possui saldo na conta e este valor foi
         * reservado para futura captura, que deve acontecer em no máximo 5
         * dias. Caso a transação <b>não seja capturada</b>, a autorização é
         * cancelada automaticamente.
         */
        @SerializedName("authorized")
        AUTHORIZED,

        /**
         * Transação paga (autorizada e capturada).
         */
        @SerializedName("paid")
        PAID,

        /**
         * Transação estornada.
         */
        @SerializedName("refunded")
        REFUNDED,

        /**
         * Transação aguardando pagamento (status para transações criadas com
         * boleto bancário).
         */
        @SerializedName("waiting_payment")
        WAITING_PAYMENT,

        /**
         * Transação paga com boleto aguardando para ser estornada.
         */
        @SerializedName("pending_refund")
        PENDING_REFUND,

        /**
         * Transação não autorizada.
         */
        @SerializedName("refused")
        REFUSED,

        /**
         * Transação sofreu chargeback.
         */
        @SerializedName("chargedback")
        CHARGEDBACK

    }

    /**
     * Motivo/agente responsável pela validação ou anulação da transação.
     */
    public enum StatusReason {

        @SerializedName("acquirer")
        ACQUIRER,

        @SerializedName("antifraud")
        ANTIFRAUD,

        @SerializedName("internal_error")
        INTERNAL_ERROR,

        @SerializedName("no_acquirer")
        NO_ACQUIRER,

        @SerializedName("acquirer_timeout")
        ACQUIRER_TIMEOUT

    }

}
