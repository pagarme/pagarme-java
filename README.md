Pagar.me Java Library
===========================
[![Build Status](https://travis-ci.org/pagarme/pagarme-java.svg?token=dqgmPH2JHKsHRgaNHZxf&branch=master)](https://travis-ci.org/pagarme/pagarme-java)

## Requirements

- Java >= 6 && <= 8

## Configure your API key

```java
PagarMe.init("api_key");
```

## Transactions

### Creating and capture a credit card transaction without fraud
```java
transaction = new Transaction();
transaction.setAmount(100);
transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
transaction.setCardHolderName("Lucas Dos Santos Alves");
transaction.setCardExpirationDate("0916");
transaction.setCardCvv("401");
transaction.setCardNumber("4111111111111111");
transaction.setInstallments(1);
transaction.setCapture(true);
transaction.save();
```

### Creating and capture a credit card transaction with customer
```java

#### Creating customer object
Customer customer = new Customer();
customer.setName("lucas santos");
customer.setDocumentNumber("15317529506");
customer.setEmail("testelibjava@pagar.me");

#### Creating customer address object
Address address = new Address();
address.setStreet("Rua Piraju");
address.setStreetNumber("218");
address.setComplementary("ao lado da consigáz");
address.setNeighborhood("Interlagos");
address.setZipcode("29045482");

#### Creating customer phone object
Phone phone = new Phone();
phone.setDdd("11");
phone.setNumber("55284132");

#### Adding address & phone on the customer object
customer.setAddress(address);
customer.setPhone(phone);

#### Adding customer object to transaction object and create transaction
transaction.setCustomer(customer);
transaction.save();
```

### Creating a Boleto Transaction without fraud
```java
transaction = new Transaction();
transaction.setAmount(100);
transaction.setPaymentMethod(Transaction.PaymentMethod.BOLETO);
transaction.save();
```

### Find a transaction
```java
transaction = new Transaction();
transaction.find("transaction_id");
```

### Find a transaction collection
```java
transaction = new Transaction();
transaction.findCollection(10,0);
```

### Reverse a transaction
```java
transaction = new Transaction();
transaction.find("transaction_id");
transaction.refund(50);
```

## Customer

### Creating customer
```java
#### Creating customer object
Customer customer = new Customer();
customer.setName("lucas santos");
customer.setDocumentNumber("15317529506");
customer.setEmail("testelibjava@pagar.me");

#### Creating customer address object
Address address = new Address();
address.setStreet("Rua Piraju");
address.setStreetNumber("218");
address.setComplementary("ao lado da consigáz");
address.setNeighborhood("Interlagos");
address.setZipcode("29045482");

#### Creating customer phone object
Phone phone = new Phone();
phone.setDdd("11");
phone.setNumber("55284132");

#### Adding address & phone on the customer object
customer.setAddress(address);
customer.setPhone(phone);

#### Save customer
customer.save();
```

### Find Customer
```java
Customer customer = new Customer();
customer.find("customer_id");
```

### Find Customer Collection
```java
Customer customer = new Customer();
customer.findCollection(10,0);
```

## Subscription & Plans

### Create plan
```java
int amount = 100;
int days = 30;
int charges = 100;
int installments = 2;
int trialDays = 3;
String name = "Plano teste";
String color = "#bababa";
Plan plan = new Plan();
plan.setCreationParameters(amount, days, name);
plan.setPaymentMethods(Arrays.asList(
    PaymentMethod.BOLETO, PaymentMethod.CREDIT_CARD
));
plan.setCharges(charges);
plan.setColor(color);
plan.setInstallments(installments);
plan.setTrialDays(trialDays);
plan.save();
```

### Find plan
```java
Integer planId = 999;
Plan plan = new Plan();
plan.find(planId);
```

### List plan
```java
Plan plan = new Plan();
plan.findCollection(10,0);
```

### Create subscription
```java
Subscription subscription = new Subscription();
subscription.setCreditCardSubscriptionWithCardId(planId, cardId, customer);
subscription.save();

Subscription subscription2 = new Subscription();
subscription2.setCreditCardSubscriptionWithCardHash(planId, cardHash, customer);
subscription2.save();

Subscription subscription3 = new Subscription();
subscription3.setBoletoSubscription(planId, customer);
subscription3.save();
```

### Find subscription
```java
Integer subscriptionId = 999;
Subscription foundSubscription = new Subscription().find(subscriptionId);
```

### List subscription collection
```java
Collection<Subscription> subscriptions = new Subscription().findCollection(10, 1);
```

### Cancel subscription
```java
Integer subscriptionId = 999;
Subscription subscription = new Subscription();
subscription.find(subscriptionId);
subscription.cancel();
```

### List all subscription transactions
```java
Integer subscriptionId = 999;
Subscription subscription = new Subscription();
subscription.find(subscriptionId);
Collection<Transaction> transactions = subscription.transactions();
```

### Agradecimento
development based on library pagarme-java [Adriano Luis](https://github.com/adrianoluis)
