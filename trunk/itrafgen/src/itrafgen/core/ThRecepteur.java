/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.core;

/**
 *
 * @author sebastienhoerner
 */

import itrafgen.ItrafgenApp;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;




import jpcap.*;

import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import jpcap.packet.IPPacket;

import jpcap.packet.TCPPacket;

//demarrer le thread ...



public class ThRecepteur implements Runnable

{

	JpcapCaptor captor = null;
	public void run() {
		//NetworkInterface[] devices = JpcapCaptor.getDeviceList();

		try {
			captor = JpcapCaptor.openDevice(ItrafgenApp.intreception, 65535, true, 10);

			//Singleton.intemission.mac_address.toString();

			//captor.setFilter("src host " + Singleton.ipsrc, true); //filtre

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//		call processPacket() to let Jpcap call PacketPrinter.receivePacket() for every packet capture.
		//captor.processPacket(10,new PacketPrinter());
		// modif
		//captor.loopPacket(30, new P akPrinter());
		captor.loopPacket(-1, new PakPrinter());



		captor.close();

	}



}



