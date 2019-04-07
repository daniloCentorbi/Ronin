package ita.homi.utils;

import java.awt.*;
import java.awt.print.*;
class pagePainter implements Printable
  {
    public int print(Graphics g, PageFormat pf,int pageIndex)
    // A demo method implementation: does not do any printing
           {
             if (pageIndex>1)return NO_SUCH_PAGE;
          g.drawString("Printed",100,100);
            /*put something on the paper*/
            return Printable.PAGE_EXISTS;
           }
  }

public class PagePainter
  {
      public static void main(String args[])
    {
      pagePainter p = new pagePainter();
      //An object of the pagePainter class is created
      PrinterJob pj = PrinterJob.getPrinterJob();
      //An object of the PrinterJob class is created using
      //the getPrinterJob() method
       PageFormat pf = pj.defaultPage();
       /* the defaultPage() method is used to get the object of the PageFormat */
       pj.pageDialog(pf);
       pf.setOrientation(PageFormat.LANDSCAPE);
       /*the pageDialog() method is called to show the Page Setup dialog box*/
       pj.setPrintable(p);
       /*The use of the setPrintable() method. An object of the Printable class is passed   as the parameter*/
       pj.setJobName("Demonstrating methods ");
       System.out.println("Parameter of the Imageable Area of the Default Page");
       System.out.println("X (Left): " +pf.getImageableX() +"  Y (Top): "   +pf.getImageableX());
       System.out.println("Width: " +pf.getHeight() +"Height: " +pf.getWidth());
       pj.pageDialog(pf); 
       /*if the user accepts printing*/
        if(pj.printDialog())
               {
               try
                      {
                  System.out.println("Printing: " + pj.getJobName() + 
                  /* get the name of the printing job; Java Printing is the default name*/
                          " initiated by "+ pj.getUserName());
                        /* get the name of the user who initiated the printing job*/
               pj.print();// call the print method
              }
                   catch (PrinterException pe) { System.out.println(pe); }
               }
      }
  }