package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * @author Lunga Tsewu
 * @date 19 April 2023
 */
public class NumberRangeSummarizerImpl implements  NumberRangeSummarizer {
    /**
     * Make collection of integers from input string
     * without duplicates and sorted in ascending order
     * @param input  is the string of list of number separated by comma
     * @Returns   Collection of integers
     * @throws   NullPointerException  if the string input is null or empty
     * @throws   RuntimeException  if the none integer value in the input string except comma
     */
    @Override
    public Collection<Integer> collect(String input) {
        isValidInputString(input);

        Collection<Integer> inputNumbers = new ArrayList<>();
        var tokenizer = new StringTokenizer(input, ",");
        while (tokenizer.hasMoreTokens()) {

            inputNumbers.add(Integer.parseInt(tokenizer.nextToken()));
        }
        return  sort(removeDuplicates(inputNumbers));

    }

    /**
     * Removes duplicates  from collection of integers
     * @param collection collection of integer numbers
     * @return collection of distinct  integers
     */
    private Collection<Integer> removeDuplicates(Collection<Integer> collection){
        return collection.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Removes sort collection of integers
     * @param collection collection of integer numbers
     * @return collection of sorted  integers
     */
    private Collection<Integer> sort(Collection<Integer> collection){
        return collection.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Check if the string is valid
     * by checking if input is not null, not empty ,not blank and does not contain non integer values
     * @param input  is the string suppose to contain numbers separated by comma
     */
    private void isValidInputString(String input){
        if(input == null ||input.trim().isEmpty() || input.trim().isBlank()) throw new RuntimeException("Null or empty or blank can not be collected and summarized") ;
        if(!input.replace(",","").trim().matches("[0-9]+")) throw new RuntimeException("Non integer  value can not be collected and summarized." +
                " Only comma  and integer must be used in the input string ") ;
    }

    /**
     * Group the numbers into a range when they are sequential
     * @param input  sorted collection of distinct integers
     * @return string of Group the numbers into a range
     * @throws   NullPointerException if the collection is null or empty
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if(input == null || input.size()==0) throw new NullPointerException("Null or empty collection can not be summarized") ;

        if (input.size()==1){
            return Integer.toString((Integer)input.toArray()[0]);
        }

        ArrayList<Integer> list = (ArrayList<Integer>)input;
        StringBuilder answer = new StringBuilder();

        int  start = 0, end = 0;
        boolean isNumberVisited = false;
        int secondLast = list.get(list.size()-2);

        for(int i =1; i<list.size(); i++){
            int current = list.get(i-1);
            int next = list.get(i);
            if(next==current+1){
                if(!isNumberVisited){
                    start = current;
                }
                end = next;
                isNumberVisited = true;
            }
            else{
                if(isNumberVisited){
                    answer.append(start).append("-").append(end).append(",");
                    isNumberVisited = false;
                }
                else{
                    answer.append(current).append(",");
                }
            }
        }

        int last =list.get(list.size()-1);
        if(secondLast+1== last){
            end = list.get(list.size()-1);
            answer.append(start).append("-").append(end);
        }
        else{
            answer.append(list.get(list.size()-1));
        }
        return answer.toString();
    }
}
