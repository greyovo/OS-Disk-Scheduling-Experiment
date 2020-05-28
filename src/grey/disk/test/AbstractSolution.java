package grey.disk.test;

import java.util.ArrayList;

public abstract class AbstractSolution {

    protected ArrayList<Integer> requestList;   //磁道号请求序列
    protected ArrayList<Integer> resultList = new ArrayList<>();    //处理结果序列
    protected int firstLocation = -1;            //初始磁道号
    protected double distance = 0;            //跨越的磁道数
    protected int trackNum = 1500;              //最大磁道号（总磁道数）

    public AbstractSolution() {

    }

    /**
     * 检查是否完成数据初始化
     */
    protected boolean isAllInitialized() {
        return trackNum != -1 && firstLocation != -1;
    }

    public abstract ArrayList<Integer> run();

    public void setRequestList(ArrayList<Integer> requestList) {
        this.requestList = requestList;
    }

    public void setFirstLocation(int firstLocation) {
        this.firstLocation = firstLocation;
    }

    public void setTrackNum(int trackNum) {
        this.trackNum = trackNum;
    }

    public ArrayList<Integer> getResultList() {
        return resultList;
    }

    public double getDistance() {
        return distance;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public int getFirstLocation() {
        return firstLocation;
    }

    public ArrayList<Integer> getRequestList() {
        return requestList;
    }

    public int totalValue(ArrayList<Integer> list) {
        int count = 0;
        for (Integer i : list) {
            count += i;
        }
        return count;
    }
}
