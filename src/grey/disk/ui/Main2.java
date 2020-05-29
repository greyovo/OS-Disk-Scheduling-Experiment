package grey.disk.ui;

import grey.disk.test.ConsoleTest;

public class Main2 {

    public static void main(String[] args) {
        ConsoleTest test = new ConsoleTest(444,444,1500);
        test.generateRequest();

        System.out.println("FCFS");
        test.drawFCFS();

        System.out.println("SSTF");
        test.drawSSTF();

        System.out.println("SCAN");
        test.drawSCAN();

        System.out.println("CSCAN");
        test.drawCSCAN();
    }

}
