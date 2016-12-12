package me.pagarme;

import java.util.Collection;

import org.junit.Before;

import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagarme.factory.RecipientFactory;

public class AnticipationTest extends BaseTest {

    private RecipientFactory recipientFactory = new RecipientFactory();
    private Recipient defaultRecipient;

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        Collection<Recipient> recipients = new Recipient().findCollection(10, 0);
        defaultRecipient = recipients.iterator().next();
    }
}
