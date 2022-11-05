package AutoCenter;

public interface UserFlowFunctionality {
    /**
     * Handles user input and and user-flow logic.
     */
    void run();

    /**
     * Displays menu options to the user.
     */
    void display();

    /**
     * Handles navigation based on user input.
     *
     * @param selection
     *                  the user's selection
     */
    void navigate(int selection);

    /**
     * Handles navigation back to the previous menu.
     */
    void goBack();
}
