import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public static long maxCount = MainClass.countDownLatchFinish.getCount();



    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cdlCountDown();

            //Когда все машины готовы - объявляем СТАРТ
            if (MainClass.countDownLatch.getCount() == 0) {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ждем когда все машины будут готовы
        MainClass.cdlAwait();

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        //когда все фрагменты трассы пройдены - уменьшаем count у лэтча, ответственного за финиш
                MainClass.countDownLatchFinish.countDown();

        //если закончившая трассу машина была 1-ой - печатаем WIN
        if (MainClass.countDownLatchFinish.getCount() == (maxCount - 1)) {
            System.out.println(this.name + " WIN");
        }


    }
}