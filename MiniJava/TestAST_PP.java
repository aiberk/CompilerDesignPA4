class TestAST_PP
{
    public static void main(String[] args){
        Demo d;
        int j;
        j = 10 * 2 + 1;
        d = new Demo();
        d.run_test(j);
    
    }
}
class Demo
{
    public void run_test(int j){
        while (j >= 0){
            System.out.print(j);
            System.out.print(" is ");
            if (j % 2 == 0){
                System.out.println("even!");

            }
            else{
                System.out.println("odd!");

            }
            j = j - 1;

        }
    
    }
}