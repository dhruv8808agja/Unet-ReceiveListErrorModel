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
   container.shell.addInitrc "${script.parent}/fshrc.groovy"
   container.add 'dae1', new PingDaemon()
  }
  node '2', address: 2, location: [0, 0, 0], shell: 1102, stack: { container ->
    container.shell.addInitrc "${script.parent}/fshrc.1.groovy"
    container.add 'dae2', new PingDaemon()
    

  }
  node '3', address: 3, location: [0, 0, 0], shell: 1103, stack: { container ->
    container.add 'dae3', new PingDaemon()
    container.shell.addInitrc "${script.parent}/fshrc.2.groovy"

  }
}
