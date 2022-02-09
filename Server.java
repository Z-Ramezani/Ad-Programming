package com.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Date {
    private int month, day, year;
    private static final int[] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Date(int theMonth, int theDay, int theYear) {
        month = checkMonth(theMonth);
        day = checkDay(theDay);
        year = checkYear(theYear);
    }

    public Date() { }

    public void setMonth(int month) { this.month = checkMonth(month); }

    public void setYear(int year) { this.year = year; }

    public void setDay(int day) { this.day = checkDay(day); }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public int getDay() { return day; }

    private int checkMonth(int testMonth) {
        if (testMonth > 0 && testMonth <= 12) {
            return testMonth;
        } else {
            throw new IllegalArgumentException("month must be 1_12");
        }
    }

    private int checkYear(int testYear) {
        if (year >= 2020) {
            return testYear;
        } else {
            throw new IllegalArgumentException("Year must be more than 2020");
        }
    }

    private int checkDay(int testDay) {
        if (testDay > 0 && testDay <= daysPerMonth[month]) { return testDay; }
        if (month == 2 && testDay == 29 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
            return testDay;
        }
        throw new IllegalArgumentException("day isn't in range");
    }

    public String toString() { return String.format("%d/%d/%d", month, day, year); }
}





public class Server {
    public static void main(String[] args) throws IOException {
        Mannager loginManager = new Mannager();
        Thread tread = new Thread(loginManager);
        ExecutorService e= Executors.newCachedThreadPool();
        e.execute(loginManager);
        int dobreak = 0;
        Scanner s = new Scanner(System.in);
        Mannager M = new Mannager();
        System.out.println("Wellcome");
        while (true){
            System.out.println("Enter yor Username:");
            String user = s.next();
            System.out.println("Enter Your Password:");
            String pass = s.next();
            if (user.equals(M.User) && pass.equals(M.Pass)){
                while (true){
                    M.list();
                    int choose = s.nextInt();
                    if (choose == 1) {//انتخاب زیر دست
                        for (int i = 0; i < M.McntFtE; i++) {
                            System.out.println(i + ":" + M.fulltimeemployee[i].username);
                        }
                        for (int i = M.McntFtE; i < M.McntFtE + M.McntPtE; i++) {
                            System.out.println(i + ":" + M.parttimeemployee[i - M.McntFtE].username);
                        }
                        int t = s.nextInt();
                        while ((t < M.McntFtE && M.fulltimeemployee[t].ServitorOrNo != 0) ||(t > M.McntFtE && M.parttimeemployee[ t - M.McntFtE ].ServitorOrNo != 0)) t = s.nextInt() ;
                        if (t < M.McntFtE) { M.ChooseSevitor(M.fulltimeemployee[t].username); }
                        else { M.ChooseSevitor(M.parttimeemployee[t - M.McntFtE].username); }
                        M.Write();
                    }

                    if (choose == 2){//انتخاب زیردست برای کارمندان
                        System.out.println("Choose Full_Time_Employee That You want to choose Servitor for him: ");
                        for (int i = 0; i < M.McntFtE; i++) {
                            System.out.println(i + ":" + M.fulltimeemployee[i].username);
                        }
                        int t1 = s.nextInt();

                        System.out.println("Choose Servitor: ");
                        for (int i = 0; i < M.McntFtE; i++) {
                            if (i != t1)System.out.println(i + ":" + M.fulltimeemployee[i].username);
                        }
                        for (int i = M.McntFtE; i < M.McntFtE + M.McntPtE; i++) {
                            System.out.println(i + ":" + M.parttimeemployee[i - M.McntFtE].username);
                        }
                        int t = s.nextInt();
                        while ((t < M.McntFtE && M.fulltimeemployee[t].ServitorOrNo != 0) ||(t > M.McntFtE && M.parttimeemployee[ t - M.McntFtE ].ServitorOrNo != 0)) t = s.nextInt() ;
                        if (t < M.McntFtE) {
                            M.ChooseServitorForFulltime_Employees(M.fulltimeemployee[t1].username,M.fulltimeemployee[t].username);
                            M.fulltimeemployee[t1].fteServitors(M.fulltimeemployee[t].username);
                        }
                        else {
                            M.ChooseServitorForFulltime_Employees(M.fulltimeemployee[t1].username,M.parttimeemployee[t - M.McntFtE].username);
                            M.fulltimeemployee[t1].fteServitors(M.parttimeemployee[t-M.McntFtE].username);
                        }
                        M.Write();
                    }

                    if (choose == 3) {//تعریف تسک برای زیر دستان
                        Date date = new Date();
                        System.out.println("* Deadline: ");
                        System.out.print("\t  Year: ");
                        int year = s.nextInt();
                        date.setYear(year);
                        System.out.println();
                        System.out.print("\t  Month: ");
                        int month = s.nextInt();
                        date.setMonth(month);
                        System.out.print("\t  Day: ");
                        int day = s.nextInt();
                        date.setDay(day);
                        System.out.print(" *Explanation: ");
                        String explanation = s.next();
                        System.out.println();
                        for (int i = 0; i < M.NumsOfServitors; i++) {
                            System.out.println(M.ServitorsUsernames[i]);
                        }
                        System.out.print("Enter Servitor's Username Of This Task: ");
                        String str = s.next();
                        M.IntroduceTask(date, explanation, str);
                    }

                    if (choose == 4) {//مشاهده ی تسک های تعریف شده برای زیردستان این رییس
                        for (int i = 0; i < M.NumsOfServitors; i++) {
                            System.out.println( M.ServitorsUsernames[i]);
                        }

                        System.out.print("Which employee? ");
                        String n = s.next();
                        System.out.print("Which Task? ");
                        int m = s.nextInt();
                        M.SeeServitorsTasks(n, m);
                    }
                    if (choose == 5){ M.SeeServitorsTasksAndScores(); }//دیدن تسک های تعریف شده برای همه ی کارمندان

                    //if (choose == 6){ M.SeeEmployee(); }

                    if (choose == 6){ M.Write(); }
                    if (choose == 7){
                        try {
                            M.loginAndlogout();
                        }catch (Exception e1){
                            System.out.println("No one has logged in yet!");
                        }
                    }
                    if (choose == 8) { break; }//خارج شدن از ضفحه ی شخصی منیجر اصلی

                    if (choose == 9){//خارج شدن از کل سابت
                        dobreak = 1 ;
                        break ;
                    }
                    //break ;
                }
            }
            else System.out.println("The Username or Password is not true!");
            if (dobreak == 1){
                dobreak = 0 ;
                System.out.println("Site Exit");
                break ;
            }
        }
    }
}

