package grey.disk.test;

import grey.disk.util.RequestMaker;
import org.junit.Test;

public class SSTF extends AbstractService {


    public SSTF() {

    }

    @Override
    public void run() {
        System.out.println("=============SSTF Test==============");
        System.out.println("firstLocation = " + firstLocation);
        System.out.println("requestList = " + requestList);
        System.out.println("Started test...");

        int minDistance = Integer.MAX_VALUE;    //距离初始化为最大值
        int curLocation = firstLocation;
        int nextLocation = 0;
        int curIndex = 0;
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

            requestList.remove(curIndex);           //移除 已寻道的请求
            minDistance = Integer.MAX_VALUE;        //距离初始化为最大值
        }

        System.out.println("SSTF average distance = " + distance / resultList.size());
        System.out.println("resultList = " + resultList);

    }

    @Test
    public void SSTFTest() {
        RequestMaker maker = new RequestMaker();
        maker.setRequestNum(400);
        setRequestList(maker.getRandomRequestList());
        setFirstLocation(900);
        run();
        System.out.println("=============Test finished==============");
    }

}
