package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

import java.util.ArrayList;

public class SSTF extends AbstractSolution {

    public SSTF(int firstLoc, int trackNum, ArrayList<Integer> list) {
        this.firstLocation = firstLoc;
        this.trackNum = trackNum;
        this.requestList = (ArrayList<Integer>) list.clone();
    }

    @Override
    public ArrayList<Integer> getResultList() {
        if (!isAllInitialized()) {
            System.out.println("初始位置或最大磁道数未指定！");
            return null;
        }

        System.out.println("=============SSTF Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        int minDistance = Integer.MAX_VALUE;    //距离初始化为最大值
        int curLocation = firstLocation;
        int nextLocation = 0;
        int curIndex = 0;

        resultList.clear();
        resultList.add(firstLocation);

        while (!requestList.isEmpty()) {
            //找出距离当前磁道号最近的请求及其在列表中的下标
            for (int i = 0; i < requestList.size(); i++) {
                if (Math.abs(requestList.get(i) - curLocation) < minDistance) {
                    minDistance = Math.abs(requestList.get(i) - curLocation);
                    nextLocation = requestList.get(i);
                    curIndex = i;
                }
            }
            distance += Math.abs(curLocation - nextLocation);
            curLocation = nextLocation;
            resultList.add(curLocation);
            requestList.remove(curIndex);           //移除已寻道的请求
            minDistance = Integer.MAX_VALUE;        //距离初始化为最大值
        }

        System.out.println("SSTF average distance = " + distance / resultList.size());
        System.out.println("resultList = " + resultList);
        System.out.println("=============Test Finished==============\n");

        return resultList;
    }

}
