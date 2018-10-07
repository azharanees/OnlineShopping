package Store;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExecuteTask implements Job {

    public static void setToWhom(String toWhom) {
        ExecuteTask.toWhom = toWhom;
    }

    private static String toWhom;

    public void execute(JobExecutionContext context){
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Email.sendNewsLetter("",toWhom);
        System.out.println("Sending newsletter " +date);
    }
}
