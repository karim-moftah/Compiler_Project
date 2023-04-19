import java.util.*;
import java.io.*;
import java.util.List;
public class Output {

	static public String blocksVisited[] = {};

		static public Set<String> blocks = new HashSet<String>();
	static public ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(blocksVisited));

    public static void main (String[] args){		// block number 1
			arrayList.add("1");
        int x = 5, y = 6;
        for(int i = 0; i < 5;i++){		// block number 2
			arrayList.add("2");
            System.out.println("For Loop");
            while(x < y){		// block number 3
			arrayList.add("3");
                x++;
                System.out.println("while loop");
                do{		// block number 4
			arrayList.add("4");
                    System.out.println("do while loop");
                }while(x == 0);
            }
        }
        x = 5;
        y = 6;
        if(x < y) {										// block number 5
			arrayList.add("5");
			System.out.println("if statement");
			}
        else {										// block number 6
			arrayList.add("6");
			System.out.println("else statement");
			}
        if(x == 5){		// block number 7
			arrayList.add("7");
            if(x > 5){		// block number 8
			arrayList.add("8");
                System.out.println("x>5");
            }
            else{		// block number 9
			arrayList.add("9");
                System.out.println("x<5");
            }
        }
        x = 6;
        y = 5;
        if(x > y){		// block number 10
			arrayList.add("10");
            if(y > 4){		// block number 11
			arrayList.add("11");
                if(x == 6) {		// block number 12
			arrayList.add("12");
                    if (x == 4) {										// block number 13
			arrayList.add("13");
			System.out.println("x == 4");
			}
                }
            }
            else {		// block number 14
			arrayList.add("14");
                System.out.println("y > x");
            }
        }
        foo();
    
		blocksVisited = arrayList.toArray(blocksVisited);
		String visitedBlocks = "";
		for(String blockNums: blocksVisited)
		{
blocks.add(blockNums);
			

				visitedBlocks += "Block #"+blockNums+" is visited\n";
		}
	visitedBlocks = blocks + "\n" + visitedBlocks;
	write("outputs/blocksVisited.txt",visitedBlocks);
			String Ss = "";
		for(String elements : blocks) {
			Ss = Ss + elements + "\n";
		}
		write("outputs/blocks.txt",Ss);

}
	public static void write(String filename,String string)  {
		try{
			String outputFileName = filename;
			FileOutputStream outputFile = new FileOutputStream(outputFileName);
			BufferedOutputStream buffer = new BufferedOutputStream(outputFile);
			byte[] bytes = string.getBytes();
			buffer.write(bytes);
			buffer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

    static void foo(){		// block number 15
			arrayList.add("15");
        int x = 1;
        switch (x){
            case 0:
				arrayList.add("case0");
                System.out.println("case 0");
                break;
            case 1:
				arrayList.add("case1");
                System.out.println("case 1");
                break;
            default:
				arrayList.add("case2");
                System.out.println("default");
                break;
        }
        System.out.println("method");
    }
}