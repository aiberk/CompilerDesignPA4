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
            System.out.println(b);
            i=i+1;
        }
        return b;
    }
    public int equal(int a, int b){
        return !(a<b) && !(b<a);
    }
    public int test(int a, int b, int c) {
        int d;
        if (equal(a,b)) {
            d=0;
        }
        else if ((a<b) && !(c<b)){
            d=111;
        } else if (!(a<b)){
            d=222;
        } else if (!(b<c)){
            d=333;
        } else {
            d=444;
        }
        System.out.println(d);
        return d;
    }
    public int main(){
        int c;
        c=20;
        c=fib(10);
        System.out.println(1000*c);
        c=test(100,100,200);
        c=test(100,150,200);
        c=test(100,10,200);
        c=test(100,300,200);
        return c;
    }
}