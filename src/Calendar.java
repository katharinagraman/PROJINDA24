import java.time.LocalTime;
import java.time.Month;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;


public class Calendar {

    LocalDate date;


    LocalDate today;

    Year year;
    Month month;

    List<LocalDate> daysOfMonth;


    Map<Integer, Integer> monthToDayLength;



    public Calendar() {
        this.month = LocalDate.now().getMonth();
        this.date = LocalDate.now();
        this.monthToDayLength = createMonthLength();
    }

    /*
    Kanske ha denna i planner ist
     */
    public Map<Integer, Integer> createMonthLength(){
        monthToDayLength.put(1, 31);
        monthToDayLength.put(3, 31);
        monthToDayLength.put(4, 30);
        monthToDayLength.put(5, 31);
        monthToDayLength.put(6, 30);
        monthToDayLength.put(7, 31);
        monthToDayLength.put(8, 31);
        monthToDayLength.put(9, 30);
        monthToDayLength.put(10, 31);
        monthToDayLength.put(11, 30);
        monthToDayLength.put(12, 31);
        if(year.isLeap()){
            monthToDayLength.put(2, 29);
        }else{
            monthToDayLength.put(2, 28);
        }

    }

    public int lengthOfMonth(int monthNum){


    }




    //private String getCommand(){
        //String command = scanner.nextLine();

    //}
    
}
