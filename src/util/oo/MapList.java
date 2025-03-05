package util.oo;

import java.util.*;

public class MapList {

    private final List<SortedMap<String, Object>> data = new ArrayList<>();

    public void add(SortedMap<String, Object> map) {
        data.add(map);
    }

    public List<SortedMap<String, Object>> getData() {
        return data;
    }

    public static SortedMap<String, Object> createMap(Object... keyValues) {
        SortedMap<String, Object> map = new TreeMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put((String) keyValues[i], keyValues[i + 1]);
        }
        return map;
    }

    public static List<SortedMap<String, Object>> createList(SortedMap<String, Object>... maps) {
        return new ArrayList<>(Arrays.asList(maps));
    }
}
