package com.company.mulitchoice.networkServices;

import com.company.mulitchoice.billersList.BillersResponseModel;
import com.company.mulitchoice.transactionDetails.Result;

public interface EventsAdapterInterface {
    void handleBillersClickedItems(Result myResult);
}
