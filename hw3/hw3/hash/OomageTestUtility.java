package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        ArrayList<Oomage>[] buckets = new ArrayList[M];
        int oomageNum = oomages.size();
        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (Oomage o : oomages) {
            int index = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[index].add(o);
        }
        for (ArrayList<Oomage> bucket : buckets) {
            if (bucket.size() < oomageNum / 50 || bucket.size() > oomageNum / 2.5) {
                return false;
            }
        }
        return true;
    }
}
