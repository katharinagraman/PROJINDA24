public class DailyEvent {
    /**
     * Class keeping track of daily events
     * Each event has an identifier important for other feautures
     */

    private Identifier type;
    private String titleOfEvent
    private int month, day, year;


    /**
     * User will input a title of their event
     * They will pick a date
     * They have to pick a start and end time
     * @param titleOfEvent
     * @param date
     * @param startTime
     * @param endTime
     */
    public DailyEvent(String titleOfEvent, String date, String startTime, String endTime) {
        this.titleOfEvent = titleOfEvent;
        this.dateEvent = date;

        String[] d = date.split("/");
        this.month = Integer.parseInt(d[0]);
        System.out.println(d[1]);
        this.day = Integer.parseInt(d[1]);
        this.year = Integer.parseInt(d[2]);

        this.start = startTime;
        this.end = endTime;
        this.monthName = mnames[month - 1];
        this.dayOfWeek = daysOfTheWeek[this.getDayOfTheWeekValue()];
    }
}
