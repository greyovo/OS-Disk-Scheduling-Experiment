package grey.disk.test;

import java.util.ArrayList;

public abstract class AbstractService {

    protected ArrayList<Integer> requestList;   //磁道号请求序列
    protected ArrayList<Integer> resultList = new ArrayList<>();    //处理结果序列
    protected int firstLocation = 0;            //初始磁道号
    protected double distance = 100;            //跨越的磁道数
    protected int trackNum = 1500;              //最大磁道号（总磁道数）

    public void run() {
    }

    public void setRequestList(ArrayList<Integer> requestList) {
        this.requestList = requestList;
    }

    public void setFirstLocation(int firstLocation) {
        this.firstLocation = firstLocation;
    }

    public ArrayList<Integer> getResultList() {
        return resultList;
    }

    public double getDistance() {
        return distance;
    }

    public int totalValue(ArrayList<Integer> list) {
        int count = 0;
        for (Integer i : list) {
            count += i;
        }
        return count;
    }
}
