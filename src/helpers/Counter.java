package helpers;

import java.util.HashMap;

public class Counter<T> {
    private final HashMap<T,Integer> map = new HashMap<>();

    public int get(T key) {
        final Integer n = map.get(key);
        return n == null ? 0 : n;
    }

    public void count(T key) {
        map.put(key, get(key) + 1);
    }

    public T getMostCommon(){
        int count = 0;
        T mostCommonKey = null;

        for (T key: map.keySet() ){
            int aux = get(key);
            if(aux > count){
                count = aux;
                mostCommonKey = key;
            }
        }

        return mostCommonKey;
    }
}