/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.core;

import itrafgen.ItrafgenApp;

/**
 *
 * @author sebastienhoerner
 */



public class Graphicupdate implements Runnable {
	public boolean a = true;

	public void run() {
		while(a){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			ItrafgenApp.updtgraph();

		}

	}

}
