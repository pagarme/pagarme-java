package me.pagar.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDate;
import me.pagar.util.JSONUtils;

import javax.ws.rs.HttpMethod;

public class Customer extends PagarMeModel<Integer>{

    @Expose
    @SerializedName("document_number")
    private String documentNumber;

    @Expose(serialize = false)
    @SerializedName("document_type")
    private String documentType;

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String gender;

    @Expose
    @SerializedName("born_at")
    private LocalDate bornAt;

    @Expose
    private Phone phone;

    @Expose
    private Address address;

    @Expose
    private Collection<Address> addresses;

    @Expose
    private Collection<Phone> phones;

    public Customer() {
        super();
    }

    public Customer(final String name, final String email) {
        this();
        this.name = name;
        this.email = email;
        this.addresses = new ArrayList<Address>();
        this.address = new Address();
        this.phones = new ArrayList<Phone>();
        this.phone = new Phone();
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBornAt() {
        return bornAt;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public Collection<Phone> getPhones() {
        return phones;
    }

    public void setDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;
        addUnsavedProperty("documentNumber");
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
        addUnsavedProperty("documentType");
    }

    public void setName(final String name) {
        this.name = name;
        addUnsavedProperty("name");
    }

    public void setEmail(final String email) {
        this.email = email;
        addUnsavedProperty("email");
    }

    public void setGender(final String gender) {
        this.gender = gender;
        addUnsavedProperty("gender");
    }

    public void setBornAt(final LocalDate bornAt) {
        this.bornAt = bornAt;
        if (bornAt != null) {
            addUnsavedProperty("bornAt");
        }
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
        addUnsavedProperty("phone");
    }

    public void setAddress(final Address address) {
        this.address = address;
        addUnsavedProperty("address");
    }

    public void setAddresses(final Collection<Address> addresses) {
        this.addresses = addresses;
        addUnsavedProperty("addresses");
    }

    public void setPhones(final Collection<Phone> phones) {
        this.phones = phones;
        addUnsavedProperty("phones");
    }

    public Customer save() throws PagarMeException {

        final Customer saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Customer find(int id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), id));

        final Customer other = JSONUtils.getAsObject((JsonObject) request.execute(), Customer.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Customer> findCollection(int totalPerPage, int page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Customer>>() {
        }.getType());
    }

    private void copy(Customer other) {
        super.copy(other);
        this.documentNumber = other.documentNumber;
        this.documentType   = other.documentType;
        this.name           = other.name;
        this.email          = other.email;
        this.bornAt         = other.bornAt;
        this.gender         = other.gender;
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }
}