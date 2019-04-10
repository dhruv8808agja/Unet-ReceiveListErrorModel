//! Simulation: 3-node network with ping daemons
///////////////////////////////////////////////////////////////////////////////
///
/// To run simulation:
///   bin/unet samples/ping/ping-sim
///
///////////////////////////////////////////////////////////////////////////////

import org.arl.fjage.*
import org.arl.unet.phy.*

///////////////////////////////////////////////////////////////////////////////
// display documentation


///////////////////////////////////////////////////////////////////////////////
// simulator configuration

platform = RealTimePlatform



// run simulation forever
  simulate {
  node '1', address: 1, location: [0, 0, 0], shell: true, stack: { container ->
   container.shell.addInitrc "${script.parent}/script1.groovy"
   container.add 'dae1', new MyAgent()
  }
  node '2', address: 2, location: [0, 0, 0], shell: 1102, stack: { container ->
    container.shell.addInitrc "${script.parent}/script2.groovy"
    container.add 'dae2', new MyAgent()
    

  }
  node '3', address: 3, location: [0, 0, 0], shell: 1103, stack: { container ->
    container.add 'dae3', new MyAgent()
    container.shell.addInitrc "${script.parent}/script3.groovy"

  }
}
