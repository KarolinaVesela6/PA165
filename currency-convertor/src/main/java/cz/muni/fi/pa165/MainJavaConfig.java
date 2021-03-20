package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {
    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");
    public static void main(String[]args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        CurrencyConvertor currencyConvertor = applicationContext.getBean("convertor", CurrencyConvertor.class);
        System.out.println(currencyConvertor.convert(EUR, CZK, BigDecimal.valueOf(1)));
    }
}
