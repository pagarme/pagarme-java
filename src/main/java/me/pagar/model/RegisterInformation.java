package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;

import java.util.Collection;

public class RegisterInformation extends PagarMeModel<String> {

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
    @SerializedName("main_address")
    private RegisterInformationAddress corporationAddress;

    @Expose
    @SerializedName("company_name")
    private String companyName;

    @Expose
    @SerializedName("trading_name")
    private String tradingName;

    @Expose
    @SerializedName("annual_revenue")
    private String annualRevenue;

    @Expose
    @SerializedName("corporation_type")
    private String corporationType;

    @Expose
    @SerializedName("founding_date")
    private LocalDate foundingDate;

    @Expose
    private String email;

    @Expose
    @SerializedName("site_url")
    private String siteUrl;

    @Expose
    @SerializedName("phone_numbers")
    private Collection<RegisterPhone> phoneNumbers;

    @Expose
    @SerializedName("managing_partners")
    private Collection<Partner> managingPartners;

    public RegisterInformationAddress getCorporationAddress() {
        return corporationAddress;
    }

    public void setCorporationAddress(RegisterInformationAddress corporationAddress) {
        this.corporationAddress = corporationAddress;
    }

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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public void setAnnualRevenue(String annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public void setCorporationType(String corporationType) {
        this.corporationType = corporationType;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void setPhoneNumbers(Collection<RegisterPhone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getAnnualRevenue() {
        return annualRevenue;
    }

    public String getCorporationType() {
        return corporationType;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public String getEmail() {
        return email;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Collection<RegisterPhone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Collection<Partner> getManagingPartners() {
        return managingPartners;
    }

    public void setManagingPartners(Collection<Partner> managingPartners) {
        this.managingPartners = managingPartners;
    }
}

