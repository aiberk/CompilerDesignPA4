class James{
    public int main(){
        int i;
        int a;
        int b;
        int n;
        int temp;
        a=0;
        b=1;
        i=1;
        n = 20;
        while (i<n) {
            temp = a+b;
            a = b;
            b = temp;
            System.out.println(b);
            i=i+1;
        }
    }
}