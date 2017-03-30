import io.rainfall.AssertionEvaluator;
import io.rainfall.Configuration;
import io.rainfall.Operation;
import io.rainfall.Runner;
import io.rainfall.Scenario;
import io.rainfall.SequenceGenerator;
import io.rainfall.SyntaxException;
import io.rainfall.TestException;
import io.rainfall.configuration.ConcurrencyConfig;
import io.rainfall.generator.LongGenerator;
import io.rainfall.generator.RandomSequenceGenerator;
import io.rainfall.statistics.StatisticsHolder;
import io.rainfall.unit.TimeDivision;
import org.ehcache.service.Ex3Service;
import org.ehcache.service.SomeService;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.rainfall.configuration.ReportingConfig.html;
import static io.rainfall.configuration.ReportingConfig.report;
import static io.rainfall.configuration.ReportingConfig.text;
import static io.rainfall.execution.Executions.during;
import static io.rainfall.generator.sequence.Distribution.SLOW_GAUSSIAN;

/**
 * @author Aurelien Broszniowski
 */
public class PerfTestEx3 {

  private static final int ENTRIES_MAX_COUNT = 100;
//  private static int ENTRIES_MAX_COUNT = 200;
//  private static int ENTRIES_MAX_COUNT = 1000;

  @Test
  @Ignore
  public void perfTest() throws SyntaxException, URISyntaxException {
    final String opName = "SomeServiceOperation";

    SomeService service = new Ex3Service();

    LongGenerator generator = new LongGenerator();

    SequenceGenerator sequenceGenerator = new RandomSequenceGenerator(SLOW_GAUSSIAN, 0, ENTRIES_MAX_COUNT, ENTRIES_MAX_COUNT / 10);

    Runner.setUp(
        Scenario.scenario("load test")
            .exec(new Operation() {
              @Override
              public void exec(final StatisticsHolder statisticsHolder,
                               final Map<Class<? extends Configuration>, Configuration> map,
                               final List<AssertionEvaluator> list) throws TestException {
                long next = sequenceGenerator.next();
                Long id = generator.generate(next);

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
        .executed(during(1, TimeDivision.minutes))
        .config(ConcurrencyConfig.concurrencyConfig()
            .threads(Runtime.getRuntime().availableProcessors())
            .timeout(30, TimeUnit.MINUTES))
        .config(report(Results.class).log(text(), html("./target/Ex3/Rainfall-report-" + ENTRIES_MAX_COUNT)))
        .start();
  }

  enum Results {READ}
}
