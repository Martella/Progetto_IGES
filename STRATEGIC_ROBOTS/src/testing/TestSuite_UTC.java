package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
			
@Suite.SuiteClasses({				
  UTC_Robot.class,
  UTC_RobotCombattente.class,
  UTC_RobotLavoratore.class,
  UTC_Ostacolo.class,
  UTC_BarraEnergia.class,
  UTC_BancoRifornimenti.class,
})

public class TestSuite_UTC {

}
