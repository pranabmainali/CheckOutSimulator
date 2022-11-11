package SelfCheckOut_Software.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.*;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.*;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GuiFacade {

	private final SelfCheckoutStation scs;
	private final SelfCheckoutStationLogic scsl;
	private final JFrame frame;
	private Dialog dialog;

	public final BeginPanel beginPanel;
	public final OwnBagsPanel ownBagsPanel;
	public final BlockedPanel blockedPanel;
	public final EnterMemberInfoPanel enterMemberPanel;
	public final ScanItemsPanel scanItemsPanel;
	public final PlaceItemPanel placeItemPanel;
	public final NumBagsUsedPanel numBagsPanel;
	public final PayMethodPanel payMethodPanel;
	public final CashPayPanel cashPayPanel;
	public final CardPayPanel cardPayPanel;
	public final ThankYouPanel thankYouPanel;
	public final PLUPanel pluPanel;
	public final PLUVisCat pluVisCatPanel;
	public final PLUVisCatAa pluVisCatPanelA;
	public final PLUVisCatBb pluVisCatPanelB;
	public final PLUVisCatNotFound pluVisCatPanelNone;

	public String[] productlist;
	public String[] pricelist;


	public GuiFacade(SelfCheckoutStation s, SelfCheckoutStationLogic l) {
		this.scs = s;
		this.scsl = l;

		var ts = scs.screen;
		frame = ts.getFrame();

		beginPanel = new BeginPanel(scsl);
		ownBagsPanel = new OwnBagsPanel(scsl);
		blockedPanel = new BlockedPanel();
		enterMemberPanel = new EnterMemberInfoPanel(scsl);
		scanItemsPanel = new ScanItemsPanel(scsl);
		placeItemPanel = new PlaceItemPanel();
		numBagsPanel = new NumBagsUsedPanel(scsl);
		payMethodPanel = new PayMethodPanel(scsl);
		cashPayPanel = new CashPayPanel(scsl);
		cardPayPanel = new CardPayPanel(scsl);
		thankYouPanel = new ThankYouPanel(scsl);
		pluPanel = new PLUPanel(scsl);
		pluVisCatPanel = new PLUVisCat(scsl);
		pluVisCatPanelA = new PLUVisCatAa(scsl);
		pluVisCatPanelB = new PLUVisCatBb(scsl);
		pluVisCatPanelNone = new PLUVisCatNotFound(scsl);

		// todo set to beginpanel... only changed for testing
		frame.add(beginPanel);
		ts.setVisible(true);
		frame.setSize(900, 600);
	}

	public void changePhase(minorPhase newPhase) {
		switch (newPhase) {
			case MAINTENANCE -> {
				frame.getContentPane().setVisible(false);
			}
			case WAIT -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(beginPanel);
				frame.getContentPane().setVisible(true);
			}
			case SCAN_MEMBER -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(enterMemberPanel);
				frame.getContentPane().setVisible(true);
			}
			case OWN_BAGS -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(ownBagsPanel);
				frame.getContentPane().setVisible(true);
			}
			case BLOCK -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(blockedPanel);
				frame.getContentPane().setVisible(true);
			}
			case SCAN_ITEM -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(scanItemsPanel);
				frame.getContentPane().setVisible(true);
				scsl.gf.buildGUIList();
			}
			case ENTER_PLU -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(pluPanel);
				frame.getContentPane().setVisible(true);
			}
			case PLU_VISUAL -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(pluVisCatPanel);
				frame.getContentPane().setVisible(true);
			}
			case PLU_VISUALA -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(pluVisCatPanelA);
				frame.getContentPane().setVisible(true);
			}
			case PLU_VISUALB -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(pluVisCatPanelB);
				frame.getContentPane().setVisible(true);
			}
			case PLU_VISUALNONE -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(pluVisCatPanelNone);
				frame.getContentPane().setVisible(true);
			}
			case PLACE_ITEM -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(placeItemPanel);
				frame.getContentPane().setVisible(true);
			}
			case USE_BAGS -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(numBagsPanel);
				frame.getContentPane().setVisible(true);
				numBagsPanel.updateFields();
			}
			// payment method panel
			case PAY_SELECT -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(payMethodPanel);
				frame.getContentPane().setVisible(true);
			}
			case PAY_CASH -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(cashPayPanel);
				frame.getContentPane().setVisible(true);
			}
			case PAY_CREDIT, PAY_DEBIT, PAY_GIFT -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(cardPayPanel);
				frame.getContentPane().setVisible(true);
			}
			case RETURN_CHANGE -> {
				frame.getContentPane().setVisible(false);
				frame.setContentPane(thankYouPanel);
				frame.getContentPane().setVisible(true);
			}
			default -> {
				frame.getContentPane().setVisible(false);
			}
		}
	}

	//Acts as an notifier, so that it updates the list that will be used in scanItems GUI when an item is scanned
	//List<AbstractMap.SimpleEntry<Product, AbstractMap.SimpleEntry<Boolean, Double>>> shoppingCart
	public void buildGUIList(){
		var shoppingCart = scsl.aif.getCart();

		productlist = new String[shoppingCart.size()];
		pricelist = new String[shoppingCart.size()];

		for (int i = 0; i < shoppingCart.size(); i++){

			Product curproduct = scsl.aif.get1st(shoppingCart.get(i));

			productlist[i] = getDescription(curproduct);
			pricelist[i] = getPrice(curproduct,scsl.aif.get3rd(shoppingCart.get(i)));
		}

		scanItemsPanel.setItemsList(productlist);
		scanItemsPanel.setPriceList(pricelist);
	}

	public String[] getProductlist(){
		return productlist;
	}
	public String[] getPricelist(){
		return pricelist;
	}

	private String getDescription(Product prod) {
		if (prod instanceof PLUCodedProduct) {
			return ((PLUCodedProduct) prod).getDescription();
		} else {
			return ((BarcodedProduct) prod).getDescription();
		}
	}
	private String getPrice(Product prod, Double weight) {
		if (prod instanceof PLUCodedProduct) {
			return prod.getPrice().multiply(new BigDecimal(weight)).setScale(2, RoundingMode.HALF_UP).toPlainString();
		} else {
			return prod.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString();
		}
	}

	public void updatePaid(BigDecimal totalPaid) {
		cardPayPanel.setAmountPaid(totalPaid);
		cashPayPanel.setAmountPaid(totalPaid);
	}

	public void updateDue(BigDecimal totalDue) {
		cardPayPanel.setAmountDue(totalDue);
		cashPayPanel.setAmountDue(totalDue);
	}

	public void informCustomer(String message) {
		if (this.dialog != null) dialog.dispose();
		dialog = new Dialog();
		dialog.setTitle("Attention, valued customer!");
		dialog.setMessage(message);
		dialog.setSize(400, 300);
		dialog.setVisible(true);
	}

	public void killGui() {
		frame.dispose();
		if (this.dialog != null) dialog.dispose();
	}
}
