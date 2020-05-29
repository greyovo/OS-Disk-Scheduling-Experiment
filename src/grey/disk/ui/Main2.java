package grey.disk.ui;

import grey.disk.test.ConsoleTest;

public class Main2 {

    public static void main(String[] args) {
        ConsoleTest test = new ConsoleTest(444,444,1500);
        test.generateRequest();

        test.drawFCFS();
        test.drawSSTF();
        test.drawSCAN();
        test.drawCSCAN();
    }

}
