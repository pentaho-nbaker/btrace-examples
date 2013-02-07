
import com.sun.btrace.AnyType;
import com.sun.btrace.aggregation.Aggregation;
import com.sun.btrace.aggregation.AggregationFunction;
import com.sun.btrace.aggregation.AggregationKey;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: nbaker
 * Date: 11/8/12
 */
@BTrace public class PentahoSystemHistorgram {

  private static Aggregation average = Aggregations.newAggregation(AggregationFunction.AVERAGE);
  private static Aggregation histogram = Aggregations.newAggregation(AggregationFunction.QUANTIZE);
  private static Aggregation count = Aggregations.newAggregation(AggregationFunction.COUNT);

//  @OnMethod(
//      clazz="org.pentaho.platform.engine.core.system.PentahoSystem",
//      method="get"
//  )
//
//  public static void onGet(AnyType[] args) {
//    String cn = str(args[0]);
//    AtomicInteger ai = Collections.get(histo, cn);
//    if (ai == null) {
//      ai = Atomic.newAtomicInteger(1);
//      Collections.put(histo, cn, ai);
//    } else {
//      Atomic.incrementAndGet(ai);
//    }
//  }

  @OnMethod(clazz = "org.pentaho.platform.engine.core.system.PentahoSystem", method = "get", location = @Location(Kind.RETURN))
  public static void onExecuteReturn(AnyType[] args, @Duration long durationL) {

    AggregationKey key = Aggregations.newAggregationKey(str(args[0]));
    int duration = (int) durationL / 1000;

    Aggregations.addToAggregation(histogram, key, duration);
    Aggregations.addToAggregation(average, key, duration);
    Aggregations.addToAggregation(count, key, 1);

  }

  @OnTimer(6000)
  public static void print() {
    println("-----------------");

    Aggregations.printAggregation("Count", count);
    Aggregations.printAggregation("Average Time", average);
    Aggregations.printAggregation("Histogram", histogram);

  }
}
