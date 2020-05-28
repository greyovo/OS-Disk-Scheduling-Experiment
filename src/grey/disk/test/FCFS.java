package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

import java.util.ArrayList;

public class FCFS extends AbstractService {

    public FCFS() {
    }

    @Override
    public void run() {
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
    }

    @Test
    public void FCFSTest(){
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);
        setRequestList(maker.getRandomRequestList());
        run();
        System.out.println("=============Test finished==============");
    }

}
