class James{
    public int main(){
        int s;
        int t;
        int a;
        int[] b;
        a = 0;
        t = 3;
        b = new int[10];
        b[0]=100;
        b[t]=200;
        a = b[t];
        System.out.println(a);
        System.out.println(b[0] + b[t]);
        System.out.println(b.length);
        return a;
    }
}