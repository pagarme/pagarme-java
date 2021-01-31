package me.pagarme;

import java.util.List;
import junit.framework.Assert;
import me.pagar.model.Calculation;
import me.pagar.model.Installment;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jonatasmaxi
 */
public class CalculateInstallmentsTest extends BaseTest {

    public Calculation calculation;

    @Before
    public void setUp() {
        super.setUp();
        calculation = new Calculation();
    }

    @Test
    public void testCalculateInstallments() throws Throwable {
        List<Installment> installments = calculation.calculateInstallments(10000, 3, 1.5, 12);
        Assert.assertEquals(10750, installments.get(4).getAmount());
        Assert.assertEquals(5, installments.get(4).getInstallment());
        Assert.assertEquals(2150, installments.get(4).getInstallmentAmount());
    }

    @Test
    public void testCalculateInstallmentsAmountWithFreeInstallmentsWithoutInterest() throws Throwable {
        List<Installment> installments = calculation.calculateInstallments(90000, 3, 0, 3);
        Assert.assertEquals(90000, installments.get(2).getAmount());
        Assert.assertEquals(3, installments.get(2).getInstallment());
        Assert.assertEquals(30000, installments.get(2).getInstallmentAmount());

    }

}
