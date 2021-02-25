package test.com;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Group {
    private List<List<String>> lines;
    private Set<String> fields;

    public Group() {
        this.lines = new ArrayList<>();
        this.fields = new HashSet<>();
    }

    public boolean containsAnyWord(List<String> line) {
        for (String value : line) {
            boolean contains = fields.contains(value);
            if (contains) {
                return true;
            }
        }
        return false;
    }

    public void add(Collection<String> line) {
        lines.add((List<String>) line);
        fields.addAll(line.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()));
    }



    public void merge(Group other) {
        lines.addAll(other.lines);
        fields.addAll(other.fields);
        other.clear();
    }

    public void clear() {
        lines.clear();
        fields.clear();
    }

    public int size() {
        return lines.size();
    }

    @Override
    public String toString() {
        return "Group{" +
                "lines=" + lines +
                '}';
    }

    public Set<String> getWords() {
        return fields;
    }
}
