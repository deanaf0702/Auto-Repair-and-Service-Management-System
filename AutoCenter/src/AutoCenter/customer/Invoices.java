package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

public class Invoices implements Interface {

    @Override
    public void run() {
        int selection = 0;
        display();
        do {
            System.out.print("Enter choice (1-3) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= 1 && selection <= 3));
        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("###################################");
        System.out.println("##### Customer: Invoices Menu #####");
        System.out.println("###################################");
        System.out.println("# 1 View Invoice details          #");
        System.out.println("# 2 Pay invoice                   #");
        System.out.println("# 3 Go Back                       #");
        System.out.println("###################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ViewInvoiceDetails().run();
                break;
            case 2:
                new PayInvoice().run();
                break;
            case 3:
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        new Invoices().run();
    }
}