class BaseEmployee {

    int cntFtE = 0 , cntPtE = 0 , Score ;
    FulltimeEmployee fte[] = new FulltimeEmployee[100];
    ParttimeEmployee pte[] = new ParttimeEmployee[100];

    public void print(int a, String st) {
        System.out.println(a + " " + st);
    }
    public void SeeTasks() { }

}


class Mannager extends BaseEmployee implements Runnable {

    String User = "M" , Pass = "MM";

    static int McntFtE , McntPtE , n = 0;
    static FulltimeEmployee fulltimeemployee[] = new FulltimeEmployee[100];
    static ParttimeEmployee parttimeemployee[] = new ParttimeEmployee[100];

    int NumsOfServitors = 0 , NSFTE = 0 , NSPTE = 0;
    String ServitorsUsernames[] = new String[3];
    FulltimeEmployee FTEServitors[] = new FulltimeEmployee[3];
    ParttimeEmployee PTEServitors[] = new ParttimeEmployee[3];

    private String TasksDeadline;
    private String TasksExplain;
    int NumsOfTasks[] = new int[3];//تعداد تسک های تعریف شده برای هر زیردست[i]
    IntroduceTask introducetask[][] = new IntroduceTask[3][3];
    GetReporting getreporting[][] = new GetReporting[3][3];
    String ftesUsers[] = new String[3] ;
    //String ptesUsers[] = new String[3] ;
    int FTENumsOfServitors ;
    // int PTENumsOfServitors ;
    public void fteServitors(String user){
        //String ftesUsers[] = new String[3] ;
        ftesUsers[FTENumsOfServitors] = user ;
        FTENumsOfServitors++;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("LoginManager started, waiting for clients");
        try {
            ServerSocket server = new ServerSocket(9831);

            while (true) {
                //dataOutputStream.writeUTF("Wellcome\n");
                int e1 = 0, e2 = 0;
                while (true) {

                    String username, password;
                    int dobreak = 0;
                    Socket connection = server.accept();

                    OutputStream outputStream = connection.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    InputStream inputStream = connection.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(inputStream);

                    dataOutputStream.writeUTF("Enter Or Register?");
                    String answers = dataInputStream.readUTF();
                    if (answers.equals("Register")) {
                        dataOutputStream.writeUTF("FulltimeEmployee or ParttimeEmployee?");
                        String answer = dataInputStream.readUTF();
                        if (answer.equals("FulltimeEmployee"))
                        {
                            dataOutputStream.writeUTF("Enter Your Username: FTE" + e1);
                            username = "FTE" + e1 + dataInputStream.readUTF();
                            e1++;
                            dataOutputStream.writeUTF("Enter Your Password: ");
                            password = dataInputStream.readUTF();
                            fulltimeemployee[McntFtE++] = new FulltimeEmployee(username, password);
                            cntFtE++;

                            System.out.println(McntFtE) ;
                        }
                        if (answer.equals("ParttimeEmployee")) {
                            dataOutputStream.writeUTF("Enter Your Username: PTE" + e2);
                            username = "PTE" + e2 + dataInputStream.readUTF();
                            e2++;
                            dataOutputStream.writeUTF("Enter Your Password: ");
                            password = dataInputStream.readUTF();

                            parttimeemployee[McntPtE++] = new ParttimeEmployee(username, password);
                            cntPtE++;

                        }
                        Write();
                    }
                    if (answers.equals("Enter")) {
                        dataOutputStream.writeUTF("Enter Your Username: ");
                        username = dataInputStream.readUTF();
                        dataOutputStream.writeUTF("Enter Your Password: ");
                        password = dataInputStream.readUTF();
                        //dataOutputStream.writeUTF(Read(username, password));
                        // if (Read(username, password).compareTo("The Username or Password is not true!") != 0)


                        //Login(username, "Login");

                        //if (username.charAt(0) == 'F')
                        int f = 0 ;


                        for ( f = 0 ; f < McntFtE ; f ++){
                            if (fulltimeemployee[f].username.equals(username) && fulltimeemployee[f].password.equals(password)){
                                dataOutputStream.writeUTF("FTE");
                                dataOutputStream.writeInt(fulltimeemployee[f].FTENumsOfServitors);
                                for (int i = 0 ; i <fulltimeemployee[f].FTENumsOfServitors ; i ++){
                                    dataOutputStream.writeUTF(fulltimeemployee[f].ftesUsers[i]);
                                }

                                            /*dataOutputStream.writeUTF("1-Introduce Task");
                                            dataOutputStream.writeUTF("2-SeeTasks");
                                            dataOutputStream.writeUTF("3-See UnDo Tasks & Reporting");
                                            dataOutputStream.writeUTF("4-See Servitors Tasks");
                                            dataOutputStream.writeUTF("5-See Other Servitors Tasks And Score");
                                            dataOutputStream.writeUTF("6-Personal Page Exit");
                                            dataOutputStream.writeUTF("7-Site Exit");
                                    //for (int f = 0 ; f < McntFtE ; f ++)
                                    {
                                        //if (fulltimeemployee[f].username.equals(username) && fulltimeemployee[f].password.equals(password))
                                        {
                                            if (dataInputStream.readInt() == 1){
                                                System.out.println("1 zad");
                                                System.out.println("*Deadline:");
                                                // st = s.next();
                                                Date date = new Date();
                                                //System.out.print("\t  Month: ");
                                                dataOutputStream.writeUTF("\t  Month:");
                                                int month = dataInputStream.readInt() ;
                                                date.setMonth(month);
                                                //System.out.print("\t  Day: ");
                                                dataOutputStream.writeUTF("\t  Day: ");
                                                int day = dataInputStream.readInt();
                                                date.setDay(day);
                                                //System.out.print("\t  Year: ");
                                                dataOutputStream.writeUTF("\t  Year:");
                                                int year = dataInputStream.readInt() ;
                                                date.setYear(year);
                                                System.out.println();
                                                //System.out.print(" *Explanation: ");
                                                dataOutputStream.writeUTF(" *Explanation:");
                                                String explanation = dataInputStream.readUTF();
                                                for (int i = 0; i < fulltimeemployee[f].FTENumsOfServitors; i++) {
                                                    dataOutputStream.writeUTF(fulltimeemployee[f].ftesUsers[i]);
                                                }
                                                //System.out.print(M.fulltimeemployee[f].NumsOfServitors);
                                                //dataInputStream.("Enter Servitor's Username Of This Task: ");
                                                String str = s.next();
                                                fulltimeemployee[f].IntrodiousTask(date, explanation, str , f);
                                            }
                                            if (dataInputStream.readInt() == 2){
                                                fulltimeemployee[f].SeeTasks();
                                            }
                                            if (dataInputStream.readInt() == 3){
                                                int f1 = f , fr = 0;
                                                int b = 0 ;
                                                for (  b = 0 ; b < McntFtE  ; b ++){
                                                    if (fulltimeemployee[f].MannagerUsername.compareTo(fulltimeemployee[b].username)==0){//رییسش فول تایم است
                                                    fr = b ;//رئیسش
                                                        for (int j = 0 ; j < fulltimeemployee[b].FcntFtE ; j ++){
                                                            if (fulltimeemployee[f].username.compareTo(fulltimeemployee[fr].fulltimeemployee[j].username)==0){
                                                                f1 = j ;//به عنوان چندمین زیردست رئیس هست
                                                                break ;
                                                            }
                                                        }
                                                        break;
                                                    }

                                                }
                                                if (b < McntFtE){//رییسش فول تایمهM
                                                    if (fulltimeemployee[fr].fulltimeemployee[f1].NumsOfUnDoTasks >0){
                                                        fulltimeemployee[f].SeeUnDoTasks();
                                                        System.out.print("NumberOfUnDoTask: ");
                                                        int NumberOfUnDoTask = s.nextInt();
                                                        java.util.Date date = Calendar.getInstance().getTime();
                                                        DateFormat dateFormat;
                                                        dateFormat = new SimpleDateFormat("M/d/y");
                                                        String strDate = dateFormat.format(date);
                                                        if (fulltimeemployee[f].gettask[NumberOfUnDoTask].deadline.toString().compareTo(strDate)<0){
                                                        System.out.println("TIME OVER");
                                                        }
                                                        else{
                                                            System.out.print("ReportingTitle: ");
                                                            String ReportingTitle = s.next();
                                                            System.out.print("RepotrtingText: ");
                                                            String ReportingText = s.next();
                                                            System.out.println(fulltimeemployee[f].username);
                                                            fulltimeemployee[f].ReportingTask(strDate, ReportingTitle, ReportingText, NumberOfUnDoTask);
                                                            for (int k = 0; k < fulltimeemployee[fr].NumsOfServitors; k++) {
                                                                if (fulltimeemployee[f].username.compareTo(fulltimeemployee[fr].ServitorsUsernames[k]) == 0) {
                                                                    fulltimeemployee[fr].getReportingFulltimeServitors(k, NumberOfUnDoTask, strDate, ReportingTitle ,fr);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else System.out.println("NO UnDo Task");
                                                }
                                                if (b >= McntFtE){//رییسش منیجره
                                                for ( b= 0 ; b < McntFtE; b ++){
                                                    if (fulltimeemployee[f].username.compareTo(fulltimeemployee[b].username)==0) {
                                                        f1 = b ;
                                                        break ;
                                                    }
                                                }
                                                if (fulltimeemployee[f1].NumsOfUnDoTasks > 0){
                                                    fulltimeemployee[f].SeeUnDoTasks();
                                                    System.out.print("NumberOfUnDoTask: ");
                                                    int NumberOfUnDoTask = s.nextInt();
                                                    java.util.Date date = Calendar.getInstance().getTime();
                                                    DateFormat dateFormat;
                                                    dateFormat = new SimpleDateFormat("M/d/y");
                                                    String strDate = dateFormat.format(date);
                                                    if (fulltimeemployee[f].gettask[NumberOfUnDoTask].deadline.toString().compareTo(strDate) < 0) {
                                                        System.out.println("TIME OVER");

                                                    }
                                                    else {

                                                        System.out.print("ReportingTitle: ");
                                                        String ReportingTitle = s.next();
                                                        System.out.print("RepotrtingText: ");
                                                        String ReportingText = s.next();
                                                        System.out.println(fulltimeemployee[f].username);
                                                        fulltimeemployee[f].ReportingTask(strDate, ReportingTitle, ReportingText, NumberOfUnDoTask);
                                                        String UserOfMannagerOfThisServitor = "Mannager04";
                                                        for (int g = 0; g < NumsOfServitors; g++) {
                                                            if (fulltimeemployee[f].username.compareTo(ServitorsUsernames[g]) == 0) {
                                                                getReportingFulltimeServitors(g, NumberOfUnDoTask, strDate, ReportingTitle);
                                                            }
                                                        }
                                                    }
                                                }
                                                else System.out.println("NO UnDo Task");
                                                }

                                            }
                                            if (dataInputStream.readInt() == 4){
                                                for (int i = 0; i < fulltimeemployee[f].NumsOfServitors; i++) {
                                                    System.out.println( fulltimeemployee[f].ServitorsUsernames[i]);
                                                }

                                                System.out.print("Which employee? ");
                                                String n = s.next();
                                                System.out.print("Which Task? ");
                                                int m = s.nextInt();
                                                fulltimeemployee[f].SeeServitorsTasks(n, m , f);
                                            }
                                             if (dataInputStream.readInt() == 5){

                                                SeeServitorsTasksAndScores();

                                             }
                                             //if (dataInputStream.readInt() == 6) break ;
                                             if (dataInputStream.readInt() == 6)
                                                System.out.println(username + " logout");
                                            }
                                             if (dataInputStream.readInt()  == 7){
                                                dobreak = 1 ;
                                                break ;
                                             }
                                        }
                                    }
                                        break;
                                }
                            }


                                if (f == McntFtE) {

                                    dataOutputStream.writeUTF("1-SeeTasks");
                                    dataOutputStream.writeUTF("2-SeeUnDoTasks & Reporting");
                                    dataOutputStream.writeUTF("3-See UnDo Tasks & Reporting");
                                    dataOutputStream.writeUTF("4-Personal Page Exit");
                                    dataOutputStream.writeUTF("5-SiteExit");
                                    for (int fs = 0 ; fs < McntPtE ; fs ++){
                                        if (parttimeemployee[fs].username.equals(username) && parttimeemployee[fs].password .equals(password)){
                                            if (dataInputStream.readInt() == 1){
                                                parttimeemployee[fs].SeeTasks();
                                            }
                                            if (dataInputStream.readInt() == 2){
                                                int f1 = fs , fr = 0;
                                            int b ;
                                            for ( b = 0 ; b < McntFtE  ; b ++){
                                                if (parttimeemployee[fs].MannagerUsername.compareTo(fulltimeemployee[b].username)==0){//رییسش فول تایم است
                                                    fr = b ;
                                                    for (int j = 0 ; j < McntFtE ; j ++){
                                                        if (parttimeemployee[fs].username.compareTo(fulltimeemployee[fr].parttimeemployee[j].username)==0){
                                                            f1 = j ;
                                                            break ;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                            if (b < McntFtE){//رییسش فول تایمه
                                                if (fulltimeemployee[fr].parttimeemployee[f1].NumsOfUnDoTasks >=0){
                                                    parttimeemployee[fs].SeeUnDoTasks();
                                                    System.out.print("NumberOfUnDoTask: ");
                                                    int NumberOfUnDoTask = s.nextInt();
                                                    java.util.Date date = Calendar.getInstance().getTime();
                                                    DateFormat dateFormat;
                                                    dateFormat = new SimpleDateFormat("M/d/y");
                                                    String strDate = dateFormat.format(date);
                                                    if (parttimeemployee[fs].gettask.deadline.toString().compareTo(strDate)<0){
                                                        System.out.println("TIME OVER");
                                                    }
                                                    else{
                                                        System.out.print("ReportingTitle: ");
                                                        String ReportingTitle = s.next();
                                                        System.out.print("RepotrtingText: ");
                                                        String ReportingText = s.next();
                                                        System.out.println(parttimeemployee[fs].username);
                                                        parttimeemployee[fs].ReportingTask(strDate, ReportingTitle, ReportingText, NumberOfUnDoTask);
                                                        for (int k = 0; k < fulltimeemployee[fr].NumsOfServitors; k++) {
                                                            if (parttimeemployee[fs].username.compareTo(fulltimeemployee[fr].ServitorsUsernames[k]) == 0) {
                                                                fulltimeemployee[fr].getReportingParttimeServitors(k, NumberOfUnDoTask, strDate, ReportingTitle ,fr);
                                                            }
                                                        }
                                                    }
                                                }
                                                else System.out.println("NO UnDo Task");
                                            }
                                            if (b >= McntFtE){//رییسش منیجره
                                                for ( b = 0 ; b < McntPtE ; b ++){
                                                    if (parttimeemployee[f].username.compareTo(parttimeemployee[b].username)==0) {
                                                        f1 = b ;
                                                        break ;
                                                    }
                                                }
                                                if (parttimeemployee[f1].NumsOfUnDoTasks >= 0){
                                                    parttimeemployee[fs].SeeUnDoTasks();
                                                    System.out.print("NumberOfUnDoTask: ");
                                                    int NumberOfUnDoTask = s.nextInt();
                                                    java.util.Date date = Calendar.getInstance().getTime();
                                                    DateFormat dateFormat;
                                                    dateFormat = new SimpleDateFormat("M/d/y");
                                                    String strDate = dateFormat.format(date);
                                                    if (parttimeemployee[fs].gettask.deadline.toString().compareTo(strDate) < 0) {
                                                        System.out.println("TIME OVER");
                                                    } else {
                                                        System.out.print("ReportingTitle: ");
                                                        String ReportingTitle = s.next();
                                                        System.out.print("RepotrtingText: ");
                                                        String ReportingText = s.next();
                                                        System.out.println(parttimeemployee[fs].username);
                                                        parttimeemployee[fs].ReportingTask(strDate, ReportingTitle, ReportingText, NumberOfUnDoTask);
                                                        String UserOfMannagerOfThisServitor = "Mannager04";
                                                        for (int k = 0; k < NumsOfServitors; k++) {
                                                            if (parttimeemployee[fs].username.compareTo(ServitorsUsernames[k]) == 0) {
                                                                getReportingParttimeServitors(k, NumberOfUnDoTask, strDate, ReportingTitle);
                                                            }
                                                        }
                                                    }
                                                }
                                                else System.out.println("NO UnDo Task");
                                            }
                                            }
                                            if (dataInputStream.readInt() == 3){
                                                SeeServitorsTasksAndScores();
                                            }
                                            if (dataInputStream.readInt() == 4) {
                                                Login(username, "Logout");
                                                break;
                                            }
                                            if (dataInputStream.readInt() == 5){
                                                dobreak = 1 ;
                                                break ;
                                            }
                                        }
                                    }

                                }
                                if (dobreak == 1) {
                                    dobreak = 0 ;
                                    break ;
                                }
                            }
                        }*/
                        /*else{
                            dataOutputStream.writeUTF("The Username or Password is not true!");
                                            kjj

                        }*/

                                break ;

                            }

                        }
                        if (f == McntFtE){
                            int i ;
                            for (i = 0 ; i <McntPtE ; i ++){
                                if (fulltimeemployee[f].username.equals(username) && fulltimeemployee[f].password.equals(password)){
                                    dataOutputStream.writeUTF("PTE");
                                    break ;
                                }
                            }
                            if (i == McntPtE){
                                dataOutputStream.writeUTF("Not Found");
                            }

                        }
                    }

                    if (answers.compareTo("Enter") != 0 && answers.compareTo("Register") != 0) {
                        System.out.println("invalid input");
                    }
                    if (dobreak == 1) {
                        dobreak = 0;
                        break;
                    }
                    dataOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Write() throws IOException {

        File EmployeesAndServitors = new File("F:\\Information");
        EmployeesAndServitors.mkdir();

        File EAS = new File(EmployeesAndServitors,"Employee.txt");
        Formatter formatE = new Formatter(EAS);
        EAS.createNewFile();

        formatE.format("%s \n%s \n%s \n","Boss Of Company",User,Pass);
        for (int i = 0 ; i < NumsOfServitors ; i++)
            formatE.format("%d %s  ", i , ServitorsUsernames[i]);
        formatE.format("\n\n%s \n","FUll_Time_Employee");
        for(int i = 0 ; i < McntFtE ; i++){
            formatE.format("%s \n%s \n",fulltimeemployee[i].username,fulltimeemployee[i].password);
            for (int j = 0 ; j < fulltimeemployee[i].NumsOfServitors ; j++){
                formatE.format("%d %S  ", j ,fulltimeemployee[i].ServitorsUsernames[j]);
            }
        }
        formatE.format("\n\n%s \n","Part_Time_Employee");
        for (int i = 0 ; i < McntPtE ; i++)
            formatE.format("%s \n%s \n",parttimeemployee[i].username,parttimeemployee[i].password);
        formatE.flush(); formatE.close();
    }

    public String Read(String user , String pass) throws FileNotFoundException {

        File employee = new File("F:\\Information\\Employee.txt");
        Scanner scanner = new Scanner(employee);
        if (user.equals(scanner.nextLine()) && pass.equals(scanner.nextLine()) )
            return scanner.nextLine();
        else if (user.charAt(0) == 'F')return "The Username or Password is not true! Or You have no servitories!\n";
        else return "You are part_time_employee and you can not have servitory!";
    }


    public void Login(String user,String condition) throws FileNotFoundException {
        String[][] s = new String[200][2];
        int i;
        for (i=0 ; i <  200 ; i++)
            s[i] = new String[2];

        for (i = 0 ; i < n ; i++) {
            if (user.equals(s[i][0])) s[i][1] = condition;
            break;
        }
        if (i == n){
            s[n][0] = user;
            s[n++][1] = condition;
        }
        File file = new File("F:\\Information\\connection");
        Formatter formatter = new Formatter(file);
        for (i = 0 ; i < n ; i++) {
            formatter.format("%s: %s",s[i][0],s[i][1]);
        }

    }

    void loginAndlogout() throws FileNotFoundException {
        File file = new File("F:\\Information\\connection");
        Scanner scanner = new Scanner(file);
        System.out.println(scanner.hasNext());
    }

    public void list() {
        Newintroducetask();
        print(1, "Choose servitor");//انتخاب زیردست
        print(2,"Choose servitor for Fulltime_Employees");
        print(3, "Introduce Task");//معرفی تسک برای هر زیردست
        print(4, "See Servitors Tasks");// بررسی کردن اینکه زیردست تسک را انجام داده یا خیر
        print(5,"See Other Servitors Tasks And Score");
        //print(6,"See Employees");//فایل مربوط به کل کارمندان
        print(6,"See Servitors of Employee");
        print(7,"See Login And Logout");
        print(8, "Personal Page Exit");//خارج شدن از صفحه ی کاربر و رفتن به صفحه ی اصلی سایت
        print(9, "Site Exit");//خارج شدن کامل از سایت
    }

    public void ChooseSevitor(String user) {
        int i;
        for (i=0 ; i < McntFtE ; i++){
            if (user.equals(fulltimeemployee[i].username)){
                FTEServitors[NSFTE++] = fulltimeemployee[i];
                fulltimeemployee[i].MannagerUsername = User;
                break;
            }
        }
        if (i < McntFtE )
            for (i=0 ; i < McntPtE ; i++){
                if (user.equals(parttimeemployee[i].username)){
                    PTEServitors[NSPTE++] = parttimeemployee[i];
                    parttimeemployee[i].MannagerUsername = User;
                }
            }
        ServitorsUsernames[NumsOfServitors++] = user;
    }

    public void ChooseServitorForFulltime_Employees(String Fuser, String Suser){
        for (int i=0 ; i < McntFtE ; i++){
            if (Fuser.equals(fulltimeemployee[i].username)){
                int j;
                fulltimeemployee[i].ServitorsUsernames[fulltimeemployee[i].NumsOfServitors++] = Suser;
                for (j = 0 ; j < McntFtE ; j++) {
                    if (Suser.equals(fulltimeemployee[j].username)) {
                        fulltimeemployee[j].MannagerUsername = fulltimeemployee[i].username;
                        fulltimeemployee[j].ServitorOrNo = 1;
                        fulltimeemployee[i].fulltimeemployee[fulltimeemployee[i].FcntFtE++] = fulltimeemployee[j];
                        break;
                    }
                }
                if (j < McntFtE){
                    for (j = 0 ; j < McntPtE ; j++) {
                        if (Suser.equals(parttimeemployee[j].username)) {
                            parttimeemployee[j].MannagerUsername = fulltimeemployee[i].username;
                            parttimeemployee[j].ServitorOrNo = 1;
                            fulltimeemployee[i].parttimeemployee[fulltimeemployee[i].FcntPtE++] = parttimeemployee[j];
                        }
                    }
                }
            }
        }
    }

    public void IntroduceTask(Date deadline, String explaintask, String ServitorUsernameOfTask) {//تعریف تسک
        for (int i = 0; i < NumsOfServitors; i++) {
            if (ServitorUsernameOfTask.compareTo(ServitorsUsernames[i]) == 0) {
                for (int j = 0; j < McntFtE ; j++) {
                    if (ServitorsUsernames[i].compareTo(fulltimeemployee[j].username) == 0) {//کارمند زیردست فول تایم است
                        fulltimeemployee[j].GetTask(deadline, explaintask);//فرستادن تسک برای کارمند زیردست
                        for (int k = 0 ; k < NSFTE ; k ++ ){
                            if (ServitorsUsernames[i].compareTo(FTEServitors[k].username)==0){
                                introducetask[i][FTEServitors[k].NumsOfGetTasks].TasksExplain = explaintask ;
                                FTEServitors[k].GetTask(deadline, explaintask);
                            }
                        }
                        break;
                    }
                }
                for (int j = 0; j < McntPtE; j++) {
                    if (ServitorsUsernames[i].compareTo(parttimeemployee[j].username) == 0) {//کارمند زیردست پلرت تایم است
                        parttimeemployee[j].GetTask(deadline, explaintask);
                        for (int k = 0 ; k < NSPTE ; k ++){
                            if (ServitorsUsernames[i].compareTo(PTEServitors[k].username)==0){
                                PTEServitors[k].GetTask(deadline, explaintask);
                            }
                        }

                        break;
                    }
                }
            }
        }
    }

    public void Newintroducetask(){
        for (int i = 0 ; i < 3 ; i ++){
            for (int j = 0 ; j < 3 ; j ++) introducetask[i][j] = new IntroduceTask();
        }
    }

    public void getReportingFulltimeServitors(int NumsOfServitor, int NumsOfSrvitorsTask, String DeliveryDate, String reporting) {

        System.out.println("Change Status") ;
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("DONE");
        FTEServitors[NumsOfServitor].gettask[NumsOfSrvitorsTask].Condition = "DONE";
        for (int i = 0 ; i < cntFtE ; i ++){
            if (fulltimeemployee[i].username.compareTo(fulltimeemployee[NumsOfServitor].username)==0)
            {
                fulltimeemployee[i].gettask[NumsOfSrvitorsTask].Condition="DONE";
            }
        }
        System.out.println(fulltimeemployee[NumsOfServitor].gettask[NumsOfSrvitorsTask].Condition);
        fulltimeemployee[NumsOfServitor].NumsOfUnDoTasks -- ;
    }
    public void getReportingParttimeServitors(int NumsOfServitor, int NumsOfSrvitorsTask, String DeliveryDate, String reporting) {
        System.out.println("Change Status") ;
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("DONE");
        parttimeemployee[NumsOfServitor].gettask.Condition = "DONE";
        parttimeemployee[NumsOfServitor ].NumsOfUnDoTasks -- ;
    }
    public void SetConditionAsTimeOver(int NumsOfServitor, int NumsOfSrvitorsTask) {
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("TIME OVER");
    }
    public void SeeServitorsTasks(String ServitorsUsername , int NumsOfServitorTask){
        int i ;
        for ( i = 0 ; i < McntFtE ; i ++){
            if (fulltimeemployee[i].username.compareTo(ServitorsUsername)==0){
                System.out.println(fulltimeemployee[i].gettask[NumsOfServitorTask].ExplanationOfTask);
                System.out.println(fulltimeemployee[i].gettask[NumsOfServitorTask].Condition);
                break;
            }
        }
        if (i >= McntFtE){
            for (int j = 0 ; j < McntPtE ; j ++){
                if (parttimeemployee[j].username.compareTo(ServitorsUsername) == 0){
                    System.out.println(parttimeemployee[j].gettask.ExplanationOfTask);
                    System.out.println(parttimeemployee[j].gettask.Condition);
                }
            }
        }
    }

    public void SeeServitorsTasksAndScores() {
        for (int i = 0 ; i < McntFtE ; i ++){
            if (fulltimeemployee[i].NumsOfGetTasks > 0){
                System.out.println(fulltimeemployee[i].username);
                fulltimeemployee[i].SeeDoneTasks();
                System.out.println(fulltimeemployee[i].Score);
            }
        }
        for (int i = 0 ; i < McntPtE ; i ++){
            if (parttimeemployee[i].NumsOfGetTasks>=0){
                System.out.println(parttimeemployee[i].username);
                parttimeemployee[i].SeeDoneTasks();
                System.out.println(parttimeemployee[i].Score);
            }
        }
    }
}


class FulltimeEmployee extends BaseEmployee {
    String username , password , MannagerUsername ;
    private Socket connection;
    String ftesUsers[] = new String[3];
    int FTENumsOfServitors = 0 ;
    public void fteServitors(String user){
        //String ftesUsers[] = new String[3] ;
        ftesUsers[FTENumsOfServitors] = user ;
        FTENumsOfServitors++;
    }
    FulltimeEmployee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    boolean checkInfo(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    boolean isLoggedIn() {
        return connection != null;
    }
    int ServitorOrNo;//0 یعنی زیردست کسی نسیت , 1 یعنی زیردست هست


    int NumsOfServitors = 0;
    String ServitorsUsernames[] = new String[3];

    //GetTask gettask[] = new GetTask[3];
    int NumsOfGetTasks = 0;
    //IntroduceTask introducetask[][] = new IntroduceTask[3][3];
    int NumsOfTasks[] = new int[3];
    //ReportingTask reportingtask[] = new ReportingTask[3];//تسک های هر کارمند که باید برایشان گزارش بنویسد
    int NumsOfUnDoTasks = 0 ;

    FulltimeEmployee fulltimeemployee[] = new FulltimeEmployee[3];
    ParttimeEmployee parttimeemployee[] = new ParttimeEmployee[3];
    int FcntFtE = 0;
    int FcntPtE = 0;

    public FulltimeEmployee(){}

    GetTask gettask[] = new GetTask[3];

    IntroduceTask introducetask[][] = new IntroduceTask[3][3];

    ReportingTask reportingtask[] = new ReportingTask[3];//تسک های هر کارمند که باید برایشان گزارش بنویسد

    public void SeeServitorsTasks(String ServitorsUsername , int NumsOfServitorTask , int f){
        int i ;
        for ( i = 0 ; i < fulltimeemployee[f].FcntFtE ; i ++){
            if (fulltimeemployee[f].fulltimeemployee[i].username.compareTo(ServitorsUsername)==0){
                System.out.println(fulltimeemployee[f].fulltimeemployee[i].gettask[NumsOfServitorTask].ExplanationOfTask);
                System.out.println(fulltimeemployee[f].fulltimeemployee[i].gettask[NumsOfServitorTask].Condition);
                break;
            }
        }
        if (i >= fulltimeemployee[f].FcntFtE){
            for (int j = 0 ; j < fulltimeemployee[f].FcntPtE ; j ++){
                if (fulltimeemployee[f].parttimeemployee[j].username.compareTo(ServitorsUsername) == 0){
                    System.out.println(fulltimeemployee[f].parttimeemployee[j].gettask.ExplanationOfTask);
                    System.out.println(fulltimeemployee[f].parttimeemployee[j].gettask.Condition);
                }
            }
        }
    }

    public void Newintroducetask(){
        for (int i = 0 ; i < 3 ; i ++)
            for (int j = 0 ; j < 3 ; j ++)
                introducetask[i][j] = new IntroduceTask();
    }

    public void list() {
        Newintroducetask();
        print(1, "Introduce Task");
        print(2, "SeeTasks");//دیدن تسک هایی که به کارمند محول شده
        print(3, "See UnDo Tasks & Reporting");//4 دیدن تسک های انجام نشده
        print(4,"See Servitors Tasks");
        print(5,"See Other Servitors Tasks And Score");//6 دیدن تسک های محول شده به سایر کارمندان و همچنین مشاهده ی وضعیت آنان
        print(6, "Personal Page Exit");//خارج شدن از صفحه ی شخصی کارمند
        print(7,"Site Exit");//خارج شدن از سایت
    }

    public void getReportingFulltimeServitors(int NumsOfServitor, int NumsOfSrvitorsTask, String DeliveryDate, String reporting , int fr) {
        System.out.println("Change Status") ;
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("DONE");
        fulltimeemployee[fr].fulltimeemployee[NumsOfServitor].gettask[NumsOfSrvitorsTask].Condition = "DONE";
        for (int i = 0 ; i < cntFtE ; i ++){
            if (fulltimeemployee[i].username.compareTo(fulltimeemployee[fr].fulltimeemployee[NumsOfServitor].username)==0)
            {
                fulltimeemployee[i].gettask[NumsOfSrvitorsTask].Condition="DONE";
            }
        }
        fulltimeemployee[fr].fulltimeemployee[NumsOfServitor].NumsOfUnDoTasks -- ;
    }

    public void getReportingParttimeServitors(int NumsOfServitor, int NumsOfSrvitorsTask, String DeliveryDate, String reporting , int fr) {
        System.out.println("Change Status") ;
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("DONE");
        fulltimeemployee[fr].parttimeemployee[NumsOfServitor].gettask.Condition = "DONE";
        fulltimeemployee[fr].parttimeemployee[NumsOfServitor ].NumsOfUnDoTasks -- ;
    }

    public void getReporting(int NumsOfServitor, int NumsOfSrvitorsTask, String DeliveryDate, String reporting) {
        introducetask[NumsOfServitor][NumsOfSrvitorsTask] = new IntroduceTask();
        System.out.println("Change Status") ;
        introducetask[NumsOfServitor][NumsOfSrvitorsTask].SetCondition("DONE");
        parttimeemployee[NumsOfServitor].NumsOfGetTasks -- ;
    }

    public void IntrodiousTask(Date deadline, String explaintask, String ServitorUsernameOfTask , int f) {
        for (int i = 0; i < fulltimeemployee[f].NumsOfServitors; i++) {
            if (ServitorUsernameOfTask.compareTo(ServitorsUsernames[i]) == 0) {
                for (int j = 0; j < cntFtE ; j++) {
                    if (ServitorsUsernames[i].compareTo(fte[j].username) == 0) {//کارمند زیردست فول تایم است
                        fulltimeemployee[j].GetTask(deadline, explaintask);//فرستادن تسک برای کارمند زیردست
                        for (int k = 0 ; k<fulltimeemployee[f].FcntFtE ; k ++){
                            if (ServitorsUsernames[i].compareTo(fulltimeemployee[f].fulltimeemployee[k].username)==0){
                                introducetask[i][fulltimeemployee[f].fulltimeemployee[k].NumsOfGetTasks].TasksExplain = explaintask ;
                                fulltimeemployee[f].fulltimeemployee[k].GetTask(deadline, explaintask);
                            }
                        }
                        break ;
                    }
                }
                for (int j = 0; j < cntPtE; j++) {
                    if (ServitorsUsernames[i].compareTo(pte[j].username) == 0) {
                        parttimeemployee[j].GetTask(deadline, explaintask);
                        for (int k = 0 ;  k<fulltimeemployee[f].FcntPtE ; k ++){
                            if (ServitorsUsernames[i].compareTo(fulltimeemployee[f].parttimeemployee[k].username)==0){
                                fulltimeemployee[f].parttimeemployee[k].GetTask(deadline, explaintask);
                            }
                        }
                        break ;
                    }
                }
            }
        }
    }

    public void GetTask(Date deadline, String ExplainTask) {//تسک هایی که این کارمند موظف به انجام ان است
        gettask[NumsOfGetTasks++] = new GetTask(deadline, ExplainTask);
        NumsOfUnDoTasks ++ ;
    }

    public void SeeTasks() {
        for (int i = 0; i < NumsOfGetTasks; i++) {
            System.out.println(gettask[i].deadline);
            System.out.println(gettask[i].ExplanationOfTask);
        }
    }

    public void SeeUnDoTasks() {
        for (int i = 0; i < NumsOfGetTasks; i++) {
            if (gettask[i].Condition .compareTo("UnDo") == 0) {
                System.out.println(gettask[i].ExplanationOfTask);
            }
        }
    }

    public void SeeDoneTasks(){
        for (int i = 0; i < NumsOfGetTasks; i++) {
            if (gettask[i].Condition .compareTo("UnDo") != 0) {
                System.out.println(gettask[i].ExplanationOfTask);
            }
        }
    }

    public void ReportingTask(String DateOfSentTask, String Subject, String Text, int i) {
        Score++;
        reportingtask[i] = new ReportingTask();
        reportingtask[i].ReportingsDate = DateOfSentTask;
        reportingtask[i].TitleOfReport = Subject;
        reportingtask[i].TextOfReporting = Text;
    }


}


class ParttimeEmployee extends BaseEmployee {
    String username , password , MannagerUsername;
    private Socket connection;

    ParttimeEmployee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    boolean checkInfo(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    boolean isLoggedIn() {
        return connection != null;
    }
    int ServitorOrNo;//0 یعنی زیردست کسی نسیت ,   1 یعنی زیردست هست


    int NumsOfGetTasks = 0;

    int NumsOfUnDoTasks = 0 ;

    GetTask gettask ;

    ReportingTask reportingtask[] = new ReportingTask[3];//تسک های هر کارمند که باید برایشان گزارش بنویسد

    public ParttimeEmployee(){}
    public void list() {
        print(1, "SeeTasks");//دیدن کل تسک های محول شده به این کارمند
        print(2, "SeeUnDoTasks & Reporting");//دیدن تسک هایی که به کارمند محول شده و کارمند آنها را انجام نداده و نوشتن گزارش برای انها
        print(3, "See Other Servitors Tasks And Score");//دیدن تسک های محول شده به سایر کارمندان
        print(4,"Personal Page Exit");//خارج شدن از صفحه ی شخصی کارمند
        print(5, "SiteExit");//خارج شدن از سایت
    }

    public void SeeTasks() {
        System.out.println(gettask.deadline);
        System.out.println(gettask.ExplanationOfTask);
    }
    public void SeeUnDoTasks() {
        if (gettask.Condition == "UnDo") System.out.println(gettask.ExplanationOfTask);
    }
    public void SeeDoneTasks() {
        if (gettask.Condition != "UnDo") System.out.println(gettask.ExplanationOfTask);
    }
    public void ReportingTask(String DateOfSentTask, String Subject, String Text, int i) {
        Score++;
        reportingtask[i] = new ReportingTask();
        reportingtask[i].ReportingsDate = DateOfSentTask;
        reportingtask[i].TitleOfReport = Subject;
        reportingtask[i].TextOfReporting = Text;
    }
    public void GetTask(Date deadline, String ExplainTask) {//تسک هایی که این کارمند موظف به انجام ان است
        gettask = new GetTask(deadline, ExplainTask);
        NumsOfGetTasks++;
    }
    public void NewPartTimeEmployee(String User, String Pass) {
        username = User;
        password = Pass;
    }
}
class IntroduceTask {//تعریف تسک برای زیردستان
    Date date;
    String TasksExplain = "ll";//متن یا توضیحات تسک
    String ServitorsUsername;//یوزر کابری که تسک بهش محول شده//یا کاربری که تسک رو باید انجام بده
    private String Condition = "undone";
    public IntroduceTask(Date deadline, String explanation, String userofservitor) {
        date = deadline;
        TasksExplain = explanation;
        ServitorsUsername = userofservitor;
    }
    public IntroduceTask() {}
    public void SetCondition(String st) { Condition = st; }
    public String GetCondition() { return Condition; }
}


class GetTask {//دریافت تسک توسط زیردستی که باید تسک را انجام دهد
    Date deadline;
    String ExplanationOfTask;
    String Condition = "UnDo";
    public GetTask(Date deadlineoftask, String explanation) {
        deadline = deadlineoftask;
        ExplanationOfTask = explanation;
    }
}


class ReportingTask {//نوشتن گزارش برای هر تسک
    String ReportingsDate;
    String TitleOfReport;
    String TextOfReporting;
}


class GetReporting {
    Date DeliveryDate;
    String Reporting;
}