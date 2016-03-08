/**
 * @author Marek Pierscieniak
 */
import java.awt.Color;
import java.awt.Desktop;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ResultTab extends JPanel{								// ResultTab differ from Tab class

	private BufferedImage resultImage;
	private JFileChooser fc;
	private JButton button;
	private JPanel chooser;
	private JLabel picLabel;
	
	public ResultTab(){
		picLabel = new  JLabel();
		fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);							// we need to store a path where to save result image
        
        JLabel label = new JLabel("Choose where to save");
        this.add(label);
        
        button = new JButton("Choose location");
        
        JTextField pathField = new JTextField(20);
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(Main.isImage1Choosen==true && Main.isImage2Choosen==true){			// if both images are chose then compare and try to save, else show message to choose both of them
					
					Frame.tab1.image = Frame.tab1.resizeImage(Frame.tab1.image);		// resize image 1 to specified dimensions
					Frame.tab2.image = Frame.tab2.resizeImage(Frame.tab2.image);		// resize image 2 to specified dimensions
					
					int result = fc.showOpenDialog(chooser);							
	                if (result==JFileChooser.APPROVE_OPTION) {
	                    pathField.setText(fc.getSelectedFile().toString());
	                }
	            
		            File outputFile = new File(pathField.getText()+"/result.png");			// Output file named "result.png" as introduced in task
	
		            resultImage = createResultImage(Frame.tab1.image, Frame.tab2.image);	// creating resultImage with proper dimensions
		            
		            Image tempImage = resultImage.getScaledInstance(100, 100, resultImage.SCALE_DEFAULT);
					
	                picLabel.setIcon(new ImageIcon(tempImage));								// creates and sets solution preview image
		            
		            try {
		    			ImageIO.write(resultImage, "png", outputFile);						// if saving result image is completed, then open image in default viewer
		    			Desktop.getDesktop().open(outputFile);
		    		} catch (IOException e1) {
		    			e1.printStackTrace();
		    			JOptionPane.showMessageDialog(null, "Error while saving an image!");
		    		}    
	                
				} else {
					JOptionPane.showMessageDialog(null, "Choose TWO images first!");
				}
			}
		});
        
        add(button);
        add(pathField);
        add(picLabel);
	}
	
	BufferedImage createResultImage(BufferedImage a, BufferedImage b){								// main logic of project - returns newly created BufferedImage with grayscaled pixels
		BufferedImage resultImage = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
		int result = 0;
		
		for(int x=0; x<Main.WIDTH; x++){
			for(int y=0; y<Main.HEIGHT; y++){
					result = Math.abs(getIntegerFromRGB(new Color(a.getRGB(x, y))) - getIntegerFromRGB(new Color(b.getRGB(x, y)))) / 65536;		// difference between corresponding pixels in both images is scaled into grayscale
					result = Math.abs(result - 255);																							// reverse the gray scale order (0 -> 255) from White -> Black to Black -> White
					resultImage.setRGB(x, y, new Color(result, result, result).getRGB());
			}
		}
		
		Frame.tab1.button.setEnabled(false);
		Frame.tab2.button.setEnabled(false);
		Frame.tab1.revalidate();
		Frame.tab2.revalidate();
		
		return resultImage;
	}
	
	int getIntegerFromRGB(Color color){ 				// getRed(), getGreen(), getBlue() can have up to 256 separate values, to obtain accurate result this particular number will be used as multiplier
		return color.getRed()+(color.getGreen()*256)+(color.getBlue()*256*256);
	}

}
