package com.demo.demosignup.algopractive.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DuplicateAlgo {

    public static void main(String[] args) {
        //Test
        //String input="programming";
        String input="ABbCcdD!!&&%%%";
        System.out.print("duplicate2---");
        //findDuplicateTwoLoopCharacter(input);
        //findDuplicateByCharacterCount(input);
       // findDuplicateByHashSetCharacter(input);
        findDuplicateCharByHashMApCount(input.toLowerCase());
    }

    //Find Duplicate Characters
    //Approach 2 Time and space complexity
    //Explanation
    /*
    programming
    p r o g r a m m i n g
    0 1 2 3 4 5 6 7 8 9 10
    0->1 .................10
      ...
      ..
      10
    */


    public static void findDuplicateTwoLoopCharacter(String str){
        char[] splitChar=str.toCharArray();
        //first for loop iterate over array of character
        for (int i=0; i<splitChar.length; i++){
            //next loop iterate over array remaining and compare with each other
            for (int j=i+1;j<splitChar.length;j++){
              if (splitChar[i] == splitChar[j])  {
                  System.out.print("--   "+splitChar[i]);
              }
            }

        }
    }

    public static void findDuplicateByCharacterCount(String str){
        int[] characterArray= new int[256];//Ascii character
        for (char character: str.toCharArray()){
            characterArray[character]++;
        }

        for (int i=0; i<256;i++){

            if (characterArray[i]>1){
                System.out.println(""+i+"---"+characterArray[i]+"---"+(char)i);
            }
        }
    }
    public static void findDuplicateByHashSetCharacter(String str){

        HashSet<String> setOfCharacters=new HashSet<String>();
        HashSet<String> setOfDuplicateCharacters=new HashSet<String>();

        for (int i=0; i<str.length();i++){
            String character=String.valueOf( str.charAt(i));
            if (!setOfCharacters.add(character)){
                setOfDuplicateCharacters.add(character);
            }
        }
        for (String value:setOfDuplicateCharacters){
            System.out.println(value);
        }

    }

    public static void findDuplicateCharByHashMApCount(String input){
        HashMap<Character, Integer> mapDuplicateChar=new HashMap<>();
        for (char c:input.toCharArray()){
            if (mapDuplicateChar.containsKey(c)){
                mapDuplicateChar.put(c,mapDuplicateChar.get(c)+1);
            }else {
                mapDuplicateChar.put(c,1);
            }
        }

        for (Map.Entry<Character,Integer> entry:mapDuplicateChar.entrySet()){
            if (entry.getValue()>1){
                System.out.println("--"+entry.getKey()+"--"+entry.getValue());
            }
        }
    }
}
