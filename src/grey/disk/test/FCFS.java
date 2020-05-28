package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

import java.util.ArrayList;

public class FCFS extends AbstractSolution {

    public FCFS(int firstLoc, int trackNum, ArrayList<Integer> list) {
        this.firstLocation = firstLoc;
        this.trackNum = trackNum;
        this.requestList = list;
    }

    @Override
    public ArrayList<Integer> run() {
        if (!isAllInitialized()) {
            System.out.println("初始位置或最大磁道数未指定！");
            return null;
        }

        System.out.println("=============FCFS Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        resultList = new ArrayList<>();
        resultList.add(firstLocation);
        for (Integer i : requestList) {
            resultList.add(i);
            distance += Math.abs(i - requestList.get(requestList.size() - 1));
        }
        System.out.println("FCFS average distance = " + distance / requestList.size());
        System.out.println("resultList = " + resultList);
        return resultList;
    }

    @Test
    public void FCFSTest() {
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);

        setRequestList(maker.getRandomRequestList());
        setTrackNum(1500);
        setFirstLocation(0);
        run();

        System.out.println("=============Test finished==============");
    }

}
