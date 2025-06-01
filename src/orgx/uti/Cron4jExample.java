package orgx.uti;

import it.sauronsoftware.cron4j.Scheduler;

public class Cron4jExample {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        //per 1min ext
        scheduler.schedule("*/1 * * * *", () -> System.out.println("执行定时任务11: " + System.currentTimeMillis()));



        //每小时的第一分执行  it.sauronsoftware.cron4j.Scheduler;
        scheduler.schedule("1 * * * *", () -> System.out.println("执行定时任务: " + System.currentTimeMillis()));
        //calc this mth stmmt..mtd
        //calc todt sttmt


        scheduler.schedule("54 20 * * *", () -> System.out.println("执行每天 20:54 的任务: " + System.currentTimeMillis()));




        //每月一号执行
        //calc lst mth sttmt




        scheduler.schedule("1 * * * *", () -> System.out.println("执行定时任务: " + System.currentTimeMillis()));


        // 每天凌晨 2 点执行
        scheduler.schedule("0 2 * * *", () -> System.out.println("执行定时任务: " + System.currentTimeMillis()));
        //calc todate sttmt



        // 每天凌晨 1点执行
        scheduler.schedule("0 1 * * *", () -> System.out.println("执行定时任务: " + System.currentTimeMillis()));


        scheduler.start();
    }
}

