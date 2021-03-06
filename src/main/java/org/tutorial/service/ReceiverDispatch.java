package org.tutorial.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.tutorial.mapper.ReceiverMapper;
import org.tutorial.model.Message;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Reader;

@Component
@MapperScan("org.tutorial.mapper")
@RabbitListener(queues = "hello")
public class ReceiverDispatch {
    @Autowired
    ReceiverMapper receiverMapper;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
    }

    private static Reader reader;
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session;

    @PostConstruct
    public void init() throws Exception {
        reader = Resources.getResourceAsReader("config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();

        session.getConfiguration().addMapper(ReceiverMapper.class);
        receiverMapper = session.getMapper(ReceiverMapper.class);
    }

    private final int instance;

    public ReceiverDispatch(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance +
                " [x] Received '" + in + "'");
        doWork(in);
        receiverMapper.addMessage(new Message(in));
        session.commit();
        watch.stop();
        System.out.println("instance " + this.instance +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
