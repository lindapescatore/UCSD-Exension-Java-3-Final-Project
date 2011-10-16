package Java3Assignment3;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class InventorySystemApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException e) {
					System.out.println("Not found.");
					e.printStackTrace();
				} catch (InstantiationException e) {
					System.out.println("instantiation exception.");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.out.println("illegal access.");
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) { 
					System.out.println("Unsupported.");
					e.printStackTrace();
				}

				SKUGenerator skuGenerator = new SKUGenerator();
				InventorySystemModelJDBC model = new InventorySystemModelJDBC(skuGenerator);

				@SuppressWarnings("unused")
				InventorySystemViewGUI view = new InventorySystemViewGUI(model);				
			}
		}
		);
	}
}


