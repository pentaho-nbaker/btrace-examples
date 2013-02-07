/**
 * User: nbaker
 * Date: 11/8/12
 */

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.aggregation.*;

@BTrace
public class PentahoSystemProfiler {
  /* put your code here */
  @TLS
  private static long starttime;
  private static Aggregation average = newAggregation(AggregationFunction.AVERAGE);
  private static Aggregation globalcount = newAggregation(AggregationFunction.COUNT);

  @OnMethod(
      clazz = "org.pentaho.platform.engine.core.system.PentahoSystem",
      method = "get"
  )
  public static void onCall() {
    println("enter this method!");
    starttime = timeNanos();
  }

  @OnMethod(
      clazz = "org.pentaho.platform.engine.core.system.PentahoSystem",
      method = "get",
      location = @Location(Kind.RETURN)
  )
  public static void onReturn() {
    println("Method End!");
    int duration = (int) (timeNanos() - starttime) / 1000000;
    println(duration);
    addToAggregation(average, duration);
    addToAggregation(globalcount, duration);
  }


  @OnTimer(20000)
  public static void onEvent() {
    println("----------------");
    printAggregation("Average", average);
    printAggregation("Global Count", globalcount);
    println("----------------");
  }

}