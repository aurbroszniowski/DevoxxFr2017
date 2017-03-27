import io.rainfall.AssertionEvaluator;
import io.rainfall.Configuration;
import io.rainfall.Operation;
import io.rainfall.Runner;
import io.rainfall.Scenario;
import io.rainfall.SequenceGenerator;
import io.rainfall.SyntaxException;
import io.rainfall.TestException;
import io.rainfall.configuration.ConcurrencyConfig;
import io.rainfall.generator.RandomSequenceGenerator;
import io.rainfall.generator.StringGenerator;
import io.rainfall.statistics.StatisticsHolder;
import io.rainfall.unit.TimeDivision;
import org.ehcache.service.Ex2Service;
import org.ehcache.service.SomeService;
import org.junit.Ignore;
import org.junit.Test;

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
public class PerfTest {

  @Test
  @Ignore
  public void perfTest() throws SyntaxException {
    final String opName = "SomeServiceOperation";

    SomeService service = new Ex2Service();

    StringGenerator generator = new StringGenerator(4);
    SequenceGenerator sequenceGenerator = new RandomSequenceGenerator(SLOW_GAUSSIAN, 0, 1000, 100);

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
        .warmup(during(45, TimeDivision.seconds))
        .executed(during(1, TimeDivision.minutes))
        .config(ConcurrencyConfig.concurrencyConfig().threads(4).timeout(30, TimeUnit.MINUTES))
        .config(report(Results.class).log(text(), html()))
        .start();
  }

  enum Results {READ}
}
