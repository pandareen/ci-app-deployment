package com.devops.ci_app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    CiJobRepository ciJobRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Started seeding database");
        loadCiJobData();
    }

    private void loadCiJobData() {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            ciJobRepository.save(new CiJob("30DE579E","L",format.parse("2024-07-20"),format.parse("2024-07-28"),"ops"));
            ciJobRepository.save(new CiJob("2B70FD56","D",format.parse("2024-07-21"),format.parse("2024-07-30"),"reporting"));
            ciJobRepository.save(new CiJob("74261488","D",format.parse("2024-07-22"),format.parse("2024-08-01"),"application"));
            ciJobRepository.save(new CiJob("E4FD6201","L",format.parse("2024-07-23"),format.parse("2024-08-03"),"ops"));
            ciJobRepository.save(new CiJob("61472E7F","D",format.parse("2024-07-24"),format.parse("2024-08-05"),"reporting"));
            ciJobRepository.save(new CiJob("3598D3C6","D",format.parse("2024-07-25"),format.parse("2024-08-07"),"application"));
            ciJobRepository.save(new CiJob("5A02A577","L",format.parse("2024-07-26"),format.parse("2024-08-09"),"ops"));
            ciJobRepository.save(new CiJob("F3EBC01F","D",format.parse("2024-07-27"),format.parse("2024-08-11"),"reporting"));
            ciJobRepository.save(new CiJob("42894AF5","D",format.parse("2024-07-28"),format.parse("2024-08-13"),"application"));
            ciJobRepository.save(new CiJob("32BBAF6E","L",format.parse("2024-07-29"),format.parse("2024-08-15"),"ops"));
        } catch (Exception e) {
            logger.info("Exeption occured while seeding database "+ e.getMessage());
        }
    }
}
