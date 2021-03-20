package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXML {
    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");
    public static void main(String[]args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        CurrencyConvertorImpl currencyConvertor = applicationContext.getBean("currencyConvertorImpl", CurrencyConvertorImpl.class);
        System.out.println(currencyConvertor.convert(EUR, CZK, BigDecimal.valueOf(1)));

    }
}
