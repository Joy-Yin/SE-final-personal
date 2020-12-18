
import java.io.IOException;

public class client {//測試功能用

	public static void main(String[] args) throws IOException{
		score a = new score();
		//a.generateCourseGrade("1071", "000001", "課程1");
		//a.updateScore("1071", "000001", "課程1", "學生1", 100);
		//a.generatePersonalGrade("1071", "410677001");
		//a.checkGrade("1071", "410677001");
		a.printGrade("410677001");

	}

}
