package Store;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleNewsletter {

    public static void schedule(String toWhom,boolean weekly,boolean yearly){
        String cronstring ;
        if(weekly){
            cronstring = "";
        }
        if(yearly){
            cronstring= "";
        }
        if(weekly&&yearly){
            cronstring="";
        }
        ExecuteTask.setToWhom(toWhom);
        JobDetail job1 = JobBuilder.newJob(ExecuteTask.class).withIdentity("job1","group1").build();
        Trigger trigger = null;
        try {
            //10sec-("0/10 * * * * ?") monthly-("0 0 12 1/31 * ? *")
            trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger","group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1,trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}


