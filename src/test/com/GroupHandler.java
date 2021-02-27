package test.com;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupHandler {
    private Set<Group> groups = new HashSet<>();
    private Map<String, Group> wordToGroup = new HashMap<>();

    public void addLine(List<String> line) {
        Group firstFoundGroup = null;
        List<String> wordsToAdd = new ArrayList<>();
        List<String> line1=line.stream().distinct().collect(Collectors.toList());
        for (String word : line1) {
            if (wordToGroup.containsKey(word)) {
                if (firstFoundGroup == null) {
                    firstFoundGroup = wordToGroup.get(word);
                    firstFoundGroup.add(line);
                }
                else if(firstFoundGroup.findcontain(word))
                {
                    continue;
                }
                else {
                    Group currentGroup = wordToGroup.get(word);
                    wordsToAdd.addAll(currentGroup.getWords());
                    firstFoundGroup.merge(currentGroup);
                    groups.remove(currentGroup);
                }
            }
        }
        if (firstFoundGroup == null) {
            Group newGroup = new Group();
            newGroup.add(line);
            groups.add(newGroup);
            addWordsToGroupHash(line, newGroup);
        } else {
            addWordsToGroupHash(line, firstFoundGroup);
            addWordsToGroupHash(wordsToAdd, firstFoundGroup);
        }
    }

    public Set<Group> getGroups() {
        return groups;
    }

    private void addWordsToGroupHash(Collection<String> line, Group firstFoundGroup) {
        for (String value : line) {
            if (!value.isEmpty()) {
                wordToGroup.put(value, firstFoundGroup);
            }
        }
    }
}
