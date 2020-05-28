package grey.disk.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * 用随机数生成函数产生“磁道号”序列（即磁盘请求的位置），共产生 400 个。
 * 其中50%位于 0～499，25%分布在 500～999，25%分布在 1000～1499。
 */
public class RequestMaker {

    private HashSet<Integer> set = new HashSet<>();
    private int requestNum = 0;
    private Random random = new Random();

    public RequestMaker() {

    }

//    public RequestMaker(int requestNum) {
//        this.requestNum = requestNum;
//    }

    public ArrayList<Integer> getRandomRequest() {
        if (requestNum == 0) {
            System.out.println("未设置请求序列的个数。");
            return null;
        }

        ArrayList<Integer> requestList = new ArrayList<>();
        int i = 0;
        Iterator<Integer> it;

        //50%位于0~499
        set.clear();
        while (set.size() < requestNum * 0.5)
            randomsToSet(0, 499, (int) (requestNum * 0.5) - set.size());
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println(set);
        System.out.println("已经生成" + i + "个序列");

        //25%位于500~999
        set.clear();
        while (set.size() < requestNum * 0.25)
            randomsToSet(500, 999, (int) (requestNum * 0.25) - set.size());
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println(set);
        System.out.println("已经生成" + i + "个序列");

        //25%位于1000~1499
        set.clear();
        while (set.size() < requestNum * 0.25)
            randomsToSet(1000, 1499, (int) (requestNum * 0.25) - set.size());
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println(set);
        System.out.println("已经生成" + i + "个序列");

        //打乱顺序
        int j, k, temp;
        for (int l = 0; l < requestList.size() * 2; l++) {
            j = (int) (random.nextDouble() * requestList.size());
            k = (int) (random.nextDouble() * requestList.size());
            temp = requestList.get(j);
            requestList.set(j, requestList.get(k));
            requestList.set(k, temp);
        }

        //最终乱序的结果
        System.out.println("\n乱序的随机请求序列：");
        System.out.println(requestList);

        return requestList;
    }


    /**
     * 生成不重复的处于begin和end之间，个数为num的随机数，存放到set中
     *
     * @param min 最小
     * @param max 最大
     * @param num 位于min和max之间的个数
     */
    public void randomsToSet(int min, int max, int num) {
        for (int i = 0; i < num; i++) {
            int temp = (int) (random.nextDouble() * (max - min + 1)) + min;
            set.add(temp);
        }
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    @Test
    public void test() {
        setRequestNum(400);
        getRandomRequest();
    }


}
