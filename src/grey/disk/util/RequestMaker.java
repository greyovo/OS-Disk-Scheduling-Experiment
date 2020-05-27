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
        ArrayList<Integer> requestList = new ArrayList<>();

        int i = 0;
        Iterator<Integer> it;

        //50%位于0~499
        set.clear();
        randomsToSet(0, 499, (int) (requestNum * 0.5));
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println("已经生成" + i + "个序列");

        //25%位于500~999
        set.clear();
        randomsToSet(500, 999, (int) (requestNum * 0.25));
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println("已经生成" + i + "个序列");

        //25%位于1000~1499
        set.clear();
        randomsToSet(1000, 1499, (int) (requestNum * 0.25));
        it = set.iterator();
        while (it.hasNext()) {
            requestList.add(it.next());
            i++;
        }
        System.out.println("已经生成" + i + "个序列");

        //打乱顺序


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

//        if (min > max || (max - min) < num)
//            return;

        for (int i = 0; i < num; i++) {
            int temp = (int) (random.nextDouble() * (max - min+1))+min;
            set.add(temp);
        }

        int size = set.size();
        System.out.println("ss: "+set.size());
        if (size < requestNum)
            randomsToSet(min, max, requestNum - size);
    }


    @Test
    public void test() {
        requestNum = 400;
//        getRandomRequest();
        randomsToSet(0, 499, requestNum);
        System.out.println(set);
        System.out.println(set.size());
    }


}
