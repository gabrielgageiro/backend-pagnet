package com.example.pagnet.backend_pagnet.config;

import com.example.pagnet.backend_pagnet.transacao.Transacao;
import com.example.pagnet.backend_pagnet.transacao.TransacaoCNAB;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Configuration
public class BatchConfig {
    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    Step step(FlatFileItemReader<TransacaoCNAB> reader, ItemProcessor<TransacaoCNAB, Transacao> processor, ItemWriter<Transacao> writer) {
        return new StepBuilder("step", jobRepository)
                .<TransacaoCNAB, Transacao>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean
    FlatFileItemReader<TransacaoCNAB> reader() {
        FileSystemResource fileSystemResource = new FileSystemResource("/home/gabriel/www/backend-pagnet/backend/backend-pagnet/files/CNAB.txt");
        return new FlatFileItemReaderBuilder<TransacaoCNAB>()
                .name("reader")
                .resource(fileSystemResource)
                .fixedLength()
                .columns(new Range(1, 1), new Range(2, 9), new Range(10, 19),
                        new Range(20, 30), new Range(31, 42), new Range(43, 48),
                        new Range(49, 62), new Range(63, 81))
                .names("tipo", "data", "valor", "cpf", "cartao", "hora",
                        "donoDaLoja", "nomeDaLoja")
                .targetType(TransacaoCNAB.class)
                .build()
                ;
    }
    /*
    *  Documentação do CNAB
        Descrição do campo	Inicio	Fim	Tamanho	Comentário
        Tipo	            1	    1	1	    Tipo da transação
        Data	            2       9	8	    Data da ocorrência
        Valor	            10	    19	10	    Valor da movimentação. Obs. O valor encontrado no arquivo precisa ser divido por cem(valor / 100.00) para normalizá-lo.
        CPF	                20	    30	11	    CPF do beneficiário
        Cartão	            31	    42	12	    Cartão utilizado na transação
        Hora	            43	    48	6	    Hora da ocorrência atendendo ao fuso de UTC-3
        Dono da loja	    49	    62	14	    Nome do representante da loja
        Nome loja	        63	    81	19	    Nome da loja
    *
    * */

    @Bean
    ItemProcessor<TransacaoCNAB, Transacao> processor() {
        return item -> {
            var transacao = new Transacao(
                    item.tipo(), null, item.valor().divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN),
                    item.cpf(), item.cartao(), null,
                    item.donoDaLoja(), item.nomeDaLoja())
                    .withData(item.data())
                    .withHora(item.hora());
            return transacao;
        };
    }

    @Bean
    JdbcBatchItemWriter<Transacao> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transacao>()
                .dataSource(dataSource)
                .sql("""
                        INSERT INTO transacoes (tipo, data, valor, cpf, cartao, hora, dono_da_loja, nome_da_loja) " +
                        "VALUES (:tipo, :data, :valor, :cpf, :cartao, :hora, :donoDaLoja, :nomeDaLoja)
                        """)
                .beanMapped()
                .build();
    }

    @Bean
    JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
