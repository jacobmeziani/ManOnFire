package importing;

import java.util.ArrayList;
import java.util.List;
import importing.FullReader;


public class TestStub {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String listname = "test string motherfuckers";
		List<String> att = new ArrayList<String> ();
		att.add("attribute1");
		att.add("attribute2");
		String creator = "Tyler the creator";
		int returnbro = FullReader.insertContributorList(listname,att,creator);
		System.out.println("returnbro " + returnbro);

	}

}
