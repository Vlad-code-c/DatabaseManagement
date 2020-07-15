package main;

public class RestrictionVerify {
    /**
     * 1. First letter always is letter (latin)
     * 2. Can contains only letters and numbers (0-9)
     * 3. Cant be empty
     * 4. Max size can be 20 (my custom value)
     * @return {@code true} if name respect all this rules, {@code false} otherwise
     * */
    public static boolean tableNameVerify(String name){
        //3
        if(name.isEmpty())
            return false;
        //1
        if(!isLetter(name.charAt(0)))
            return false;
        //2
        if(!onlyLettersAndNumbers(name))
            return false;
        //4
        if(name.length() > 20)
            return false;

        return true;
    }

    /**
     * 1. First letter always is letter (latin)
     * 2. Can contains only letters and numbers (0-9)
     * 3. Cant be empty
     * 4. Max size can be 20 (my custom value)
     * @return {@code true} if name respect all this rules, {@code false} otherwise
     * */
    public static boolean colNameVerify(String name){
        //3
        if(name.isEmpty())
            return false;
        //1
        if(!isLetter(name.charAt(0)))
            return false;
        //2
        if(!onlyLettersAndNumbers(name))
            return false;
        //4
        if(name.length() > 20)
            return false;

        return true;
    }

    /**
     * @param str
     * @return {@code true} if str contains only latin letters from a to z, and numbers from 0 to 9 , {@code false} otherwise
     * */
    static private boolean onlyLettersAndNumbers(String str){
        char[] letters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        char[] ch = str.toCharArray();

        for(int i = 0; i < ch.length; i++){
            boolean cont = false;

            for(int j = 0; j < letters.length; j++){
                if(letters[j] == ch[i]){
                    cont = true;
                }
            }

            if(!cont)
                return false;
        }
        return true;
    }

    /**
     * @param ch
     * @return {@code true} if ch is a letter, {@code false} otherwise
     * */
    static private boolean isLetter(char ch){
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for(int i = 0; i < letters.length; i++){
            if(Character.toLowerCase(ch) == letters[i])
                return true;
        }
        return false;
    }


}
