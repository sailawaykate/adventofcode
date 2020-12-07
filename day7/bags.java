package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class bags {
    
    static Map<String, Integer> bagsIn = new HashMap<>();
    static Set<String> bagsOut = new HashSet<>();
    static private final Set<String> answer = new HashSet<>();
    
    private static int bagsInMine(Map<String, List<String>> graph, String key) {
        if(bagsIn.containsKey(key)) {
            return bagsIn.get(key);
        }
        bagsOut.add(key);
        int total = 0;
        for(String n : graph.getOrDefault(key, new ArrayList<>())) {
            String[] split = n.split(":");
            if (split[1].contains("other")) {
                continue;
            }
            int num = split[0].charAt(0) - '0';
            int d = bagsInMine(graph, split[1]);
            total += (num * d) + num;
        }
        bagsIn.put(key, total);
        return total ;
    }
    
    private static void bagsThatHaveMine(Map<String, List<String>> graph, String key) {
        bagsOut.add(key);
        for(String n : graph.getOrDefault(key, new ArrayList<>())) {
            if(!bagsOut.contains(n)) {
                answer.add(n);
                bagsThatHaveMine(graph, n);
            }
        }
    }
    
    public static void main(String[] args) {
        File file = new File("day7/bags.txt");
        final String myBag = "shiny gold";
        try {
            Map<String, List<String>> graph1 = new HashMap<>();
            Map<String, List<String>> graph2 = new HashMap<>();
            Scanner sc = new Scanner(file);
           
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] split = line.split("(\\sbags contain)");
                String container = split[0];
                String[] inside = split[1].split(",");
    
                for (String s : inside) {
                    String num = s.trim().split(" ")[0];
                    String color = s.replace(num, "")
                                    .replace("bags", "")
                                    .replace("bag", "")
                                    .replace(".", "")
                                    .trim();
                    List<String> n2 = graph2.getOrDefault(container, new ArrayList<>());
                    n2.add(num + ":" + color);
                    graph2.put(container, n2);
                    List<String> n1 = graph1.getOrDefault(color, new ArrayList<>());
                    n1.add(container);
                    graph1.put(color, n1);
                }
            }
            //part1
            bagsThatHaveMine(graph1, myBag);
            System.out.println(answer.size());
            
            //part2
            int total = bagsInMine(graph2, myBag);
            System.out.println(total);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
