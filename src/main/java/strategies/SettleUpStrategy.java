package strategies;

import dtos.Transaction;

import java.util.List;
import java.util.Map;

public interface SettleUpStrategy {
    List<Transaction> settleUpGroup(Map<String,Integer> map);
}
