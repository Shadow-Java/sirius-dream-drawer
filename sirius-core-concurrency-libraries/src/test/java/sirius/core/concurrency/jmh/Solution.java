package sirius.core.concurrency.jmh;

/**
 * @author shadow
 * @create 2023-06-04 23:18
 **/
public class Solution {

    public static void main(String[] args) {
        final String s1 = "123";
        String s2 = "123";
        /**
         * s3是个final变量，那么只能指向123，不能指向其他的
         */
        final String s3 = new String("123");
        //s3 = new String("234");s3是个final变量，那么只能指向123，不能指向其他的
        String s4 = new String("123");
    }

}
