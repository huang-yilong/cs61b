import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
  @Test
  public void randomizedTest() {
    // @source StudentArrayDequeLauncher.java
    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
    StringBuilder ops = new StringBuilder();

    for (int i = 0; i < 10000; i++) {
      double r = StdRandom.uniform();

      if (r < 0.25) {
        sad.addFirst(i);
        ads.addFirst(i);
        ops.append(String.format("addFirst(%d)\n", i));
      } else if (r < 0.5) {
        sad.addLast(i);
        ads.addLast(i);
        ops.append(String.format("addLast(%d)\n", i));
      } else if (r < 0.75) {
        ops.append("removeFirst()\n");
        Integer exp = ads.size() > 0 ? ads.removeFirst() : null;
        Integer act = sad.size() > 0 ? sad.removeFirst() : null;
        assertEquals(ops.toString(), exp, act);
      } else {
        ops.append("removeLast()\n");
        Integer exp = ads.size() > 0 ? ads.removeLast() : null;
        Integer act = sad.size() > 0 ? sad.removeLast() : null;
        assertEquals(ops.toString(), exp, act);
      }

      // Sizes should always agree
      assertEquals(ops.toString(), ads.size(), sad.size());

      // Occasionally check a random index with get()
      if (ads.size() > 0 && StdRandom.uniform() < 0.1) {
        int idx = StdRandom.uniform(ads.size());
        ops.append("get(" + idx + ")\n");
        Integer exp = ads.get(idx);
        Integer act = sad.get(idx);
        assertEquals(ops.toString(), exp, act);
      }
    }
  }
}