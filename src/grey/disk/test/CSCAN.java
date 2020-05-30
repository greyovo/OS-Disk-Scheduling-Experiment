package grey.disk.test;

import java.util.ArrayList;

public class CSCAN extends AbstractSolution {

    public static final int POSITIVE_DIRECTION = 0; //按增大的方向
    public static final int NEGATIVE_DIRECTION = 1; //按减小的方向

    private int direction = -1;

    public CSCAN() {

    }

    public CSCAN(int firstLoc, int trackNum, ArrayList<Integer> list, int direction) {
        this.firstLocation = firstLoc;
        this.trackNum = trackNum;
        this.direction = direction;
        this.requestList = (ArrayList<Integer>) list.clone();
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public ArrayList<Integer> getResultList() {
        if (!isAllInitialized()) {
            System.out.println("初始位置或最大磁道数未指定！");
            return null;
        }
        if (direction == -1) {
            System.err.println("方向未指定");
            return null;
        }
        System.out.println("=============C-SCAN Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        resultList.clear();
        resultList.add(firstLocation);
        int curLocation = firstLocation;

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
            System.out.println("未指定方向或方向错误！");
        }

        System.out.println("SCAN average distance = " + distance / (resultList.size() - 1));
        System.out.println("resultList = " + resultList);
        System.out.println("=============Test Finished==============\n");

        return resultList;

    }

}
