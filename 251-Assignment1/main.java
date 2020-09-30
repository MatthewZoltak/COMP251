import java.io.*;
import java.util.*;


public class main {     

     
    public static void main(String[] args) {
        int A1 = 554;
        int A2 = 1023;
        int w = 10;
        int seed = 0;

        int list1[] = {86, 85, 6, 97, 19, 66, 26, 14, 15, 49, 75, 64, 35, 54, 31, 9, 82, 29, 81, 13};
        int list2[] = {70, 54, 19, 58, 46, 14, 67, 80, 3, 93, 47, 50, 74, 72, 85, 95, 86, 91, 81, 90};



        Chaining chainingMap1 = new Chaining(w, seed, A1);
        Chaining chainingMap2 = new Chaining(w,seed, A2);

        Open_Addressing open_addressing1 = new Open_Addressing(w,seed,A1);
        Open_Addressing open_addressing2 = new Open_Addressing(w,seed,A2);


        int chainingCollisions1 = chainingMap1.insertKeyArray(list1);
        int chainingCollisions2 = chainingMap2.insertKeyArray(list2);

        int openAddressingCollisions1 = open_addressing1.insertKeyArray(list1);
        int openAddressingCollisions2 = open_addressing2.insertKeyArray(list2);


        System.out.println("First chaining collisions: " + chainingCollisions1);
        System.out.println("Second chaining collisions: " + chainingCollisions2);

        System.out.println("First open addressing collisions: " + openAddressingCollisions1);
        System.out.println("Second open addressing collisions: " + openAddressingCollisions2);


    }
}