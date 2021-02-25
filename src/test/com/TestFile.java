package test.com;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TestFile {

    private static List<List<String>> readFile(String filePath) throws IOException {
        List<List<String>> lines = Files.readAllLines(Paths.get(filePath)).parallelStream()
                .map(str -> getLine(str))
                .filter(list -> list.size() == 3)
              //  .distinct()
                .collect(Collectors.toList());
        return lines;
    }

    private static List<String> getLine(String str) {
        List<String> strings = Arrays.asList(str.split(";"));
        return strings.stream()
//                .map(value -> value.replace("\"", ""))
                .map(value -> value.substring(1, value.length() - 1))
                .collect(Collectors.toList());
    }

    private static Set<Group> findGroupe(List<List<String>> lines) {
        GroupHandler groupHandler = new GroupHandler();
        for (int i = 0; i < lines.size(); i++) {
            List<String> current = lines.get(i);
            groupHandler.addLine(current);
        }
        return groupHandler.getGroups();
    }

    public static void main(String[] args) throws IOException {
        List<List<String>> lines = readFile("F:\\TestForJob\\src\\lng.csv");//new ArrayList<>();
        Set<Group> groups = findGroupe(lines);
        List<Group> lists = new ArrayList<>(groups);
        lists.sort((l1, l2) -> l2.size() - l1.size());
        long count=lists.stream().filter(r ->r.size() > 1)
                .count();

        try (PrintWriter writer = new PrintWriter(new File("output.txt"))) {
            writer.write("Count of groups with more than one element: "+(int) count+"\n");
            for (int i = 0; i < lists.size(); i++) {
                writer.write("Group : " + i + ": " + lists.get(i) + "\n");
            }
        }
    }
}
