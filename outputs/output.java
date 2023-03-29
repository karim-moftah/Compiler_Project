//class Task01 {
//    public static void main(String[] args) {
//        int x = 0;
//        int expression = 9;
//        if( x == true ) {
//
//            if ( x == true)
//                System.out.println("this is not a block");
//
//            if (z  == false){
//                System.out.println("this is a block");
//            }
//        }
//    }
//}

import java.util.*;
class Task02 {

	static public Integer blocksVisited[] = {};
	static public ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(blocksVisited));

    public static void main(String[] args) {		// block number 1
			arrayList.add(1);
        int x = 5;
        if(x == 5 || x == 6)
            {										// block number 2
			arrayList.add(2);
			System.out.println("hello");
			}
        else if(true)
            {										// block number 3
			arrayList.add(3);
			System.out.println("happy");
			}
        else
            {										// block number 4
			arrayList.add(4);
			System.out.println("new");
			}
        for(int i = 0 ; i < 3  ;i++)
        {		// block number 5
			arrayList.add(5);
            System.out.println("world");
        }
    
		blocksVisited = arrayList.toArray(blocksVisited);
		for(int blockNums: blocksVisited)
		{
			if(blockNums != 0)
				System.out.println("Block #"+blockNums+" is visited");
		}
	}
}