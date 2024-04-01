class James{
    public int fib(int n){
        int i;
        int a;
        int b;
        int temp;
        a=0;
        b=1;
        i=1;
        while (i<n) {
            temp = a+b;
            a = b;
            b = temp;
            i=i+1;
        }
        return b;
    }
    public int main(){
        int j;
        j = fib(100);
        System.out.println(j);
        return j;
    }

}