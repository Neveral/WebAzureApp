package servlets;

import java.util.*;

/**
 * Created by Neveral on 13.11.15.
 */
public class WShingling {

    public static final int SHINGLE_LENGTH = 2;


    public int wShingling(String text1, String text2){
        text1 = canonize(text1);
        text2 = canonize(text2);

        Long[] hash1 = genshingle(text1);
        Long[] hash2 = genshingle(text2);

        Set<Long> setOfHash1 = new HashSet<>(Arrays.asList(hash1));
        Set<Long> setOfHash2 = new HashSet<>(Arrays.asList(hash2));

        int generalSize = setOfHash1.size() + setOfHash2.size();

        setOfHash1.retainAll(setOfHash2); // intersects of sets
        int same = (int) (setOfHash1.size() * 2.0 / generalSize * 100.0);
        return same;
    }

    public String canonize(String text){
        //char[] stop_symbols = ".,!?:;-\n\r()".toCharArray();
        //text = text.replaceAll("[.,!?:;()]", "");
        text = text.replaceAll(System.lineSeparator(), " ");
        text = text.replaceAll("[\t]", " ");
        text = text.replaceAll(" +", " ");

        return text.toLowerCase();
    }

    public Long[] genshingle(String text){
        int shingleLen = SHINGLE_LENGTH; //длина шингла

        List<String> list = new ArrayList<>(Arrays.asList(text.split(" ")));

        int numOfShingles = list.size() - (SHINGLE_LENGTH - 1);

        Long[] out = new Long[numOfShingles];

        for (int i = 0; i < numOfShingles; i++) {
            //String subStr = text.substring(i, shingleLen);
            List subList = list.subList(i, shingleLen+i);
            //System.out.println(subList.toString() + " | " + subList.toString().hashCode());
            //byte[] arr = subList.toString().getBytes(StandardCharsets.UTF_8);
            //crc32.update(arr);
            //out[i] = crc32.getValue();
            out[i] = (long)subList.toString().hashCode();
        }


        //System.out.println("-------------------------");
        //System.out.println(Arrays.toString(out));

        return out;
    }


}
