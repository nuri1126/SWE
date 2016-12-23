import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Schedule implements Serializable {
   private String scheduleContent;
   private int scheduleNumber;
   private String scheduleDate;
   
   public Schedule(String scheduleContent, int scheduleNumber, String scheduleDate) {
      this.scheduleContent = scheduleContent;
      this.scheduleNumber = scheduleNumber;
      this.scheduleDate = scheduleDate;
   }
   
   public int getScheduleNumber() {
      return scheduleNumber;
   }
   
   public void setScheduleNumber(int scheduleNumber) {
      this.scheduleNumber = scheduleNumber;
   }
   @Override
   public String toString() {
      return "["+scheduleNumber+"]"+"Content : " + scheduleContent + ", Date : " + scheduleDate;
   }
}

class ScheduleClass {
   static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
   static Vector<Schedule> scheduleVector = new Vector<Schedule>();
   final static int initialScheduleNumber = -1;
   private static final String String = null;
   
   static void scheduleMain() throws IOException {
      BufferedReader inputNumber = new BufferedReader(inputStreamReader);
      boolean managingSchedule = true;
      while(managingSchedule){
         System.out.println("----------SCHEDULE----------");
         System.out.println("Please enter the task number.");
         System.out.println("1.Add Schedule");
         System.out.println("2.View All Schedule");
         System.out.println("3.Delete Schedule");
         System.out.println("0.Quit");
         System.out.print(">>>");
         switch(inputNumber.readLine()){
            case "1":
               add();
               break;
            case "2":
               view(isEmpty());
               break;
            case "3":
               if(scheduleVector.isEmpty()){
                  System.out.println("There are no schedule.");
                  break;
               }
               BufferedReader tempNumber = new BufferedReader(inputStreamReader);
               System.out.println("Please enter the schedule number to clear.");
               System.out.print(">>>");
               int scheduleNumber = Integer.parseInt(tempNumber.readLine());
               delete(scheduleNumber);/////////////////////////////////
               break;
            case "0":
               System.out.println("Leave the schedule menu.");
               fileSave(scheduleVector);
               managingSchedule = false;
               break;
            default :
               System.out.println("Please enter a number between 0 and 3!");
               break;
         }
      }
   }
   
   static void add() throws IOException {
      BufferedReader tempContent = new BufferedReader(inputStreamReader);
      System.out.println("Please enter your schedule.");
      System.out.print(">>>");
      String inputContent = tempContent.readLine();
      System.out.println("Please enter the date.(yyyy-MM-dd)");
      System.out.print(">>>");
      String inputDate = tempContent.readLine(); 
      //do {
      //   System.out.println("Please enter the date.(yyyy-MM-dd)");
      //   System.out.print(">>>");
      //   inputDate = tempContent.readLine();
      //}while(!CheckDateFormat(inputDate));
      //System.out.print(CheckDateFormat(inputDate));
      
      while(true){
         System.out.println("Would you like to add a schedule? (Y/N)");
         System.out.print(">>>");
         BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
         String answer = tempAnswer.readLine();
         if(answer.equals("Y")) {
            Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
            scheduleVector.add(schedule);
   System.out.println("You have saved your schedule!");
            updateNumber();
            break;
         } else if(answer.equals("N")){
            System.out.println("You have canceled the creation.");
            break;
         } else
            System.out.println("Please enter Y or N!");
      }
   }
   static int isEmpty(){
      if(!scheduleVector.isEmpty())
         return 0;
      return -1;
   }
   static boolean view(int emptyCheck) {
      if(emptyCheck!=0){
         System.out.println("There are no schedule.");
         return false;
      }
      for(int i = 0 ; i < scheduleVector.size() ; i++){
         System.out.println(scheduleVector.get(i).toString());
      }
      return true;
   }
   
   static boolean delete(int scheduleNumber) {
      for(int i = 0 ; i < scheduleVector.size() ; i++){
         Schedule tempSchedule = scheduleVector.get(i);
         if(tempSchedule.getScheduleNumber()==scheduleNumber){
            scheduleVector.remove(i);
            updateNumber();
            System.out.println("Schedule " + scheduleNumber + " has been deleted!");
            return true;
         }
      }
      System.out.println("There is no schedule number " + scheduleNumber + ". Check the schedule number again with the view menu.");
      return false;
   }
   
   static void fileSave(Object schedule) throws IOException {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("schedule.txt"));
      oos.writeObject(schedule);
      oos.close();
   }
   
   static void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException   {
      File file = new File("schedule.txt");
      try {
         if(file.exists()){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("schedule.txt"));
            scheduleVector = (Vector<Schedule>) ois.readObject();
         }
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }
   
   static void updateNumber() {
      for(int i=0 ; i < scheduleVector.size() ; i++)
         scheduleVector.get(i).setScheduleNumber(i+1);
   }
}