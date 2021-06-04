import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
public class FractalExplorer
{
	private int displaySize;
	private JImageDisplay image;
	private FractalGenerator fractal;
	private Rectangle2D.Double range;
	public FractalExplorer(int displaySize)
	{
		this.displaySize=displaySize;
		this.fractal = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        fractal.getInitialRange(this.range);
	}
	public void createAndShowGUI()
	{
		Mandelbrot mandelbrot=new Mandelbrot();
		Tricorn tricorn=new Tricorn();
		BurningShip burningShip=new BurningShip();

		JFrame frame=new JFrame("Fractal Explorer");

		JPanel northpanel=new JPanel();
		JPanel southpanel=new JPanel();

		JLabel label=new JLabel("Fractal");

		//String[] fractals={mandelbrot, tricorn, burningShip};
		JComboBox comboBox = new JComboBox();
		comboBox.addItem(mandelbrot);
		comboBox.addItem(tricorn);
		comboBox.addItem(burningShip);

		image=new JImageDisplay(displaySize, displaySize);

		JButton reset=new JButton("reset");
		JButton save=new JButton("save");

		//обработчик комбобокса 
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JComboBox am=(JComboBox) e.getSource();
				fractal=(FractalGenerator) am.getSelectedItem();
			}
		});
		//

		//обработчик кнопки reset
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
            	fractal.getInitialRange(range);
            	drawFractal();
        	}
		});
		//

		//обработчик кнопки save
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				JButton button=(JButton) e.getSource();
            	JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                if(fileChooser.showSaveDialog(button.getParent())== JFileChooser.APPROVE_OPTION)
                {
	                try 
	                {
	                    ImageIO.write(image.getBufferedImage(),"png", fileChooser.getSelectedFile());
	                } 
	                catch (IOException ex) 
	                {
	                    JOptionPane.showMessageDialog(button.getParent(), ex.getMessage(),
	                            "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
	                }
            	}
        	}
		});
		//

		//рисование фрактала
		image.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
        	{
	            int x = e.getX();
	            int y = e.getY();
	            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
	            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
	            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
	            drawFractal();
        	}
		});
		northpanel.add(label);
		northpanel.add(comboBox);
		southpanel.add(save);
		southpanel.add(reset);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(image,BorderLayout.CENTER);
        
        frame.add(southpanel, BorderLayout.SOUTH);
        frame.add(northpanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
	}

	private void drawFractal(){
        for(int i = 0; i < displaySize; i++){
            for(int j = 0; j < displaySize; j++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, displaySize, i);
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, displaySize, j);
                int iter = fractal.numIterations(xCoord,yCoord);
                if (iter == -1)image.drawPixel(i,j,0);
                else {
                    float hue = 0.7f + (float)iter / 200f;
                    image.drawPixel(i,j,Color.HSBtoRGB(hue,1f,1f));
                }
            }
        }
        image.repaint();
    }

    
    public static void main(String[] args) 
    {
    	FractalExplorer fractalexp=new FractalExplorer(500);
    	fractalexp.createAndShowGUI();
    	fractalexp.drawFractal();


    }

}