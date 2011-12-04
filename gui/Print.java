/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 * @inspiration http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html
 * @author arturhebda
 */
public class Print implements Printable {
    private JComponent[] components;

    public Print(JComponent[] components) {
      this.components = components;
    }
  
    public void print() throws PrinterException {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        if (printJob.printDialog())
            printJob.print();
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 2)
            return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        disableDoubleBuffering(components[pageIndex]);
        components[pageIndex].paint(g2d);
        enableDoubleBuffering(components[pageIndex]);
        
        return PAGE_EXISTS;
  }

  public void disableDoubleBuffering(JComponent c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }

  public void enableDoubleBuffering(JComponent c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }
}