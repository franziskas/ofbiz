package org.apache.ofbiz.order.shoppingcart;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;

import java.util.Locale;

import static org.mockito.Mockito.mock;

class ShoppingCartBuilder {
    private Delegator delegator = mock(Delegator.class);
    private GenericValue productStore = mock(GenericValue.class);
    private Locale defaultLocale = Locale.CANADA;
    private Locale locale = Locale.US;
    private String productStoreId = "my_store";
    private String currency = "EUR";
    private String defaultCurrency = "USD";
    private String billFromVendorPartyId = null;
    private boolean readOnly = false;
    private ShoppingCart.Logger logger = mock(ShoppingCart.Logger.class);
    private ShoppingCart.ProductStoreRepository productStoreRepository;
    private ShoppingCart.MinimumOrderPriceListRepository minimumOrderPriceRepository;

    public static ShoppingCartBuilder cart() {
        return new ShoppingCartBuilder();
    }

    ShoppingCartBuilder withProductStore(GenericValue store) {
        this.productStore = store;
        return this;
    }

    ShoppingCartBuilder withDefaultLocale(Locale locale) {
        this.defaultLocale = locale;
        return this;
    }

    ShoppingCartBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    ShoppingCartBuilder withProductStoreId(String id) {
        this.productStoreId = id;
        return this;
    }

    ShoppingCartBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    ShoppingCartBuilder withDefaultCurrency(String currency) {
        this.defaultCurrency = currency;
        return this;
    }

    ShoppingCartBuilder withBillFromVendorPartyId(String partyId) {
        this.billFromVendorPartyId = partyId;
        return this;
    }

    public ShoppingCartBuilder readOnly() {
        this.readOnly = true;
        return this;
    }

    public ShoppingCartBuilder withLogger(ShoppingCart.Logger logger) {
        this.logger = logger;
        return this;
    }

    public ShoppingCartBuilder withProductStoreRepository(ShoppingCart.ProductStoreRepository repository) {
        this.productStoreRepository = repository;
        return this;
    }

    ShoppingCart build() {
        ShoppingCart cart = new ShoppingCart(this.delegator, this.productStoreId, "websiteid", this.locale, this.currency, null, this.billFromVendorPartyId) {
            @Override
            protected Locale getDefaultLocale() {
                return defaultLocale;
            }

            @Override
            protected GenericValue loadProductStore(Delegator delegator, String productStoreId) {
                return productStore;
            }

            @Override
            protected String getDefaultCurrency(Delegator delegator) {
                return defaultCurrency;
            }

            @Override
            protected Logger getLogger() {
                return logger;
            }

            @Override
            protected ProductStoreRepository getProductStoreRepository() {
                return productStoreRepository;
            }

            @Override
            protected MinimumOrderPriceListRepository getMinimumOrderPriceListRepository() {
                return minimumOrderPriceRepository;
            }
        };
        cart.setReadOnlyCart(this.readOnly);
        return cart;
    }

    public ShoppingCartBuilder withMinimumOrderPriceListRepository(ShoppingCart.MinimumOrderPriceListRepository testMinimumOrderPriceListRepository) {
        this.minimumOrderPriceRepository = testMinimumOrderPriceListRepository;
        return this;
    }
}