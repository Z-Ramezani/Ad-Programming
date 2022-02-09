package google;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            Scanner scanner1 = new Scanner(System.in);
            while (true) {
                Socket connection = new Socket("127.0.0.1", 9831);
                InputStream inputStream = connection.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                int dobreak = 0 ;
                System.out.println(dataInputStream.readUTF());
                String s = scanner1.next();
                dataOutputStream.writeUTF(s);
                if (s.equals("Register")) {
                    System.out.println(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(scanner1.next());
                    System.out.print(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(scanner1.next());
                    System.out.print(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(scanner1.next());
                }
                if (s.equals("Enter")) {
                    System.out.println(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(scanner1.next());
                    System.out.println(dataInputStream.readUTF());
                    dataOutputStream.writeUTF(scanner1.next());
                    String s1 = dataInputStream.readUTF();
                    //خروج
                    //System.out.println(s1);
                    try {
                        if (s1.equals("Not Found")) {
                            throw new MyException("Invalid Username Or Password");
                        }
                        else{
                            int cnts = dataInputStream.readInt();
                            String st ;
                            System.out.println(cnts) ;
                            for (int i = 0 ; i < cnts ; i ++){
                                st = dataInputStream.readUTF() ;
                                System.out.println(st);
                            }
                            System.out.println("yess") ;
                        }
                    }catch (MyException e){
                        System.err.println(e.getMessage());
                    }
                    System.out.println("Exit Or No? ") ;
                    String choose = scanner1.next();
                    if (choose .equals("Exit")) dobreak = 1 ;

                }
                outputStream.close();
                if (dobreak == 1) {
                    dobreak = 0 ;
                    break ;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class MyException extends Exception{ //برای خطای نادرست بودن یوزر و پسورد
    public MyException(String s){
        super(s);
    }
}
class MyException2 extends Exception{ //خطای تمام شدن ددلاین گزارش
    public MyException2(String s){
        super(s);
    }
}