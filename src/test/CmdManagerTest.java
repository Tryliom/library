package test;

import org.junit.Test;

import me.alexishaldy.classes.Library;
import me.alexishaldy.util.CmdManager;

public class CmdManagerTest {
	CmdManager cmd = new CmdManager();
	Library lib = Library.getLibrary();
	
	@Test
	public void testCmdMku() {	
		cmd.sendCommand("mku Try Alexis Haldy test@gmail.com 0796562341".split(" "));
		cmd.sendCommand("mku Tryliom Alexis Haldy test@gmail.com 0796562341".split(" "));
		cmd.sendCommand("mku Lili Krine Loro llth@gmail.com 0796662341".split(" "));
		cmd.sendCommand("mku Lili Krine Loro email 0796662341".split(" "));
	}
	
	@Test
	public void testCmdMkb() {	
		cmd.sendCommand("mkb Title Author 2017 Description 5 Dupuis".split(" "));
		cmd.sendCommand("mkb Title Author 2017 Description 4 Dupuis".split(" "));
		for (int i=0;i<10;i++)
			cmd.sendCommand("mkb Title Author 2017 Description 3 Dupuis".split(" "));
		cmd.sendCommand("mkb The_Fire Francis 2015 The_fire_has_appear_in_the_world 1 Le_Miel_De_l'Ours".split(" "));
		cmd.sendCommand("mkb The_Fire François 2015 The_fire_has_appear_in_the_world 1 Le_Miel_De_l'Ours".split(" "));
		cmd.sendCommand("mkb The_Fire Francis 2015 The_fire_has_appear_in_the_world 2 Le_Plaisir_De_Lire".split(" "));
		cmd.sendCommand("mkb Powa Paul 2015 toto 1 Dupuis".split(" "));
		cmd.sendCommand("mkb Powaa Paul 2015 toto 2 Dupuis".split(" "));
		cmd.sendCommand("mkb Power Paul 2015 toto 1 Dupuis".split(" "));
		cmd.sendCommand("mkb Poowa Paul 2015 toto 1 Dupuis".split(" "));
		cmd.sendCommand("mkb Pouwa Paul 2015 toto 1 Dupuis".split(" "));
		cmd.sendCommand("mkb Piwa Paul 1998 Toutou 1 Dupuis".split(" "));
	}

	@Test
	public void testCmdRmu() {	
		cmd.sendCommand(("rmu id "+lib.getUserIdByUsername("Try")).split(" "));
		cmd.sendCommand(("rmu username Lili").split(" "));
	}
	
	@Test
	public void testCmdRmb() {	
		cmd.sendCommand(("rmb title Title").split(" "));
		cmd.sendCommand(("rmb author Author").split(" "));
		cmd.sendCommand(("rmb year 1998").split(" "));
		cmd.sendCommand(("rmb title_author The_Fire Francis").split(" "));
		cmd.sendCommand(("rmb title_author_numedition The_Fire Francis 2").split(" "));
		cmd.sendCommand(("rmb desc Toutou").split(" "));
	}
	
	@Test
	public void testCmdEditu() {	
		cmd.sendCommand("mku Lili Krine Loro llth@gmail.com 0796662341".split(" "));
		cmd.sendCommand(("eu id "+lib.getUserIdByUsername("Lili")+" ii@gmail.com 0796667231").split(" "));
		cmd.sendCommand(("eu id "+lib.getUserIdByUsername("Lili")+" iil@gmail.com").split(" "));
	}
	
	@Test
	public void testCmdTakeb() {	
		cmd.sendCommand("mku Tryliom Alexis Haldy test@gmail.com 0796562341".split(" "));
		cmd.sendCommand(("tb username Tryliom title Title").split(" "));
		cmd.sendCommand(("tb username Tryliom author Author").split(" "));
		cmd.sendCommand(("tb username Tryliom year 1998").split(" "));
		cmd.sendCommand(("tb username Tryliom title_author The_Fire Francis").split(" "));
		cmd.sendCommand(("tb username Tryliom title_author_numedition The_Fire Francis 2").split(" "));
		cmd.sendCommand(("tb username Tryliom desc Toutou").split(" "));

		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" title Title").split(" "));
		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" author Author").split(" "));
		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" year 1998").split(" "));
		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" title_author The_Fire Francis").split(" "));
		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" title_author_numedition The_Fire Francis 2").split(" "));
		cmd.sendCommand(("tb id "+lib.getUserIdByUsername("Tryliom")+" desc Toutou").split(" "));
	}
	
	@Test
	public void testCmdDisplay() {	
		cmd.sendCommand(("dpu").split(" "));
		cmd.sendCommand(("dpb").split(" "));
	}
	
	
}

















