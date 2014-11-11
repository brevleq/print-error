package org.lutum.print;

import javafx.application.Application;
import javafx.print.PrinterJob;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: hudson
 * Date: 11/11/14
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public class PrintError extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) throws PrintException {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        printerJob.showPrintDialog(stage);


        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(printerJob.getJobSettings().getCopies()));
        printRequestAttributeSet.add(new JobName("test", Locale.getDefault()));
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc mydoc = new SimpleDoc(ClassLoader.class.getResourceAsStream("/should-be-printed.txt"), flavor, null);
        DocPrintJob job = getPrintService(printerJob.getPrinter().getName()).createPrintJob();
        job.print(mydoc, printRequestAttributeSet);
    }

    private PrintService getPrintService(String name) {
        for (PrintService printService : java.awt.print.PrinterJob.lookupPrintServices()) {
            if (name.equalsIgnoreCase(printService.getName())) {
                return printService;
            }
        }
        return null;
    }
}
