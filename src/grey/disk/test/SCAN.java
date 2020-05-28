package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

import java.util.Comparator;

/**
 * 又称电梯算法
 */
public class SCAN extends AbstractService {

    public static final int POSITIVE_DIRECTION = 0; //按增大的方向
    public static final int NEGATIVE_DIRECTION = 1; //按减小的方向
    private int direction = -1;

    public SCAN() {

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void run() {
        System.out.println("=============SCAN Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        int curLocation = firstLocation;

        if (direction == POSITIVE_DIRECTION) {
            //向磁道号增大的方向扫描
            System.out.println("direction = POSITIVE_DIRECTION");

            while (curLocation < trackNum) {
                if (requestList.contains(curLocation)) {
                    if (!resultList.isEmpty()) {
                        distance += Math.abs(resultList.get(resultList.size() - 1) - curLocation);
                    } else {
                        distance += Math.abs(curLocation - firstLocation);
                    }
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation++;
            }

            //之后反向
            while (curLocation > 0) {
                if (requestList.contains(curLocation)) {
                    if (!resultList.isEmpty()) {
                        distance += resultList.get(resultList.size() - 1) - curLocation;
                    } else {
                        distance += Math.abs(curLocation - firstLocation);
                    }
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation--;
            }

        } else if (direction == NEGATIVE_DIRECTION) {
            //向磁道号减小方向扫描
            System.out.println("direction = NEGATIVE_DIRECTION");

            while (curLocation > 0) {
                if (requestList.contains(curLocation)) {
                    if (!resultList.isEmpty()) {
                        distance += Math.abs(resultList.get(resultList.size() - 1) - curLocation);
                    } else {
                        distance += Math.abs(curLocation - firstLocation);
                    }
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation--;
            }

            //之后反向
            while (curLocation < trackNum) {
                if (requestList.contains(curLocation)) {
                    if (!resultList.isEmpty()) {
                        distance += resultList.get(resultList.size() - 1) - curLocation;
                    } else {
                        distance += Math.abs(curLocation - firstLocation);
                    }
                    resultList.add(curLocation);
                    requestList.remove(new Integer(curLocation));
                }
                curLocation++;
            }

        } else {
            System.out.println("未指定方向或方向错误");
        }

        System.out.println("SCAN average distance = " + distance / resultList.size());
        System.out.println("resultList = " + resultList);


//        requestList.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1.compareTo(o2);
//            }
//        });

//        //找出距离当前磁道号最近的请求及其在列表中的下标
//        for (int i = 0; i < requestList.size(); i++) {
//            if (Math.abs(requestList.get(i) - curLocation) < minDistance) {
//                minDistance = Math.abs(requestList.get(i) - curLocation);
//                nextLocation = requestList.get(i);
//                curIndex = i;
//            }
//        }
//        resultList.add(curLocation);
//        curLocation = nextLocation;

    }


    @Test
    public void SCANTest() {
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);
        setRequestList(maker.getRandomRequestList());
        setFirstLocation(555);
        setDirection(POSITIVE_DIRECTION);
        run();
    }


}
