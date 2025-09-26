import java.util.*;

public class chartest {
    public static void main(String[] args) {
        Map<Integer, char[]> stateMap = new HashMap<>();

        List<String> stringList = new ArrayList<>();


        stringList.add("cat");
        stringList.add("car");
        stringList.add("care");

        for(String word: stringList) {
            int id = 0;
            char[] c = word.toCharArray();
            stateMap.put(id, c);
            System.out.println(c);
        }



        System.out.println(stateMap.get(1));
    }
}
