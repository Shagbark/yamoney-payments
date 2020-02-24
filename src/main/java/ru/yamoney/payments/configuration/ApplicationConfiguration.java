package ru.yamoney.payments.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ChainedTransactionManager chainedTransactionManager(@Qualifier("transactionManager") PlatformTransactionManager transactionManager,
                                                               @Qualifier("shard02JpaTransactionManager") PlatformTransactionManager secondTransactionManager,
                                                               @Qualifier("shard03JpaTransactionManager") PlatformTransactionManager thirdTransactionManager) {
        return new ChainedTransactionManager(transactionManager, secondTransactionManager, thirdTransactionManager);
    }

}
