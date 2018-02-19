package union_find;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Simple implementation of UnionFind data structure.
 * Maintain a partition of a set of objects
 *
 * @param T object type that implements UnionFindObj interface
 * @author Ziang Lu
 */
public class UnionFind <T> {

    /**
     * Groups of the entire set.
     * Maintain a linked structure, and each subset has an arbitrary leader
     * (representative of the group) object.
     */
    HashMap<String, ArrayList<UnionFindObj>> groups;

    /**
     * Constructor with parameter.
     * @param objs list of sole objects
     */
    public UnionFind(ArrayList<T> objs) {
        groups = new HashMap<String, ArrayList<UnionFindObj>>();
        for (T obj : objs) {
            UnionFindObj o = (UnionFindObj) obj;
            ArrayList<UnionFindObj> group = new ArrayList<UnionFindObj>();
            group.add(o);
            groups.put(o.name(), group);
        }
    }

    /**
     * Returns the name of the group that the given object belongs to.
     * @param obj given object
     * @return name of the group
     */
    public String find(UnionFindObj obj) {
        return obj.leader().name();
        // Running time complexity: O(1)
    }

    /**
     * Fuses the given two groups together.
     * Objects in the first group and objects in the second group should all
     * coalesce, and be now in one single group.
     * @param groupNameA name of the first group
     * @param groupNameB name of the second group
     */
    public void union(String groupNameA, String groupNameB) {
        // Check whether the input strings are null or empty
        if ((groupNameA == null) || (groupNameA.length() == 0) || (groupNameB == null) || (groupNameB.length() == 0)) {
            throw new IllegalArgumentException("The input group names should not be null or empty.");
        }
        // Check whether the input group names exist
        if (!(groups.containsKey(groupNameA)) || !(groups.containsKey(groupNameB))) {
            throw new IllegalArgumentException("The input group names don't both exist.");
        }

        ArrayList<UnionFindObj> groupA = groups.get(groupNameA), groupB = groups.get(groupNameB);
        // In order to reduce the number of leader updates, let the smaller group inherit the leader of the larger one.
        if (groupA.size() >= groupB.size()) {
            UnionFindObj groupALeader = groupA.get(0).leader();
            updateLeader(groupB, groupALeader);
        } else {
            UnionFindObj groupBLeader = groupB.get(0).leader();
            updateLeader(groupA, groupBLeader);
        }
        // Running time complexity: O(n)
    }

    /**
     * Private helper method to update leader of the given group to the given new leader.
     * @param group given group
     * @param newLeader given new leader
     */
    private void updateLeader(ArrayList<UnionFindObj> group, UnionFindObj newLeader) {
        for (UnionFindObj obj : group) {
            obj.setLeader(newLeader);
        }
        // Running time complexity: O(n)
    }

}