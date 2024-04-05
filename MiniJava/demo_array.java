class James{
    public int arrayDemo(int s, int t){
        int a;
        int[] b;
        a = 0;
        b = new int[t];
        b[0]=100;
        b[t]=200;
        a = b[t];
        System.out.println(a);
        return a;
    }
    public int main(){
        int a;
        int j;
        int k;
        a = arrayDemo(10,3);
        return a;
    }
}