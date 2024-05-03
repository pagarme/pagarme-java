package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;

import java.util.Collection;

public class Partner extends PagarMeModel<String> {

    @Expose
    private Customer.Type type;

    @Expose
    @SerializedName("document_number")
    private String documentNumber;

    @Expose
    private String name;

    @Expose
    @SerializedName("mother_name")
    private String motherName;

    @Expose
    private LocalDate birthdate;

    @Expose
    @SerializedName("monthly_income")
    private String monthlyIncome;

    @Expose
    @SerializedName("professional_occupation")
    private String professionalOccupation;

    @Expose
    private RegisterInformationAddress address;

    @Expose
    private String email;

    @Expose
    @SerializedName("self_declared_legal_representative")
    private Boolean selfDeclaredLegalRepresentative;

    @Expose
    @SerializedName("phone_numbers")
    private Collection<RegisterPhone> phoneNumbers;


    public RegisterInformationAddress getAddress() {
        return address;
    }

    public void setAddress(RegisterInformationAddress address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getProfessionalOccupation() {
        return professionalOccupation;
    }

    public void setProfessionalOccupation(String professionalOccupation) {
        this.professionalOccupation = professionalOccupation;
    }

    public Customer.Type getType() {
        return type;
    }

    public void setType(Customer.Type type) {
        this.type = type;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumbers(Collection<RegisterPhone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public Collection<RegisterPhone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Boolean getSelfDeclaredLegalRepresentative() {
        return selfDeclaredLegalRepresentative;
    }

    public void setSelfDeclaredLegalRepresentative(Boolean selfDeclaredLegalRepresentative) {
        this.selfDeclaredLegalRepresentative = selfDeclaredLegalRepresentative;
    }
}

