package design;

public class Singleton {

    private static volatile Singleton instance;

    public Singleton(){
    }

    private static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
