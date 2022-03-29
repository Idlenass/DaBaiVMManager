package Agent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAgent {

    private Date date = new Date();
    private String datestr = "";
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0;
    private int min = 0;
    private int sec = 0;

    public DateAgent() {
        this.date = new Date(System.currentTimeMillis());
        genStr();
        genDatas();
    }

    public DateAgent(Date date) {
        this.date = date;
        genStr();
        genDatas();
    }

    public DateAgent(String datestr) {
        this.datestr = datestr;
        genDatas();
    }

    public DateAgent(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void genStr(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datestr = dateFormat.format(date);
    }

    public String genDateStr(){
        return year + "-" + month + "-" + day +" " + hour + ":" + min + ":" + sec;
    }

    public void genDatas(){
        String[] str1 = datestr.split(" ");
        year = Integer.parseInt(str1[0].split("-")[0]);
        month = Integer.parseInt(str1[0].split("-")[1]);
        day = Integer.parseInt(str1[0].split("-")[2]);
        hour = Integer.parseInt(str1[1].split(":")[0]);
        min = Integer.parseInt(str1[1].split(":")[1]);
        sec = Integer.parseInt(str1[1].split(":")[2]);
    }

    public void pushHours(int hours){
        this.date = new Date(System.currentTimeMillis() + (hours * 3600 * 1000));
        genStr();
        genDatas();
    }

    public boolean compareDate(DateAgent dateAgent){
        //现在时间比传入参数的时间晚，则返回false
        if(this.year > dateAgent.getYear()) {
            return false;
        }else if(this.year < dateAgent.getYear()){
            return true;
        }else{
            if (this.month > dateAgent.getMonth()) {
                return false;
            }else if(this.month < dateAgent.getMonth()){
                return true;
            }else{
                if (this.day > dateAgent.getDay()) {
                    return false;
                }else if(this.day < dateAgent.getDay()){
                    return true;
                }else{
                    if (this.hour > dateAgent.getHour()) {
                        return false;
                    }else if(this.hour < dateAgent.getHour()){
                        return true;
                    }else{
                        if (this.min > dateAgent.getMin()) {
                            return false;
                        }else if(this.min < dateAgent.getMin()){
                            return true;
                        }else{
                            if (this.sec > dateAgent.getSec()) {
                                return false;
                            }else if(this.sec < dateAgent.getSec()){
                                return true;
                            }else{
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    public String getDatestr() {
        return datestr;
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }
}
