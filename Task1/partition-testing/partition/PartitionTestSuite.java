package partition;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    CatanTest.class,
    CityTest.class,
    SettlementTest.class,
    RoadTest.class,
    ResourceHexTest.class
})
public class PartitionTestSuite {}