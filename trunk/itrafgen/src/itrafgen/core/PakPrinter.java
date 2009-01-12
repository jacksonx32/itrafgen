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
import java.io.UnsupportedEncodingException;
import java.util.Vector;



import jpcap.PacketReceiver;
import jpcap.packet.Packet;

class PakPrinter implements PacketReceiver {
	  //this method is called every time Jpcap captures a packet

	  public void receivePacket(Packet packet) {

	    //just print out a captured packet
		  java.util.Date today = new java.util.Date();
		  long oo = today.getTime();
		  long a=0;
		  long ao=0;
		  //System.out.println("jfkjdskfjkdsjfkdsjfkds -----");
		 // System.out.println(packet);

	    try {

	    	/*if(new String(packet.data,"UTF-8")!=null & new String(packet.data,"UTF-8").split(" ts:")[1] != null){
	    		a = Long.parseLong(new String(packet.data,"UTF-8").split(" ts:")[1]);
	    		//ao = Integer.parseInt(new String(packet.data,"UTF-8").split(" ts:")[0].split(" nb:")[1]);
	    		//System.out.println("envoye : " + a);

	    	}
			System.out.println(("recu : " + oo  + "\nrecu : " + packet.sec + "" + packet.sec));
			*/
			//System.out.println("recuvrai : " + packet.sec);
			//System.out.println("num : " + ao + " latence : " + "" + (oo - a) + " microsecond");
			//Singleton.recu.add(new Point(oo,oo-a));
			//Principale.ajoutpoint(oo, oo-a);

			int vl=0;
			String wow = new String(packet.data,"UTF-8");
            if(wow.contains("!itrafgen")){


			long timestamp = 0;
			long num = 0;
			if(wow.contains(" ts:")){
				String[] oop = wow.split(" ts:");
				timestamp = Long.parseLong(oop[1]);
				String[] oop2 = oop[0].split(" vlan:");
				vl = Integer.parseInt(oop2[1]);
				String[] oop3 = oop2[0].split(" num:");
				num = Long.parseLong(oop3[1]);
				ItrafgenApp.resultat.get(vl).add(new Point(oo, oo-timestamp));
				//System.out.println("******************* CAPTE TS : " + timestamp + "******" + (oo-timestamp) + "***** vlan:" + vl);
			}
           // System.out.println("relevé : " + num + " attendu :" + ItrafgenApp.droped.get(vl));
           // System.out.println("nbdrop : " + ItrafgenApp.nbdroped.get(vl));
			if((long) ItrafgenApp.droped.get(vl) != (num)){
                //System.out.println("droped----");
				ItrafgenApp.nbdroped.set(vl, ItrafgenApp.nbdroped.get(vl)+1);


			}
			ItrafgenApp.droped.set(vl, num + 1);
            }



		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // System.out.println(packet);

	  }
	}
