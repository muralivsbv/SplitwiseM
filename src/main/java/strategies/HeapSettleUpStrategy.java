package strategies;

import dtos.Transaction;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HeapSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUpGroup(Map<String, Integer> map) {
        // take couple of heaps of pair type which carries the username and also the amount that he owes/receives

        PriorityQueue<Pair>  receivers = new PriorityQueue<Pair>();
        PriorityQueue<Pair>   givers = new PriorityQueue<Pair>();

        // now we need to segregate the map of extra amounts passed , according to the giver and receivers based on
        // Integer is -ve ( giver) or +ve (receivers)
        // implement a compareTo method as well for sorting the Heaps of pair type

        for(String user_name : map.keySet()){
            Integer user_amount = map.get(user_name);
            if(user_amount > 0){
                 receivers.add(new Pair(user_name,user_amount));
            }else{
                givers.add(new Pair(user_name,-1*user_amount));
            }
        }

        // both heaps are ready with data now we need to build transactions from the heaps by popping the heaps
        // continue untill the heaps are empty
        while(receivers.size() > 0 && givers.size() > 0){
            Pair receiver = receivers.remove();
            Pair giver = givers.remove();

            if(receiver.amount > giver.amount){
                receivers.add(new Pair(receiver.name,receiver.amount-giver.amount));
            }
        }


        
    }

    private class Pair implements Comparable{

        String name;
        int amount;

        public Pair(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        @Override
        public int compareTo(Object o) {
            Pair other = (Pair) o;
            if(this.amount <= other.amount){
                return -1;
            }
            return 1;
        }
    }
}
