import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.aggregation.AggregationKey;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

/**
 * User: nbaker
 * Date: 11/9/12
 */
@BTrace
public class MetadataLoad {


  @OnMethod(clazz = "org.pentaho.platform.plugin.services.metadata.SecurityAwareMetadataDomainRepository", method = "<init>")
  public static void printMetadataInit() {

    println("Loaded Metadata repository");
    BTraceUtils.Threads.jstack();

  }

  @OnMethod(clazz = "org.pentaho.platform.plugin.services.pluginmgr.servicemgr.DefaultServiceManager.getServiceBean", method = "getServiceBean")
  public static void serviceBeanCall(AnyType[] args) {

    println(strcat("getServiceBean::", str(args[0])));


  }

}
