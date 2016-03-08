/**
 * @author Marek Pierscieniak
 */
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Frame extends JFrame{							// "main window" class of application

	public JTabbedPane mainPane;
	static Tab tab1;
	static Tab tab2;
	private ResultTab tabResult;
	
	public Frame(){	
		super("Differences Finder");
		setSize(new Dimension(300,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false); 								// resizing of window is not needed (is set to "true" may argue with Tab's layout)
		
		mainPane = new JTabbedPane();
		
		tab1 = new Tab(1);									// Tab's constructor require ID as parameter (in order to distinguish images) 
		tab2 = new Tab(2);									// Tab's constructor require ID as parameter (in order to distinguish images) 
		
		tabResult = new ResultTab();
		mainPane.addTab("Image 1", tab1);
		mainPane.addTab("Image 2", tab2);
		mainPane.addTab("Result image", tabResult);	
		
		add(mainPane);
		setVisible(true);
	}
	
}
