class James{
    public int test(int a, int b){
        Joe c;
        int n_j;
        c = new Joe();
        n_j = c.j * 2 + 1;
        return c.joe(a, b);
    }
}
class Joe{
    int j;
    public int joe(int a, int b){
        return a + b;
    }
}