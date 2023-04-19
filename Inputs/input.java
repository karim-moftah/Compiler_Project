import java.util.List;
public class Output {
    public static void main (String[] args){
        int x = 5, y = 6;
        for(int i = 0; i < 5;i++){
            System.out.println("For Loop");
            while(x < y){
                x++;
                System.out.println("while loop");
                do{
                    System.out.println("do while loop");
                }while(x == 0);
            }
        }
        x = 5;
        y = 6;
        if(x < y) System.out.println("if statement");
        else System.out.println("else statement");
        if(x == 5){
            if(x > 5){
                System.out.println("x>5");
            }
            else{
                System.out.println("x<5");
            }
        }
        x = 6;
        y = 5;
        if(x > y){
            if(y > 4){
                if(x == 6) {
                    if (x == 4) System.out.println("x == 4");
                }
            }
            else {
                System.out.println("y > x");
            }
        }
        foo();
    }
    static void foo(){
        int x = 1;
        switch (x){
            case 0:
                System.out.println("case 0");
                break;
            case 1:
                System.out.println("case 1");
                break;
            default:
                System.out.println("default");
                break;
        }
        System.out.println("method");
    }
}