package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

import java.util.ArrayList;

public class FCFS extends BaseMethod {

    public FCFS() {
    }

    @Override
    public void run() {
        resultList = new ArrayList<>();
        resultList.add(firstLocation);
        for (Integer i : requestList) {
            resultList.add(i);
            distance += Math.abs(i - requestList.get(requestList.size() - 1));
        }
        System.out.println("average distance = " + distance / requestList.size());
    }

    @Test
    public void FCFSTest(){
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);
        setRequestList(maker.getRandomRequestList());
        run();
    }

}
