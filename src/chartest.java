import java.util.*;

public class chartest {
    public static void main(String[] args) {

        ArrayList<String> stringArray = new ArrayList<>();

        Set<Character> setChar = new LinkedHashSet<>();

        String[] s1 = {"cat", "car", "care"};


        for(String word: s1){
            stringArray.add(word);

        }

        System.out.println(stringArray);

        for(String word: stringArray){

            for(int i = 0; i< word.length();i++){
                setChar.add(word.charAt(i));

            }
        }
        System.out.println(setChar);


//        System.out.println(stateMap.get(1));
    }
}
