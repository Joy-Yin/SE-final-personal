import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class score {
	
	//管理員產生每個學生各個學期的成績單(10)
	public void generatePersonalGrade(String semester, String studentID) throws IOException, FileNotFoundException{
		String personalGrade = semester + studentID + ".txt";
		String enterScore = "";
		FileReader reader = new FileReader("classes.txt");
		Scanner scFile = new Scanner(reader);
		nextCourse:
		while(scFile.hasNext()) {
			String[] data = scFile.nextLine().split(" ");
			for(int i = 6; i < data.length; i++) {
				if(studentID.equals(data[i]) && semester.equals(data[0])) {//打開該學生選修的課程成績的檔案
					try {
						String openFile = semester + data[1] + data[2] + ".txt";
						FileReader readScore = new FileReader(openFile);
						Scanner scScore = new Scanner(readScore);
						while(scScore.hasNext()) {
							String[] student = scScore.nextLine().split(" ");
							if(studentID.equals(student[0])) {
								enterScore += data[2] + " " + student[2] + "\n";
								scScore.close();
								continue nextCourse;
							}
						}
					}
					catch(FileNotFoundException e) {
						generateCourseGrade(semester, data[1], data[2]);
						String openFile = semester + data[1] + data[2] + ".txt";
						FileReader readScore = new FileReader(openFile);
						Scanner scScore = new Scanner(readScore);
						while(scScore.hasNext()) {
							String[] student = scScore.nextLine().split(" ");
							if(studentID.equals(student[0])) {
								enterScore += data[2] + " " + student[2] + "\n";
								scScore.close();
								continue nextCourse;
							}
						}
					}
				}
			}
		}
		scFile.close();
		FileWriter writer = new FileWriter(personalGrade);
		if(enterScore.equals(""))
			writer.write("無選修課程");
		else
			writer.write(enterScore);
		writer.flush();
		writer.close();
	}
	
	//學生檢查各學期各課程成績(11) *結合GUI時須再修改此方法
	public void checkGrade(String semester, String studentID) throws IOException{
		try {
			String personalGrade = semester + studentID + ".txt";
			String studentName = searchName(studentID);
			FileReader reader = new FileReader(personalGrade);
			Scanner scFile = new Scanner(reader);
			System.out.println(semester + "學期" + studentName + "修課成績");
			System.out.println("課程:\t成績:");
			while (scFile.hasNext())
				System.out.println(scFile.nextLine());
			reader.close();
			scFile.close();
		}
		catch(FileNotFoundException e) {
			generatePersonalGrade(semester, studentID);
			String personalGrade = semester + studentID + ".txt";
			String studentName = searchName(studentID);
			FileReader reader = new FileReader(personalGrade);
			Scanner scFile = new Scanner(reader);
			System.out.println(semester + "學期" + studentName + "修課成績");
			System.out.println("課程:\t成績:");
			while (scFile.hasNext())
				System.out.println(scFile.nextLine());
			reader.close();
			scFile.close();
		}
	}
	
	//學生列印所有學期選修課程的成績單(12) *結合GUI時須再修改此方法
	public void printGrade(String studentID) throws IOException {
		FileReader reader = new FileReader("account.txt");
		Scanner scFile = new Scanner(reader);
		while(scFile.hasNext()) {
			String[] student = scFile.nextLine().split(" ");
			if(studentID.equals(student[0])) {
				for(int i = Integer.parseInt(student[3]); i < Integer.parseInt(student[3]) + 4; i++) {
					for(int j = 1; j < 3; j++) {
						try {
							String semester = Integer.toString(i) + Integer.toString(j);
							String openFile = semester + studentID + ".txt";
							reader = new FileReader(openFile);
							scFile = new Scanner(reader);
							System.out.println(semester + "學期" + student[2] +"的成績");
							while(scFile.hasNext()) {
								System.out.println(scFile.nextLine());
							}
						}
						catch(FileNotFoundException e) {
							String semester = Integer.toString(i) + Integer.toString(j);
							generatePersonalGrade(semester, studentID);
							String openFile = semester + studentID + ".txt";
							reader = new FileReader(openFile);
							scFile = new Scanner(reader);
							System.out.println(semester + "學期" + student[2] +"的成績");
							while(scFile.hasNext()) {
								System.out.println(scFile.nextLine());
							}
						}
					}
				}
				scFile.close();
				return;
			}
		}
	}
	
	//教授產生其授課課程之成績(14) *不確定是否需要顯示出來還是建立檔案就好
	public void generateCourseGrade(String semester, String subjectID, String subjectName) throws IOException {
		String courseGrade = "";
		FileReader reader = new FileReader("classes.txt");
		Scanner scFile = new Scanner(reader);
		System.out.println(semester + subjectName + "學生修課成績");
		System.out.println("學號:\t姓名:\t成績:");
		while(scFile.hasNext()) {
			String[] data = scFile.nextLine().split(" ");
			if(data[0].equals(semester) && data[1].equals(subjectID) && data[2].equals(subjectName)) {
				String fileName = semester + subjectID + subjectName + ".txt";
				FileWriter writer = new FileWriter(fileName);
				for(int i = 6; i < data.length; i++) {
					String studentName = searchName(data[i]);
					courseGrade += data[i] + " " + studentName + " -\n";
					System.out.println(data[i] + studentName + " -");
				}
				writer.write(courseGrade);
				writer.flush();
				writer.close();
				reader.close();
				scFile.close();
				return;
			}
		}
		System.out.println("無此科目");
		reader.close();
		scFile.close();
	}
	
	//管理員、教授輸入或更新某個學生的成績(9, 13)
	public String updateScore(String semester, String subjectID, String subjectName, String studentName, int studentScore) throws IOException{
		try {
			String openFile = semester + subjectID + subjectName + ".txt";//各個科目的成績檔案
			String enterScore = "";
			FileReader reader = new FileReader(openFile);
			Scanner scFile = new Scanner(reader);
			while(scFile.hasNext()) {
				String[] studentList = scFile.nextLine().split(" ");
				if(studentName.equals(studentList[1])) {
					studentList[2] = Integer.toString(studentScore);
				}
				enterScore += studentList[0] + " " + studentList[1] + " " + studentList[2] + "\n";
			}
			reader.close();
			scFile.close();
			FileWriter writer = new FileWriter(openFile);
			writer.write(enterScore);
			writer.flush();
			writer.close();
			return "成功";
		}
		catch(FileNotFoundException e) {
			generateCourseGrade(semester, subjectID, subjectName);
			String openFile = semester + subjectID + subjectName + ".txt";//各個科目的成績檔案
			String enterScore = "";
			FileReader reader = new FileReader(openFile);
			Scanner scFile = new Scanner(reader);
			while(scFile.hasNext()) {
				String[] studentList = scFile.nextLine().split(" ");
				if(studentName.equals(studentList[1])) {
					studentList[2] = Integer.toString(studentScore);
				}
				enterScore += studentList[0] + " " + studentList[1] + " " + studentList[2] + "\n";
			}
			reader.close();
			scFile.close();
			FileWriter writer = new FileWriter(openFile);
			writer.write(enterScore);
			writer.flush();
			writer.close();
			return "成功";
		}
		
	}
	
	private String searchName(String ID) throws IOException {
		FileReader reader = new FileReader("account.txt");
		Scanner scFile = new Scanner(reader);
		while(scFile.hasNext()) {
			String[] data = scFile.nextLine().split(" ");
			if(ID.equals(data[0])) {
				scFile.close();
				return data[2];
			}
		}
		scFile.close();
		return "無效輸入";
	}
	
}
