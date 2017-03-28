import io.rainfall.AssertionEvaluator;
import io.rainfall.Configuration;
import io.rainfall.Operation;
import io.rainfall.Reporter;
import io.rainfall.Runner;
import io.rainfall.Scenario;
import io.rainfall.SequenceGenerator;
import io.rainfall.SyntaxException;
import io.rainfall.TestException;
import io.rainfall.configuration.ConcurrencyConfig;
import io.rainfall.generator.RandomSequenceGenerator;
import io.rainfall.generator.StringGenerator;
import io.rainfall.statistics.StatisticsHolder;
import io.rainfall.statistics.StatisticsPeekHolder;
import io.rainfall.unit.TimeDivision;
import org.ehcache.service.Ex4Service;
import org.ehcache.service.SomeService;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.cache.management.CacheStatisticsMXBean;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import static io.rainfall.configuration.ReportingConfig.html;
import static io.rainfall.configuration.ReportingConfig.report;
import static io.rainfall.execution.Executions.during;
import static io.rainfall.generator.sequence.Distribution.SLOW_GAUSSIAN;

/**
 * @author Aurelien Broszniowski
 */
public class PerfTestEx4 {

  private static int entriesMaxCount = 100;
//  private static int entriesMaxCount = 200;
//  private static int entriesMaxCount = 1000;

  @Test
  @Ignore
  public void perfTest() throws SyntaxException, URISyntaxException {
    final String opName = "SomeServiceOperation";

    SomeService service = new Ex4Service();

    StringGenerator generator = new StringGenerator(4);

    SequenceGenerator sequenceGenerator = new RandomSequenceGenerator(SLOW_GAUSSIAN, 0, entriesMaxCount, entriesMaxCount / 10);

    Runner.setUp(
        Scenario.scenario("load test")
            .exec(new Operation() {
              @Override
              public void exec(final StatisticsHolder statisticsHolder,
                               final Map<Class<? extends Configuration>, Configuration> map,
                               final List<AssertionEvaluator> list) throws TestException {
                long next = sequenceGenerator.next();
                String id = generator.generate(next);

                long start = getTimeInNs();
                // This is what we measure
                service.someLogic(id);
                //
                long end = getTimeInNs();
                statisticsHolder.record(opName, (end - start), Results.READ);
              }

              @Override
              public List<String> getDescription() {
                return Collections.singletonList("someService operation");
              }
            })
    )
        .warmup(during(20, TimeDivision.seconds))
        .executed(during(5, TimeDivision.minutes))
        .config(ConcurrencyConfig.concurrencyConfig()
            .threads(Runtime.getRuntime().availableProcessors())
            .timeout(30, TimeUnit.MINUTES))
        .config(report(Results.class).log(
            new Reporter() {
              CacheStatisticsMXBean cacheStatisticsMXBean;

              @Override
              public void header(final List list) {
                try {
                  MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
                  ObjectName objectName = new ObjectName("javax.cache:type=CacheStatistics,CacheManager="
                                                         + getClass().getResource("/ehcache-ex4.xml")
                                                             .toURI()
                                                             .toString()
                                                             .replace(":", ".") + ",Cache=someCache4");
                  cacheStatisticsMXBean = MBeanServerInvocationHandler.newProxyInstance(beanServer, objectName, CacheStatisticsMXBean.class, false);
                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              }

              @Override
              public void report(final StatisticsPeekHolder statisticsPeekHolder) {
                System.out.printf("Cache Gets: %d, ", cacheStatisticsMXBean.getCacheGets());
                System.out.printf("Cache Miss count: %d, ", cacheStatisticsMXBean.getCacheMisses());
                System.out.printf("Cache Hit percentage: %f, ", cacheStatisticsMXBean.getCacheHitPercentage());
                System.out.printf("Cache Evictions count: %d\n", cacheStatisticsMXBean.getCacheEvictions());
              }

              @Override
              public void summarize(final StatisticsHolder statisticsHolder) {

              }
            }, html("Rainfall-report-" + entriesMaxCount)))
        .start();
  }

  enum Results {READ}
}
