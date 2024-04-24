import java.time.LocalTime;
import java.time.Month;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Calendar {


    LocalDate date;


    LocalDate today;

    Year year;
    Month month;

    List<LocalDate> daysOfMonth;
    private Map <Year, Month> yearMonthMap;

    private ArrayList<Integer> year = new ArrayList<>();


    private Map<Integer, Integer> monthToDayLength;

    private int[][] monthMatrice = new int[7][4];



    public Calendar() {
        createMonthLength();
        //this.year = LocalDate.now().getYear();
        this.month = LocalDate.now().getMonth();
        this.date = LocalDate.now();

    }

    public void createCalendar(){



    }

    public void createMontlyCalendar(int num){

    }

    /*
    Kanske ha denna i planner ist
     */
    public void createMonthLength(){
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
        return monthToDayLength.get(monthNum);
    }


    public void print(){
        System.out.println(this.month);
        for(int row = 0; row < monthMatrice.length; row++){
            for (int col = 0; col < monthMatrice[row].length; col++){
                System.out.print(monthMatrice[row][col]);
            }
        }



    }



    //private String getCommand(){
        //String command = scanner.nextLine();

    //}
    
}
