/**
 * @author Marek Pierscieniak
 */
import java.awt.AlphaComposite;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Tab extends JPanel{							// class which allows create separete Tab for choosing input image
	
	public JButton button;
	public BufferedImage image;
	private JLabel picLabel;
	private File file;
	private JFileChooser fc;
	private JLabel label;
	private JPanel chooser;
	
	public Tab(int imageNumber){							// "imageNumber" argument indicates with which exactly Tab we are dealing (acts like ID)
		setLayout(new FlowLayout());
		picLabel= new JLabel();
		fc = new JFileChooser();
		String[] imageType = {"png"};																// specify which files you want to load/read (in problem PNGs only)
        FileNameExtensionFilter fnf = new FileNameExtensionFilter("PNG", imageType);
        fc.setFileFilter(fnf);
        
        label = new JLabel("Choose image number: "+imageNumber);
        this.add(label);
        
        button = new JButton("Browse");
        this.add(button);
        
        this.add(picLabel);
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fc.showOpenDialog(chooser);
                if (result==JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    image = null;
					try {
						image = ImageIO.read(new File(file.getAbsolutePath()));						// load/read image from selected file
						
						if(imageNumber==1)															// set an info which image are we dealing with
							Main.isImage1Choosen=true;												// both needed to be set to "true" in order to compare
						else if(imageNumber==2)
							Main.isImage2Choosen=true;
						
						checkDimensions(image);														// check which dimensions are larger
						
						Image tempImage = image.getScaledInstance(100, 100, image.SCALE_DEFAULT); 	// creates an icon as preview
						
	                    picLabel.setIcon(new ImageIcon(tempImage));									// sets created icon
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
                }
			}
		});
	}
	
	BufferedImage resizeImage(BufferedImage image){				// Returns a BufferedImage resized to wanted dimensions (in simple way)
		
        BufferedImage scaledBI = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = scaledBI.createGraphics();
    	
    	g.setComposite(AlphaComposite.Src);
    	
    	g.drawImage(image, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    	
		return scaledBI;
	}
	
	void checkDimensions(BufferedImage image){					// The Goal is to find and store (int global variables) the greatest pair of dimensions from given Images
		if(image.getWidth()>Main.WIDTH){						
			Main.WIDTH = image.getWidth();
			Main.HEIGHT = image.getHeight();
		}
	}
}
