package grey.disk.test;

import java.util.ArrayList;

public class FCFS extends AbstractSolution {

    public FCFS(int firstLoc, int trackNum, ArrayList<Integer> list) {
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

        System.out.println("=============FCFS Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        resultList.clear();
        resultList.add(firstLocation);

        for (Integer integer : requestList) {
            distance += Math.abs(integer - resultList.get(resultList.size() - 1));
            resultList.add(integer);
        }

        System.out.println("FCFS average distance = " + distance / (resultList.size() - 1));
        System.out.println("resultList = " + resultList);
        System.out.println("=============Test Finished==============\n");

        requestList.clear();

        return resultList;
    }

}
