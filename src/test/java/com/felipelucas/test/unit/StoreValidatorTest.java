package com.felipelucas.test.unit;

import com.felipelucas.commons.exceptions.BusinessException;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.service.validator.StoreValidator;
import com.felipelucas.test.mock.StoreMockFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StoreValidatorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = BusinessException.class)
    public void nullStore() {
        StoreValidator validator = new StoreValidator();
        validator.validateStore(null);
    }

    @Test(expected = BusinessException.class)
    public void emptyStore() {
        StoreValidator validator = new StoreValidator();
        validator.validateStore(new StoreDTO());
    }

    @Test
    public void validStore() {
        StoreValidator validator = new StoreValidator();
        validator.validateStore(StoreMockFactory.mockFirst());
    }

    @Test
    public void validStoreWithNullss() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.id = null;
        mockFirst.mediumConsumption = null;
        validator.validateStore(mockFirst);
    }

    @Test(expected = BusinessException.class)
    public void invalidStoreRevenue() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.revenue = null;
        validator.validateStore(mockFirst);
    }


    @Test(expected = BusinessException.class)
    public void invalidCity() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.city = "";
        validator.validateStore(mockFirst);
    }

    @Test(expected = BusinessException.class)
    public void invalidState() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.state = "";
        validator.validateStore(mockFirst);
    }

    @Test(expected = BusinessException.class)
    public void invalidLatitude() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.latitude = "";
        validator.validateStore(mockFirst);
    }

    @Test(expected = BusinessException.class)
    public void invalidLongitude() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.longitude = "";
        validator.validateStore(mockFirst);
    }

    @Test(expected = BusinessException.class)
    public void invalidName() {
        StoreValidator validator = new StoreValidator();
        StoreDTO mockFirst = StoreMockFactory.mockFirst();
        mockFirst.name = "";
        validator.validateStore(mockFirst);
    }


}
