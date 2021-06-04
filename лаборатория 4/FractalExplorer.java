import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
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
		JFrame frame=new JFrame("Fractal Explorer");
		image=new JImageDisplay(displaySize, displaySize);
		JButton reset=new JButton("reset");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
            	fractal.getInitialRange(range);
            	drawFractal();
        	}
		});
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(image,BorderLayout.CENTER);
        frame.add(reset,BorderLayout.SOUTH);
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
    	FractalExplorer fractalexp=new FractalExplorer(1000);
    	fractalexp.createAndShowGUI();
    	fractalexp.drawFractal();


    }

}