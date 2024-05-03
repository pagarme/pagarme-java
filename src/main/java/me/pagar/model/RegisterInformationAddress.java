package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;


public class RegisterInformationAddress extends PagarMeModel<Integer> {

    @Expose
    private String street;

    @Expose
    private String complementary;

    @Expose
    @SerializedName("street_number")
    private String streetNumber;

    @Expose
    private String neighborhood;

    @Expose
    private String city;

    @Expose
    private String state;

    @Expose
    private String zipcode;

    @Expose
    private String country;

    @Expose
    @SerializedName("reference_point")
    private String referencePoint;

    public RegisterInformationAddress() {
        super();
    }

    public RegisterInformationAddress(final String street, final String streetNumber, final String neighborhood, final String zipcode, final String referencePoint) {
        this();
        this.street = street;
        this.streetNumber = streetNumber;
        this.neighborhood = neighborhood;
        this.zipcode = zipcode;
        this.referencePoint = referencePoint;
    }


    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(String referencePoint) {
        this.referencePoint = referencePoint;
        addUnsavedProperty("referencePoint");
    }

    public String getStreet() {
        return street;
    }

    public String getComplementary() {
        return complementary;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public DateTime getCreatedAt() {
        throw new UnsupportedOperationException("Not allowed.");
    }

    public void setStreet(final String street) {
        this.street = street;
        addUnsavedProperty("street");
    }

    public void setComplementary(final String complementary) {
        this.complementary = complementary;
        addUnsavedProperty("complementary");
    }

    public void setStreetNumber(final String streetNumber) {
        this.streetNumber = streetNumber;
        addUnsavedProperty("streetNumber");
    }

    public void setNeighborhood(final String neighborhood) {
        this.neighborhood = neighborhood;
        addUnsavedProperty("neighborhood");
    }

    public void setCity(final String city) {
        this.city = city;
        addUnsavedProperty("city");
    }

    public void setState(final String state) {
        this.state = state;
        addUnsavedProperty("state");
    }

    public void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
        addUnsavedProperty("zipcode");
    }

    public void setCountry(final String country) {
        this.country = country;
        addUnsavedProperty("country");
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }

}
