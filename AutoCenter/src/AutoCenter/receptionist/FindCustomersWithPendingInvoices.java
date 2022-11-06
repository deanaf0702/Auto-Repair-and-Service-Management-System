package AutoCenter.receptionist;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.services.RepositoryService;

public class FindCustomersWithPendingInvoices implements UserFlowFunctionality{
	private RepositoryService repoService = null;
	private Integer[] ABCPriceTier;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 3;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
	@Override
	public void run() {
		display();
		

	}

	@Override
	public void display() {
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("# A. Customer ID  # ");
		System.out.println("# B. Customer Name  #");
		System.out.println("# C. Invoice ID  #");
		System.out.println("# C. Invoice ID  #");
		System.out.println("# C. Invoice Date  #");
		System.out.println("# C. Amount  #");
		System.out.println(DIRECTION_SEPARATOR);
	}

	@Override
	public void navigate(int selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub

	}

}
