/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.XMLPARSER;

/**
 *
 * @author sebastienhoerner
 */
import java.util.Vector;


public class champ {
	public String name;
	public String longueur;
	public String offset;
	public String valdef;
	public Vector<choice> lch;
	public champ(String name, String longueur, String offset, String valdef,
			Vector<choice> lch) {

		this.name = name;
		this.longueur = longueur;
		this.offset = offset;
		this.valdef = valdef;
		this.lch = lch;
	}


}
