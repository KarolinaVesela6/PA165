package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackageClasses = {TimeAspect.class})
@EnableAspectJAutoProxy
public class JavaConfig {
    @Bean
    public ExchangeRateTable exchangeRateTable(){
        return new ExchangeRateTableImpl();
    }
    @Bean(name = "convertor")
    public CurrencyConvertor currencyConvertor(){
        return new CurrencyConvertorImpl(exchangeRateTable());
    }
}
