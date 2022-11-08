package AutoCenter.mechanic;

/**
 * Represents the state of a swap request.
 * It is used to store the state of a swap request in the
 * {@link RequestSwap} and {@link AcceptRejectSwap} and
 * {@link ManageSwapRequests} classes.
 */
public enum SwapRequestState {
    PENDING, ACCEPTED, REJECTED
}
