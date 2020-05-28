package grey.disk.test;


import grey.disk.util.RequestMaker;
import org.junit.Test;

public class CSCAN extends AbstractService {

    public static final int POSITIVE_DIRECTION = 0; //按增大的方向
    public static final int NEGATIVE_DIRECTION = 1; //按减小的方向

    private int direction = -1;

    public CSCAN() {

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void run() {

        System.out.println("=============C-SCAN Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        int curLocation = firstLocation;
        resultList.add(curLocation);

        if (direction == POSITIVE_DIRECTION) {
            //向磁道号增大的方向扫描
            System.out.println("direction = POSITIVE_DIRECTION");

            while (!requestList.isEmpty()) {
                if (curLocation > trackNum) {
                    curLocation = 0;
                }
                if (requestList.contains(curLocation)) {
                    distance += Math.abs(resultList.get(resultList.size() - 1) - curLocation);
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation++;
            }


        } else if (direction == NEGATIVE_DIRECTION) {
            //向磁道号减小方向扫描
            System.out.println("direction = NEGATIVE_DIRECTION");

            while (!requestList.isEmpty()) {
                if (curLocation < 0) {
                    curLocation = trackNum;
                }

                if (requestList.contains(curLocation)) {
                    distance += Math.abs(resultList.get(resultList.size() - 1) - curLocation);
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation--;
            }


        } else {
            System.out.println("未指定方向或方向错误");
        }

        System.out.println("SCAN average distance = " + distance / resultList.size());
        System.out.println("resultList = " + resultList);

    }

    @Test
    public void CSCANTest() {
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);
        setRequestList(maker.getRandomRequestList());
        setFirstLocation(555);
        setDirection(SCAN.POSITIVE_DIRECTION);
        run();
        System.out.println("=============Test finished==============");
    }


}
