import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT); // Лэтч для старта
    public static final CountDownLatch countDownLatchFinish = new CountDownLatch(CARS_COUNT); // Лэтч для финиша
    public static final Semaphore semaphore = new Semaphore(CARS_COUNT/2); //Семафор для тоннеля




    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread((cars[i])).start();
        }



        //Когда все участники завершают трассу - объявляем КОНЕЦ
        try {
            MainClass.countDownLatchFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    public static void cdlCountDown() {
        countDownLatch.countDown();
    }

    public static void cdlAwait() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void smpAcquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void smpRelease() {
        semaphore.release();
    }
}





