package ca.ucalgary.seng300.selfcheckout.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.*;
import javax.swing.*;

public class AbstractSCSGuiPanel extends JPanel {
	private static final long serialVersionUID = -1626340706871667753L;
	protected final SelfCheckoutStationLogic scsl;

	public AbstractSCSGuiPanel(SelfCheckoutStationLogic scsl) {
		this.scsl = scsl;
	}
}