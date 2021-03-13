package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CurrencyConvertorImplTest {
    private final Currency CZK = Currency.getInstance("CZK");
    private final Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable table;

    private CurrencyConvertor convertor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        convertor = new CurrencyConvertorImpl(table);
    }
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(table.getExchangeRate(EUR,CZK)).thenReturn(new BigDecimal("0.1"));
        assertEquals(new BigDecimal("0.23"), convertor.convert(EUR, CZK, new BigDecimal("2.303")));
        assertEquals(new BigDecimal("1.00"), convertor.convert(EUR, CZK, new BigDecimal("10.050")));
        assertEquals(new BigDecimal("1.01"), convertor.convert(EUR, CZK, new BigDecimal("10.051")));
        assertEquals(new BigDecimal("1.01"), convertor.convert(EUR, CZK, new BigDecimal("10.149")));
        assertEquals(new BigDecimal("1.02"), convertor.convert(EUR, CZK, new BigDecimal("10.150")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(null, CZK, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(EUR, null, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(EUR, CZK, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(table.getExchangeRate(EUR, CZK))
                .thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        convertor.convert(EUR, CZK, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(table.getExchangeRate(EUR, CZK))
                .thenThrow(ExternalServiceFailureException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        convertor.convert(EUR, CZK, BigDecimal.ONE);
    }

}
