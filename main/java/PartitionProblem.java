import java.util.ArrayList;
import java.util.HashMap;

public class PartitionProblem {

    private static HashMap<String,Long> precompute = new HashMap<>();
    private static HashMap<String,Long> precompute2 = new HashMap<>();
    private static StringBuilder sb = new StringBuilder();
    public static void main(String [] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            arrayList.add(i);
        }
        System.out.println(partitionWithCombination2(n, arrayList));
        System.out.println(partitionWithPermutation(n, arrayList, new ArrayList<Integer>()));
        precompute = new HashMap<>();
        System.out.println(partitionWithCombination3(n, arrayList));

        //precompute.forEach((k,v) -> System.out.println(k + " -> " + v));

    }

    private static String getHashKey(int sum, ArrayList<Integer> setToUse) {
        sb.delete(0,sb.length());
        sb.append(sum);
        sb.append(",");
        sb.append(setToUse.toString());
        return sb.toString();
    }

    private static long partitionWithCombination2(int sum, ArrayList<Integer> setToUse) {
        if (precompute.containsKey(getHashKey(sum, setToUse))) {
            return precompute.get(getHashKey(sum, setToUse));
        }
        if (sum == 0) {
            precompute.put(getHashKey(sum, setToUse), 1L);
            return 1;
        } else if (sum < 0) {
            precompute.put(getHashKey(sum, setToUse), 0L);
            return 0;
        }
        if (setToUse.size() == 0) {
            precompute.put(getHashKey(sum, setToUse), 0L);
            return 0;
        }
        int lastInSet = setToUse.get(setToUse.size() - 1);
        long retVal = partitionWithCombination2(sum - lastInSet, setToUse);
        lastInSet = setToUse.remove(setToUse.size() - 1);
        retVal +=  partitionWithCombination2(sum, setToUse);
        setToUse.add(lastInSet);
        precompute.put(getHashKey(sum, setToUse), retVal);

        return retVal;
    }

    private static long partitionWithCombination3(int sum, ArrayList<Integer> setToUse) {
        if (precompute.containsKey(getHashKey(sum, setToUse))) {
            return precompute.get(getHashKey(sum, setToUse));
        }
        if (sum == 0) {
            precompute.put(getHashKey(sum, setToUse), 1L);
            return 1;
        } else if (sum < 0) {
            precompute.put(getHashKey(sum, setToUse), 0L);
            return 0;
        }
        if (setToUse.size() == 0) {
            precompute.put(getHashKey(sum, setToUse), 0L);
            return 0;
        }
        int lastInSet = setToUse.get(setToUse.size() - 1);
        //Convert from recursive to iterative
        //ArrayList<Integer> k = new ArrayList<>(setToUse);
        //k.remove(k.size() - 1);
        //System.out.print("F[" + sum + "," + setToUse.toString() + "] =");
        //System.out.print(" F[" +  (sum - lastInSet) + "," + setToUse.toString() + "] + ");
        //System.out.println("F[" + sum + "," + k.toString() + "]");

        long retVal = partitionWithCombination3(sum - lastInSet, setToUse);
        lastInSet = setToUse.remove(setToUse.size() - 1);
        retVal +=  partitionWithCombination3(sum, setToUse);
        setToUse.add(lastInSet);
        precompute.put(getHashKey(sum, setToUse), retVal);

        return retVal;
    }

    private static long partitionWithPermutation(int sum, ArrayList<Integer> setToUse,
                                                 ArrayList<Integer> ways) {
//        if (sum == 0) {
//            //System.out.println(ways.toString());
//        }
        if (precompute2.containsKey(getHashKey(sum, ways))) {
            return precompute2.get(getHashKey(sum, setToUse));
        }
        if (sum == 0) {
            precompute2.put(getHashKey(sum, ways), 1L);
            //System.out.println(ways.toString());
            return 1;
        } else if (sum < 0) {
            precompute2.put(getHashKey(sum, ways), 0L);
            return 0;
        }
        if (setToUse.size() == 0) {
            precompute2.put(getHashKey(sum, ways), 0L);
            return 0;
        }
        long retVal = 0;
        int limit = setToUse.size();
        //limit = (limit%2 == 0)? limit /2 : (limit +1) / 2;
        for (int i = 0; i < limit; i++) {
            int lastInSet = setToUse.get(i);
            if (sum - lastInSet >= 0) {
                ways.add(lastInSet);
                retVal += partitionWithPermutation(sum - lastInSet, setToUse, ways);
                ways.remove(ways.size() - 1);
            }
            //lastInSet = setToUse.remove(i);
            //retVal += partitionWithPermutation(sum, setToUse, ways);
            //setToUse.add(i, lastInSet);
        }
        precompute2.put(getHashKey(sum, ways), retVal);

        return retVal;
    }
}
