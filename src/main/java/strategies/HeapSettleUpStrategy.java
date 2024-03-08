package strategies;

import dtos.Transaction;

import java.util.List;
import java.util.Map;

public class HeapSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUpGroup(Map<String, Integer> map) {
        return null;
    }
}
