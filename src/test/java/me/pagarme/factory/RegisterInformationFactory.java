package me.pagarme.factory;

import me.pagar.model.Address;
import me.pagar.model.Customer;
import me.pagar.model.Phone;
import me.pagar.model.RegisterInformation;
import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.Collection;

public class RegisterInformationFactory {

    public static final Customer.Type DEFAULT_TYPE = Customer.Type.CORPORATION;
    public static final String DEFAULT_DOCUMENT_NUMBER = "18344334799";
    public static final String DEFAULT_NAME = "Full Name Company";
    public static final String DEFAULT_TRADING_NAME = "Full Name Company SA";
    public static final LocalDate DEFAULT_FOUNDING_DATE = new LocalDate();
    public static final String DEFAULT_EMAIL = "company@negocios-de-valor.com";
    public static final String DEFAULT_SITE_URL = "http://www.pagarme.com";

    public RegisterInformation create(){
        RegisterInformation registerInformation = new RegisterInformation();
        registerInformation.setDocumentNumber(DEFAULT_DOCUMENT_NUMBER);
        registerInformation.setCompanyName(DEFAULT_NAME);
        registerInformation.setTradingName(DEFAULT_TRADING_NAME);
        registerInformation.setEmail(DEFAULT_EMAIL);
        registerInformation.setSiteUrl(DEFAULT_SITE_URL);
        registerInformation.setType(DEFAULT_TYPE);
        registerInformation.setFoundingDate(DEFAULT_FOUNDING_DATE);
        return registerInformation;
    }
}
