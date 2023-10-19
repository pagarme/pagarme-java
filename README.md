[![Build Status](https://travis-ci.org/pagarme/pagarme-java.svg?token=dqgmPH2JHKsHRgaNHZxf&branch=master)](https://travis-ci.org/pagarme/pagarme-java)

# Introdução

Essa SDK foi construída com o intuito de torná-la flexível, de forma que todos possam utilizar todas as features, de todas as versões de API.

Você pode acessar a documentação oficial do Pagar.me acessando esse [link](https://docs.pagar.me).

## Índice

- [Requerimentos](#requerimentos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Transações](#transações)
  - [Criando uma transação](#criando-uma-transação)
  - [Capturando uma transação](#capturando-uma-transação)
  - [Estornando uma transação](#estornando-uma-transação)
    - [Estornando uma transação parcialmente](#estornando-uma-transação-parcialmente)
  - [Retornando transações](#retornando-transações)
  - [Retornando uma transação](#retornando-uma-transação)
  - [Retornando recebíveis de uma transação](#retornando-recebíveis-de-uma-transação)
  - [Retornando um recebível de uma transação](#retornando-um-recebível-de-uma-transação)
- [Cartões](#cartões)
  - [Criando cartões](#criando-cartões)
  - [Retornando um cartão](#retornando-um-cartão)
- [Planos](#planos)
  - [Criando planos](#criando-planos)
  - [Retornando planos](#retornando-planos)
  - [Retornando um plano](#retornando-um-plano)
  - [Atualizando um plano](#atualizando-um-plano)
- [Assinaturas](#assinaturas)
  - [Criando assinaturas](#criando-assinaturas)
  - [Split com assinatura](#split-com-assinatura)
  - [Retornando uma assinatura](#retornando-uma-assinatura)
  - [Retornando assinaturas](#retornando-assinaturas)
  - [Cancelando uma assinatura](#cancelando-uma-assinatura)
  - [Transações de assinatura](#transações-de-assinatura)
  - [Pulando cobranças](#pulando-cobranças)
- [Postbacks](#postbacks)
  - [Retornando postbacks](#retornando-postbacks)
  - [Retornando um postback](#retornando-um-postback)
  - [Reenviando um Postback](#reenviando-um-postback)
- [Saldo do recebedor principal](#saldo-do-recebedor-principal)
- [Recebível](#recebível)
  - [Retornando recebíveis](#retornando-recebíveis)
  - [Retornando um recebível](#retornando-um-recebível)
- [Transferências](#transferências)
  - [Criando uma transferência](#criando-uma-transferência)
  - [Retornando transferências](#retornando-transferências)
  - [Retornando uma transferência](#retornando-uma-transferência)
  - [Cancelando uma transferência](#cancelando-uma-transferência)
- [Antecipações](#antecipações)
  - [Criando uma antecipação](#criando-uma-antecipação)
  - [Obtendo os limites de antecipação](#obtendo-os-limites-de-antecipação)
  - [Confirmando uma antecipação building](#confirmando-uma-antecipação-building)
  - [Cancelando uma antecipação pending](#cancelando-uma-antecipação-pending)
  - [Deletando uma antecipação building](#deletando-uma-antecipação-building)
  - [Retornando antecipações](#retornando-antecipações)
- [Contas bancárias](#contas-bancárias)
  - [Criando uma conta bancária](#criando-uma-conta-bancária)
  - [Retornando uma conta bancária](#retornando-uma-conta-bancária)
  - [Retornando contas bancárias](#retornando-contas-bancárias)
- [Recebedores](#recebedores)
  - [Criando um recebedor](#criando-um-recebedor)
  - [Retornando recebedores](#retornando-recebedores)
  - [Retornando um recebedor](#retornando-um-recebedor)
  - [Atualizando um recebedor](#atualizando-um-recebedor)
  - [Saldo de um recebedor](#saldo-de-um-recebedor)
- [Clientes](#clientes)
  - [Criando um cliente](#criando-um-cliente)
  - [Retornando clientes](#retornando-clientes)
  - [Retornando um cliente](#retornando-um-cliente)
- [Análise de Fraude](#análise-de-fraude)
  - [Retornando análises antifraude](#retornando-análises-antifraude)
- [Consulta de CEP](#consulta-de-cep)
- [Regras de Split](#regras-de-split)
  - [Retornando as regras de divisão de uma transação](#retornando-as-regras-de-divisão-de-uma-transação)
  - [Retornando uma regra de divisão específica](#Retornando-uma-regra-de-divisão-específica)



## Requerimentos

- Java >= 6 && <= 8

## Instalação

Para instalar a nossa biblioteca no seu projeto, você deve usar uma dependência Maven. Segue:

```xml
<dependency>
    <groupId>me.pagar</groupId>
    <artifactId>pagarme-java</artifactId>
    <version>2.1.0</version>
</dependency>
```

## Configuração

Para todos os casos, você irá precisar das seguintes importações:

```java
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
```

Para incluir sua chave de API, você fará da seguinte maneira:

```java
PagarMe.init("sua_api_key");
```

E então, você pode poderá utilizar o cliente para fazer requisições ao Pagar.me, como nos exemplos abaixo.

## Transações

Nesta seção será explicado como utilizar transações no Pagar.me com essa biblioteca. 

Para essa seção, você precisará dessas importações:

```java
import java.util.ArrayList;
import java.util.Collection;
import me.pagar.model.Address;
import me.pagar.model.Billing;
import me.pagar.model.Customer;
import me.pagar.model.Document;
import me.pagar.model.Item;
import me.pagar.model.Shipping;
import me.pagar.model.Transaction;
import me.pagar.model.Payable;
```

### Criando uma transação

```java
Transaction transaction = new Transaction();
Customer customer = new Customer();
customer.setType(Customer.Type.INDIVIDUAL);
customer.setExternalId("1001");
customer.setName("Phineas Flynn");
customer.setBirthday("1999-07-09");
customer.setEmail("phineas@threestatearea.com");
customer.setCountry("br");

Collection<Document> documents = new ArrayList();
Document document = new Document();
document.setType(Document.Type.CPF);
document.setNumber("77551442758");
documents.add(document);
customer.setDocuments(documents);

Collection<String> phones = new ArrayList();
phones.add("+5511982657575");
customer.setPhoneNumbers(phones);

Billing billing = new Billing(); 
billing.setName("Phineas Flynn");
Address address  = new Address(); 
address.setCity("Santo Andre");
address.setCountry("br");
address.setState("sp");
address.setNeighborhood("Parque Miami");
address.setStreet("Rua Rio Jari");
address.setZipcode("09133180");
address.setStreetNumber("7");
billing.setAddress(address);

Shipping shipping = new Shipping();
shipping.setAddress(address);
shipping.setName("Phineas Flynn");
shipping.setFee(3200);

Collection<Item> items = new ArrayList();
Item item = new Item(); 
item.setId("OX890");
item.setQuantity(12);
item.setTangible(Boolean.TRUE);
item.setTitle("Rockets");
item.setUnitPrice(120);

transaction.setShipping(shipping);
transaction.setBilling(billing);
transaction.setItems(items);
transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
transaction.setAmount(4640);
transaction.setCardHolderName("Phineas Flynn");
transaction.setCardNumber("4242424242424242");
transaction.setCardCvv("909");
transaction.setCardExpirationDate("1119"); 
transaction.setCustomer(customer);
transaction.save();
```

### Capturando uma transação

```java
Transaction trx = new Transaction().find(ID_OU_TOKEN_TRANSAÇÃO);

trx.capture(VALOR_PARA_CAPTURAR);
```

### Estornando uma transação

```java
Transaction trx = new Transaction().find(ID_DA_TRANSAÇÃO);

trx.capture(VALOR_PARA_ESTORNAR);
```

Esta funcionalidade também funciona com estornos parciais, ou estornos com split. Por exemplo:

#### Estornando uma transação parcialmente

```java
Transaction trx = new Transaction().find(ID_DA_TRANSAÇÃO);

trx.capture(VALOR_PARA_ESTORNAR);
```

### Retornando transações

```java
Integer count = 20;
Integer page = 1;

Collection<Transaction> trx = new Transaction().findCollection(count, page);
```

### Retornando uma transação

```java
Transaction tx = new Transaction().find(ID_DA_TRANSAÇÃO);
```

### Retornando recebíveis de uma transação

```java
Collection<Payable> payables = new Transaction().find(ID_DA_TRANSAÇÃO).payables();
```

### Retornando um recebível de uma transação

```java
Payable p = new Transaction().find(ID_DA_TRANSAÇÃO).payables(ID_DO_RECEBIVEL);
```

## Cartões

Sempre que você faz uma requisição através da nossa API, nós guardamos as informações do portador do cartão, para que, futuramente, você possa utilizá-las em novas cobranças, ou até mesmo implementar features como one-click-buy.

Nesta seção será explicado como utilizar transações no Pagar.me com essa biblioteca. 

Para essa seção, você precisará dessa importação:

```java
import me.pagar.model.Card;
```

### Criando cartões

```java
Card card = new Card();
card.setNumber("4018720572598048");
card.setHolderName("Aardvark Silva");
card.setExpiresAt("1122");
card.setCvv(123);
card.save();
```

### Retornando um cartão

```java
Card cards = new Card().find("ID_DO_CARTÃO");
```

## Planos

Representa uma configuração de recorrência a qual um cliente consegue assinar.
É a entidade que define o preço, nome e periodicidade da recorrência

Para essa seção, você precisará dessa importação:

```java
import me.pagar.model.Plan;
```

### Criando planos

```java
Plan plan = new Plan();
Integer amount = 15000;
Integer days = 30;
String name = "The Pro Plan - Platinum  - Best Ever";
plan.setCreationParameters(amount, days, name);
plan.save();
```

### Retornando planos

```java
Integer count = 20;
Integer page = 1;

Collection<Plan> plans = new Plan().findCollection(count, page);
```

### Retornando um plano

```java
Plan plan = new Plan().find("ID_DO_PLANO");
```

### Atualizando um plano

```java
Plan planF = new Plan();
planF.setName("The Pro Plan - John");
planF.setTrialDays(7500);
planF.setId("ID_DO_PLANO_A_SER_ATUALIZADO");
planF.save();
```

## Assinaturas

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import me.pagar.model.Customer;
import me.pagar.model.Address;
import me.pagar.model.Card;
import me.pagar.model.Phone;
import me.pagar.model.Subscription;
import me.pagar.model.SplitRule;
```

### Criando assinaturas

```java
Phone phone = new Phone();
phone.setDdd("11");
phone.setDdi("55");
phone.setNumber("99999999");

String street = "Avenida Brigadeiro Faria Lima";
String streetNumber = "1811";
String neighborhood = "Jardim Paulistano";
String zipcode = "01451001";
Address address = new Address(street, streetNumber, neighborhood, zipcode);

String name = "Aardvark Silva";
String email = "aardvark.silva@pagar.me";
String documentNumber = "18152564000105";
Customer customer = new Customer(name, email);
customer.setAddress(address);
customer.setPhone(phone);
customer.setDocumentNumber(documentNumber);

Card card = new Card();
card.setNumber("4901720080344448");
card.setHolderName("Jose da Silva");
card.setExpiresAt("1219");
card.setCvv("123");
card.save();

Subscription subscription = new Subscription();
subscription.setCreditCardSubscriptionWithCardId("ID_PLANO", card.getId(), customer);
subscription.save();
```

### Split com assinatura

```java
Phone phone = new Phone();
phone.setDdd("11");
phone.setDdi("55");
phone.setNumber("99999999");

String street = "Avenida Brigadeiro Faria Lima";
String streetNumber = "1811";
String neighborhood = "Jardim Paulistano";
String zipcode = "01451001";
Address address = new Address(street, streetNumber, neighborhood, zipcode);

String name = "Aardvark Silva";
String email = "aardvark.silva@pagar.me";
String documentNumber = "18152564000105";
Customer customer = new Customer(name, email);
customer.setAddress(address);
customer.setPhone(phone);
customer.setDocumentNumber(documentNumber);

Card card = new Card();
card.setNumber("4901720080344448");
card.setHolderName("Jose da Silva");
card.setExpiresAt("1222");
card.setCvv("123");
card.save();

Collection<SplitRule> splitRules = new ArrayList();
SplitRule split1 = new SplitRule();
split1.setRecipientId("ID_RECEBEDOR");
split1.setPercentage(50);
split1.setChargeProcessingFee(true);
split1.setLiable(true);

SplitRule split2 = new SplitRule();
split2.setRecipientId("ID_RECEBEDOR");
split2.setPercentage(50);
split2.setChargeProcessingFee(true);
split2.setLiable(true);

splitRules.add(split1);
splitRules.add(split2);

Subscription subscription = new Subscription();
subscription.setSplitRules(splitRules);
subscription.setCreditCardSubscriptionWithCardId("ID_PLANO", card.getId(), customer);
subscription.save();
```

### Retornando uma assinatura

```java
Subscription subscription = new Subscription().find("ID_ASSINATURA");
```

### Retornando assinaturas

```java
Integer count = 10;
Integer page = 1;

Collection<Subscription> subs = new Subscription().findCollection(count, page);
```

### Cancelando uma assinatura

```java
Subscription subscription = new Subscription().find("ID_ASSINATURA");

subscription.cancel();
```

### Transações de assinatura

```java
Collection<Transaction> txs = new Subscription().find("ID_ASSINATURA").transactions();
```

### Pulando cobranças

```java
 Subscription subs = new Subscription().find("ID_ASSINATURA");

 subs.settleCharges(NUMERO_DE_COBRANÇAS_A_SER_PULADA);
```

## Postbacks

Ao criar uma transação ou uma assinatura você tem a opção de passar o parâmetro postback_url na requisição. Essa é uma URL do seu sistema que irá então receber notificações a cada alteração de status dessas transações/assinaturas.

Para obter informações sobre postbacks, 3 informações serão necessárias, sendo elas: `model`, `model_id` e `postback_id`.

`model`: Se refere ao objeto que gerou aquele POSTback. Pode ser preenchido com o valor `transaction` ou `subscription`.

`model_id`: Se refere ao ID do objeto que gerou ao POSTback, ou seja, é o ID da transação ou assinatura que você quer acessar os POSTbacks.

`postback_id`: Se refere à notificação específica. Para cada mudança de status de uma assinatura ou transação, é gerado um POSTback. Cada POSTback pode ter várias tentativas de entregas, que podem ser identificadas pelo campo `deliveries`, e o ID dessas tentativas possui o prefixo `pd_`. O campo que deve ser enviado neste parâmetro é o ID do POSTback, que deve ser identificado pelo prefixo `po_`. 

Para essa seção, você precisará dessa importação:

```java
import java.util.Collection;
import me.pagar.model.Postback;
import me.pagar.model.Transaction;
```

### Retornando postbacks

```java
Collection<Postback> postbacks = new Transaction().find(ID_DA_TRANSAÇÃO).postbacks();
```

### Retornando um postback

```java
Postback postback = new Transaction().find(ID_TRANSAÇÃO).postbacks("ID_POSTBACK");
```

### Reenviando um postback

```java
Postback postback = new Transaction().find(ID_TRANSAÇÃO).postbackRedeliver("ID_POSTBACK");
```

## Saldo do recebedor principal
Para essa seção, você precisará dessas importações:

```java
import me.pagar.model.Balance;
import me.pagar.model.Recipient;
```

Para saber o saldo de sua conta, você pode utilizar esse código:

```java
Balance b = new Recipient().find("ID_RECEBEDOR_PRINCIPAL").balance();

int available = b.getAvailable().getAmount();

int waitingFunds = b.getWaitingFunds().getAmount();

int transferred = b.getTransferred().getAmount();
```

## Recebível

Objeto contendo os dados de um recebível. O recebível (payable) é gerado automaticamente após uma transação ser paga. Para cada parcela de uma transação é gerado um recebível, que também pode ser dividido por recebedor (no caso de um split ter sido feito).

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import org.joda.time.DateTime;
import me.pagar.model.Payable;
import me.pagar.model.filter.PayableQueriableFields;
```

### Retornando recebíveis

```java
Integer count = 10;
Integer page = 1;

PayableQueriableFields payableFilter = new PayableQueriableFields();

payableFilter.createdBefore(new DateTime());

Collection<Payable> payables = new Payable().findCollection(count, page, payableFilter);
```

### Retornando um recebível

```java
Payable payable = new Payable().find(ID_PAYABLE);
```

## Transferências
Transferências representam os saques de sua conta.

Para essa seção, você precisará dessas importações:

```java
import me.pagar.model.Transfer;
import java.util.Collection;
```

### Criando uma transferência

```java
Transfer transfer = new Transfer();
transfer.setAmount(10000);
transfer.setRecipientId("ID_RECEBEDOR");
transfer.save();
```

### Retornando transferências

```java
Integer count = 10;
Integer page = 1;

Collection<Transfer >transfers = new Transfer().findCollection(count, page);
```

### Retornando uma transferência

```java
Transfer transfer = new Transfer().find("ID_TRANSFERÊNCIA");
```

### Cancelando uma transferência

```java
Transfer transfer = new Transfer().find("ID_TRANSFERÊNCIA");
transfer.cancel();
```

## Antecipações

Para entender o que são as antecipações, você deve acessar esse [link](https://docs.pagar.me/docs/overview-antecipacao).

Para essa seção, você precisará dessas importações:

```java
import org.joda.time.DateTime;
import java.util.Collection;
import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.model.Recipient;
```

### Criando uma antecipação

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");
DateTime paymentDate = new DateTime().plusDays(7);
int requestedAmount = 13000;
boolean build = true;
BulkAnticipation anticipation = new BulkAnticipation();
anticipation.setRequiredParametersForCreation(
  paymentDate, 
  Timeframe.END, 
  requestedAmount, 
  build
);
anticipation = recipient.anticipate(anticipation);
```

### Obtendo os limites de antecipação

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");
DateTime paymentDate = new DateTime().plusDays(7);
recipient.getMaxAnticipationLimit(paymentDate, Timeframe.END);
recipient.getMinAnticipationLimit(paymentDate, Timeframe.END);
```

### Confirmando uma antecipação building

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");

BulkAnticipation anticipiation = (BulkAnticipation) recipient.findAnticipations(10, 1).toArray()[0];
BulkAnticipation cancelledAnticipation = recipient.confirmBulkAnticipation(createdAnticipation);
```

### Cancelando uma antecipação pending

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");

BulkAnticipation anticipiation = (BulkAnticipation) recipient.findAnticipations(10, 1).toArray()[0];

BulkAnticipation cancelledAnticipation = recipient.cancelAnticipation(anticipiation);
```

### Deletando uma antecipação building

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");

BulkAnticipation anticipation = new BulkAnticipation();
anticipation.setRequiredParametersForCreation(
  new DateTime().plusDays(7),
  BulkAnticipation.Timeframe.END,
  1000,
  true
);

BulkAnticipation createdAnticipation = recipient.anticipate(anticipation);

recipient.deleteAnticipation(createdAnticipation);
```

### Retornando antecipações

```java
String recipientId = "re_ciu4jif1j007td56dsm17yew9";
Recipient recipient = new Recipient().find(recipientId);

Integer count = 10;
Integer page = 1;

Collection<BulkAnticipation> bulkAnticipations = recipient.findAnticipations(count, page);
```

## Contas bancárias

Contas bancárias identificam para onde será enviado o dinheiro de futuros pagamentos.

Para essa seção, você precisará dessa importação:

```java
import me.pagar.model.BankAccount;
```

### Criando uma conta bancária

```java
BankAccount account = new BankAccount();
account.setAgencia("0192");
account.setAgenciaDv("0");
account.setConta("03245");
account.setContaDv("0");
account.setBankCode("341");
account.setDocumentNumber("26268738888");
account.setLegalName("API BANK ACCOUNT");
account.save();
```

### Retornando uma conta bancária

```java
BankAccount accounts = new BankAccount().find(ID_BANK_ACCOUNT);
```

### Retornando contas bancárias

```java
Integer count = 10;
Integer page = 1;

Collection<BankAccount> accounts = new BankAccount().findCollection(count, page);
```

## Recebedores

Para dividir uma transação entre várias entidades, é necessário ter um recebedor para cada uma dessas entidades. Recebedores contém informações da conta bancária para onde o dinheiro será enviado, e possuem outras informações para saber quanto pode ser antecipado por ele, ou quando o dinheiro de sua conta será sacado automaticamente.

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import me.pagar.model.Balance;
import me.pagar.model.Recipient.TransferInterval;
import me.pagar.model.Recipient;
```

### Criando um recebedor

```java
Recipient recipient = new Recipient();
recipient.setAnticipatableVolumePercentage(85);
recipient.setAutomaticAnticipationEnabled(true);
recipient.setBankAccountId(ID_BANK_ACCOUNT);
recipient.setTransferDay(5);
recipient.setTransferEnabled(true);
recipient.setTransferInterval(TransferInterval.WEEKLY);
recipient.save();
```

### Retornando recebedores

```java
Integer count = 10;
Integer page = 1;

Collection<Recipient> recipients = new Recipient().findCollection(count, page);
```

### Retornando um recebedor

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");
```

### Atualizando um recebedor

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");

recipient.setAnticipatableVolumePercentage(94);
recipient.setAutomaticAnticipationEnabled(true);
recipient.setTransferDay(3);
recipient.setTransferEnabled(true);
recipient.setTransferInterval(Recipient.TransferInterval.WEEKLY);
recipient.save();
```

### Saldo de um recebedor

```java
Recipient recipient = new Recipient().find("ID_RECEBEDOR");
Balance balance = recipient.balance();
```


## Clientes

Clientes representam os usuários de sua loja, ou negócio. Este objeto contém informações sobre eles, como nome, e-mail e telefone, além de outros campos.

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import me.pagar.model.Customer;
```

### Criando um cliente

```java
Customer customer = new Customer();
customer.setType(Customer.Type.INDIVIDUAL);
customer.setExternalId("1001");
customer.setName("Phineas Flynn");
customer.setBirthday("1999-07-09");
```

### Retornando clientes

```java
Integer count = 10;
Integer page = 1;

Collection<Customer> customers = new Customer().findCollection(count, page);
```

### Retornando um cliente

```java
Customer cliente = new Customer().find(ID_CUSTOMER);
```

## Análise de Fraude

Caso a sua conta esteja habilitada com análise manual pelo antifraude, é possível buscar pela análises via API seguindo esses exemplos.

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import me.pagar.model.AntifraudAnalysis;
import me.pagar.model.Transaction;
```

### Retornando análises antifraude

```java
Collection<AntifraudAnalysis> analysis = new Transaction().find(913456).antifraudAnalysises();
```

## Consulta de CEP

Com essa rota você pode verificar os dados de um determinado CEP.

```java
import me.pagar.model.Zipcode;

Zipcode zipcode = new Zipcode();
zipcode.checkZipcode("01452001");
```

## Regras de Split

Retorna os dados referentes a uma regra de divisão específica de uma determinada transação.

Para essa seção, você precisará dessas importações:

```java
import java.util.Collection;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
```

### Retornando as regras de divisão de uma transação

```java
Collection<SplitRule> splitRules = new Transaction().find(ID_TRANSAÇÃO).splitRules();
```

### Retornando uma regra de divisão específica

```java
Transaction trx = new Transaction().find("ID_TRANSAÇÃO");
System.out.println(trx.splitRules("ID_SPLIT_RULES"));
```

## Agradecimento
development based on library pagarme-java [Adriano Luis](https://github.com/adrianoluis)
