package edu.dhbw.configuration_bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(MyRunner.class);
    private final Author beanAuthor;


    public MyRunner(Author beanAuthor) {
        this.beanAuthor = beanAuthor;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("[MANUAL LOG] Application has started and MyRunner is running");

        /*
         * Ohne das @Bean in der @Configuration muss der Autor in jeder Klasse selbst definiert
         * werden. Er könnte also nicht so einfach zentral zur Verfügung gestellt und überall
         * verwendet werden (bzw. müsste dafür als Singleton-Pattern implementiert werden).
         */
        Author author = new Author("Irgendwer", "Mit einem Nachnamen");
        logger.info("[MANUAL LOG] Without using the bean the manual author defined is: "
                + author.getFirstName() + " " + author.getLastName());

        /*
         * Auch der Author aus dem Bean kann aber genutzt werden, ohne dass er hier definiert oder
         * initialisiert wird! Er steht als @Bean definiert in der @Configuration in allen Klassen
         * zur Verfügung, die von Spring per Dependency Injection verwaltet werden.
         */
        logger.info("[MANUAL LOG] But also the author defined in the bean is available as: "
                + beanAuthor.getFirstName() + " " + beanAuthor.getLastName());

    }

}
