package me.pagarme;

import java.util.Collection;
import me.pagarme.helper.TestEndpoints;
import me.pagar.model.BalanceOperation;
import me.pagar.model.Transfer;
import me.pagar.model.Payable;
import me.pagar.model.SplitRule;
import me.pagar.model.filter.BalanceOperationQueriableFields;
import me.pagarme.factory.CustomerFactory;
import me.pagarme.factory.SplitRulesFactory;
import me.pagarme.factory.TransactionFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.joda.time.DateTime;

public class BalanceOperationsTest extends BaseTest {

    private final CustomerFactory customerFactory = new CustomerFactory();
    private final TestEndpoints testEndpoints = new TestEndpoints();
    private final SplitRulesFactory splitRulesFactory = new SplitRulesFactory();
    private final TransactionFactory transactionFactory = new TransactionFactory();
    private static final int AMOUNT = 100000;

    @Before
    public void SetUp() {
        super.setUp();
    }

    @Test
    public void testAvailableBalances() throws Throwable {
        transaction = transactionFactory.createBoletoTransaction();
        transaction.setAmount(AMOUNT);
        transaction.save();
        testEndpoints.payBoleto(transaction);

        BalanceOperationQueriableFields balanceFilter = new BalanceOperationQueriableFields();
        balanceFilter.statusEquals(BalanceOperation.Status.AVAILABLE);
        Collection<BalanceOperation> balances = new BalanceOperation().findCollection(1, 1, balanceFilter);
        BalanceOperation foundBalance = balances.iterator().next();

        Assert.assertEquals(Payable.class, foundBalance.getMovementObjectAsPagarmeObject().getClass());
        Assert.assertEquals(BalanceOperation.Status.AVAILABLE, foundBalance.getStatus());
    }

    @Test
    public void testWaitingFundsBalances() throws Throwable {
        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(AMOUNT);
        transaction.save();

        BalanceOperationQueriableFields balanceFilter = new BalanceOperationQueriableFields();
        balanceFilter.statusEquals(BalanceOperation.Status.WAITING_FUNDS);
        Collection<BalanceOperation> balances = new BalanceOperation().findCollection(1, 1, balanceFilter);
        BalanceOperation foundBalance = balances.iterator().next();

        Assert.assertEquals(Payable.class, foundBalance.getMovementObjectAsPagarmeObject().getClass());
        Assert.assertEquals(BalanceOperation.Status.WAITING_FUNDS, foundBalance.getStatus());
    }

    @Test
    public void testTransferredBalances() throws Throwable {
        transaction = transactionFactory.createBoletoTransaction();
        transaction.setAmount(AMOUNT);
        Collection<SplitRule> splitRules = splitRulesFactory.createSplitRuleWithPercentage();
        transaction.setSplitRules(splitRules);
        transaction.save();
        testEndpoints.payBoleto(transaction);

        Transfer transfer = new Transfer(10000, splitRules.iterator().next().getRecipientId());
        transfer.save();

        BalanceOperationQueriableFields balanceFilter = new BalanceOperationQueriableFields();
        balanceFilter.statusEquals(BalanceOperation.Status.TRANSFERRED);
        Collection<BalanceOperation> balances = new BalanceOperation().findCollection(1, 1, balanceFilter);
        BalanceOperation foundBalance = balances.iterator().next();

        Assert.assertEquals(Transfer.class, foundBalance.getMovementObjectAsPagarmeObject().getClass());
        Assert.assertEquals(BalanceOperation.Status.TRANSFERRED, foundBalance.getStatus());
    }

    @Test
    public void testFindBalance() throws Throwable {
        transaction = transactionFactory.createBoletoTransaction();
        transaction.setAmount(AMOUNT);
        customer = customerFactory.create();
        transaction.setCustomer(customer);
        transaction.save();
        testEndpoints.payBoleto(transaction);

        BalanceOperationQueriableFields balanceFilter = new BalanceOperationQueriableFields();
        balanceFilter.createdBefore(new DateTime());
        Collection<BalanceOperation> balances = new BalanceOperation().findCollection(1, 1, balanceFilter);
        BalanceOperation foundBalance = balances.iterator().next();

        Assert.assertNotNull(new BalanceOperation().find(foundBalance.getId()));
    }
}
