class testing_lang{

    public static void main(String[] args){
        System.out.println("Hello world!");
        IntList lst = new IntList();
        int c = 0;
        while (c < 10){
            lst.append(c);
            c++;
        }
        int my_arr[];
        System.out.println("length");
        System.out.println('C');
        System.out.println(lst.length());
        if (lst instanceof StringList){
            System.out.println("should not be seeing this");
        }
        for (int i = 10; i >= 0; i--){
            if (i != 20 && (i <= 2 || true || false)){
                switch (i%2){
                    case 0:
                        lst.append(i);
                        break;
                    case 1:
                        break;
                    default:
                        int new_val = (10 + 2)*2 / 1;
                }
                break;
            }
        }
    }
}

class IntList{
    IntListNode default_start = null;
    int length = 0;
    public void append(int val){
        if (default_start == null){
            default_start = new IntListNode();
        }
        default_start.append(val);
        length++;
    }
    public int length(){
        return length;
    }
}

class IntListNode{
    int value = -10000;
    boolean set = false;
    IntListNode next = null;
    public void append(int val){
        if (!set){
            this.set_value(val);
        }
        else{
            if (next == null){
                next = new IntListNode();
            }
            next.append(val);
        }
    }
    public void set_value(int val){
        value = val;
        set = true;
    }
}

class StringList extends IntList{
    int length = 0;
    public void append(String val){
        
        length++;
    }
    public int length(){
        return super.length();
    }
}