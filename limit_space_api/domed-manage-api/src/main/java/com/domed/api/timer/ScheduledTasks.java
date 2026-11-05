package com.domed.api.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduledTasks {

//    @Scheduled(cron="0 58 23 * * ?")
//    @PostConstruct
    public void getKq() {
        try {
//            scheduledTasksService.getKq();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
