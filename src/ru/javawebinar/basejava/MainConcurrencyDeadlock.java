package ru.javawebinar.basejava;

class Letter {
    public String message;
    public Letter(String message) {
        this.message = message;
    }
}

class Adress {
    String adress;
    public Letter letterToSend;
    public Letter letterToReceive = null;
    public Adress adressToDeliver;

    public Adress(String adress) {
        this.adress = adress;
    }
}

class Postman implements Runnable {
    Adress adressFrom;
    Adress adressTo;

    public Postman(Adress adressFrom, Adress adressTo) {
        this.adressFrom = adressFrom;
        this.adressTo = adressTo;
    }

    @Override
    public void run() {
        Adress sender;
        Adress receiver;
        Letter letter;
        synchronized(adressFrom) {
            letter = adressFrom.letterToSend;
            System.out.println(Thread.currentThread().getName() + " : getting the letter from " + adressFrom.adress);
//            try {
//                Thread.sleep(1000);
//            } catch(InterruptedException e) {
//            }
            synchronized(adressTo)
            {
                adressTo.letterToReceive = letter;
                System.out.println(Thread.currentThread().getName() + " : delivering the letter to " + adressTo.adress);
            }
        }
    }
}

public class MainConcurrencyDeadlock {
    public static void main(String[] args) throws InterruptedException {
        Adress adress1 = new Adress("adress1");
        Adress adress2 = new Adress("adress2");
        adress1.adressToDeliver = adress2;
        adress1.letterToSend = new Letter("Hello from adress1 to adress2!");
        adress2.adressToDeliver = adress1;
        adress2.letterToSend = new Letter("Hello from adress2 to adress1!");

        Postman postman1 = new Postman(adress1, adress2);
        Postman postman2 = new Postman(adress2, adress1);

        Thread threadPostman1 = new Thread(postman1, "Postman1");
        Thread threadPostman2 = new Thread(postman2, "Postman2");

        threadPostman1.start();
        threadPostman2.start();

        threadPostman1.join();
        threadPostman2.join();
    }
}
