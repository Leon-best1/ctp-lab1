import javax.swing.*;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
class JImageDisplay extends JComponent
{

	public BufferedImage image;
	public JImageDisplay(int width, int heigth)
	{
		image=new BufferedImage(width, heigth,BufferedImage.TYPE_INT_RGB);
		super.setPreferredSize(new Dimension(width, heigth));

	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}
	public void clearImage()// устанавливает все пиксели изображения в черный цвет 
    {
        for(int i = 0; i < image.getHeight(); i++)
        {
            for(int j = 0; i < image.getWidth(); i++)
            {
                image.setRGB(j,i,0);
            }
        }
    }
    public void drawPixel(int x, int y, int rgbColor)
    {
        image.setRGB(x, y, rgbColor);
    }
    public BufferedImage getBufferedImage()
    {
        return image;
    }

}