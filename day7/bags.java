package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class bags {

    public static ArrayList<String> fileReader() throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader("day7/bags.txt"));
        String line = bufReader.readLine();
        ArrayList<String> rules = new ArrayList<>();

        while (line != null) {
            rules.add(line);
            line = bufReader.readLine();
        }

        return rules;
    }

    public static ArrayList<ArrayList<String>> rulesList(ArrayList<String> baseRules) {
        ArrayList<ArrayList<String>> fancyList = new ArrayList<>();
        String[] partRule;
        for (String rule : baseRules) {
            String ezRule = rule.replace("bags", "")
                    .replace(",", "")
                    .replace("contain", "")
                    .replace(".", "")
                    .replace("bag", "")
                    .replace(" ", "");
            partRule = ezRule.split("[0-9]");
            ArrayList<String> actualRule = new ArrayList<>(Arrays.asList(partRule));
            fancyList.add(actualRule);
        }
        return fancyList;
    }

    public static int countUniqueCharacters(ArrayList<String> input) {
        boolean[] isItThere = new boolean[Character.MAX_VALUE];

        for (String answer : input) {
            for (int i = 0; i < answer.length(); i++) {
                isItThere[answer.charAt(i)] = true;
                for (String answer2 : input) {
                    if (!answer2.contains(String.valueOf(answer.charAt(i)))) {
                        isItThere[answer.charAt(i)] = false;
                        break;
                    }
                }
            }
        }

        int count = 0;
        for (boolean b : isItThere) {
            if (b) {
                count++;
            }
        }

        return count;
    }

    public static ArrayList<String> containsMyBag(ArrayList<String> bagColors, ArrayList<ArrayList<String>> bagRules) {
        final ArrayList<String> newList = new ArrayList<>();
        for (String bagColor : bagColors) {
            for (ArrayList<String> currentColor : bagRules) {
                if (currentColor.contains(bagColor)
                        && !bagColor.equals(currentColor.get(0))
                        && !bagColors.contains(currentColor.get(0))) {
                    newList.add(currentColor.get(0));
                }
            }
        }
        return newList;
    }

    public static void main(String[] args) throws IOException {
        final ArrayList<String> bags = fileReader();
        final ArrayList<ArrayList<String>> list = rulesList(bags);

        final String myBag = "shinygold";
        ArrayList<String> colors = new ArrayList<>();
        colors.add(myBag);

        for (int i = 0; i < colors.size(); i++) {
            int oldSize = colors.size();
            colors.addAll(containsMyBag(colors, list));
            int newSize = colors.size();
            System.out.println(colors);
            if (newSize == oldSize) {
                break;
            }
        }

        Collections.sort(colors);
        for (int k = 0; k < colors.size() - 1; k++) {
            if (colors.get(k).equals(colors.get(k + 1))) {
                colors.remove(k + 1);
            }
        }
        System.out.println(colors.size()-1);
        System.out.println(colors);
    }
}
