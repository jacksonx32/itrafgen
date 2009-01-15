/*
 * ItrafgenApp.java
 */

package itrafgen;

import itrafgen.core.Graphicupdate;
import itrafgen.core.Point;
import itrafgen.core.ThEmeteur;
import itrafgen.core.ThRecepteur;
import itrafgen.gui.AttentionBox;
import itrafgen.gui.ItrafgenView;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;


import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;
import itrafgen.gui.FluxBox;
import itrafgen.gui.GraphBox;

import itrafgen.gui.Interfchooser;
import java.io.File;
import javax.swing.JFrame;

/**
 * The main class of the application.
 */
public class ItrafgenApp extends SingleFrameApplication {
    public static Vector<Long> droped = new Vector<Long>();
	public static Vector<Long> nbdroped = new Vector<Long>();
	public static NetworkInterface intemission;
	public static NetworkInterface intreception;
	public static Vector<FluxBox> flux = new Vector<FluxBox>();
	public static Vector<ThEmeteur> emflux = new Vector<ThEmeteur>();
	public static Vector<GraphBox> graphic = new Vector<GraphBox>();
	public static Vector<Vector<Point>> resultat = new Vector<Vector<Point>>();
	public static Vector<Boolean> launched = new Vector<Boolean>();
	public static String macemission;
	public static String macreception;
    public static int increment = 0;
    public static String ipemission;
    public static String ipreception;
    public static int nbupdate=0;
    public static double moyenne = 0;
    public static NetworkInterface[] devices;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //fenetre de choix!!
        
           

        
        

        
        //fenetre de choix!!


        
    	//System.out.print(" MAC address:");
         if(!(intemission.addresses.length ==0 || intemission.addresses.length ==0 || intemission.mac_address == null || intreception.mac_address == null)){
    	macemission = "";
    	  for (byte b : intemission.mac_address){
    		   macemission = macemission + (Integer.toHexString(b&0xff) + ":");
    	  }
      	macreception = "";
  	  for (byte b : intreception.mac_address){
  		macreception = macreception + (Integer.toHexString(b&0xff) + ":");
  	  }
    	  //System.out.println();
  	  ipemission = "";
  	  ipreception = "";

    	  //print out its IP address, subnet mask and broadcast address
          //erreur probable avec les addresse en IPv6 IPv4 : confusion complete :S
    	  NetworkInterfaceAddress a = intemission.addresses[0];
          if(!("" + a.address).contains(".")){//cas de mac os x (prend ipv6)
            a = intemission.addresses[1];
          }
    	    ipemission = ("" + a.address).split("/")[1];
            
      	  NetworkInterfaceAddress bb = intreception.addresses[0];
          if(!("" + bb.address).contains(".")){//cas de mac os x (prend ipv6)
            bb = intreception.addresses[1];
          }
  	    ipreception = ("" + bb.address).split("/")[1];
       }
         else{
              System.out.println("configure vos interfaces");
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AttentionBox dialog = new AttentionBox(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(
        (screenSize.width-dialog.getWidth())/2,
        (screenSize.height-dialog.getHeight())/2
        );
                
                dialog.getJLabel1().setText("Verifiez vos interfaces (pas d'adresse IP)");
                dialog.setVisible(true);
            }

        });
         }

    	ThRecepteur b = new ThRecepteur();

    	 Thread re = new Thread(b);
		 re.setPriority(Thread.MAX_PRIORITY);
		 re.start();

		 Graphicupdate updt = new Graphicupdate();
		 Thread up = new Thread(updt);
		 up.start();
        show(new ItrafgenView(this));
        
    }
    
   
    
        public static void updtgraph(){

    	for(int i = 0;i<graphic.size();i++){
    		if(launched.get(i)){
    			flux.get(i).getJProgressBar1().setValue(flux.get(i).getJProgressBar1().getValue() + 15);
    			if(flux.get(i).getJProgressBar1().getValue()>=100){
    				flux.get(i).getJProgressBar1().setValue(0);
    			}
    		}
    	}
		long max = 0;
		for(int o=0;o<graphic.size();o++){
            if(launched.get(o)){

            int nb = (int) (((double)500)/emflux.get(o).pause);
            
            graphic.get(o).series_ligne_1.setMaximumItemCount(nb);
			//if(nbupdate == 3){

	    		//nbupdate = 0;
	    		//sauvegarder la reception ...
	    		//if(resultat.get(o).size()>0){
	    		//long last = resultat.get(o).get((resultat.get(o).size() - 1)/3).temps;

	    			//vide les liste supprime les anciens points

	    		//graphic.get(o).series_ligne_1.removeAgedItems(resultat.get(o).get((resultat.get(o).size() - 1)/3).temps,true);
	    		/*for(int p=0;p<resultat.get(o).size()/3;p++){
	    			//graphic.get(o).suppoint(resultat.get(o).get(p).temps);
	    			resultat.get(o).removeElementAt(p);

	    		}*/

	    		//}
	    		//graphic.get(o).series_ligne_1.setMaximumItemCount(60);

	    		//moyenne(o);


	    	//}
			max = 0;
			long sum = 0;
		for(int i =0;i<resultat.get(o).size();i++){

	    		sum = sum + resultat.get(o).get(i).latence;

	    	//double moyenne = sum/resultat.get(o).size();
			graphic.get(o).ajoutpoint(resultat.get(o).get(i).temps, resultat.get(o).get(i).latence);
			if(resultat.get(o).get(i).latence>max){
				max = resultat.get(o).get(i).latence;

			}
		}
		moyenne = 0;
		if(resultat.get(o).size()!=0){
			moyenne = ((double) sum)/((double) resultat.get(o).size());
			//graphic.get(o).series_ligne_1.setDescription("Latence réseau - moyenne : " + moyenne + "ms");
			//System.out.println("moy ))) " + sum + "/" + resultat.get(o).size()+ "=" + moyenne);
            resultat.get(o).removeAllElements();
			DecimalFormat f = new DecimalFormat();
			f.setMaximumFractionDigits(2);
            

			graphic.get(o).chart.setTitle("Flux " + o + " - Latence (ms) \nMoyenne : " + f.format(moyenne) + " ms - drop : " + nbdroped.get(o) + " paquets");
            graphic.get(o).rangeAxis.setLowerBound(0);
            graphic.get(o).rangeAxis.setUpperBound(max + 5);
		}
		else{

			moyenne = 0;
			//graphic.get(o).series_ligne_1.setDescription("Latence réseau - moyenne : " + moyenne + "ms");
			//System.out.println(0);
            graphic.get(o).rangeAxis.setLowerBound(0);
            graphic.get(o).rangeAxis.setUpperBound(max + 5);
		}
		//resultat.get(o).removeAllElements();
		//max = 0;

		
		}
        else{
            graphic.get(o).chart.setTitle("Flux " + o + "Latence (ms) - Stop");
        }
            }
		//nbupdate++;
        
	}

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ItrafgenApp
     */
    public static ItrafgenApp getApplication() {
        return Application.getInstance(ItrafgenApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        try{
        devices = JpcapCaptor.getDeviceList();
        Interfchooser u = new Interfchooser();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        u.setLocation(
        (screenSize.width-u.getWidth())/2,
        (screenSize.height-u.getHeight())/2
        );
         u.show();
        //launch(ItrafgenApp.class, args);
        }
        catch(java.lang.UnsatisfiedLinkError i){
                          System.out.println("configure vos interfaces");
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AttentionBox dialog = new AttentionBox(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(
        (screenSize.width-dialog.getWidth())/2,
        (screenSize.height-dialog.getHeight())/2
        );
        dialog.getJLabel1().setText("Jpcap modifié n'est pas installé : aide en ligne");
        dialog.setVisible(true);
                }

        });
        }
        
    }
}
