package Data;

public class ListServerInit {
	private int list_id;
	private String list_name;
	
	ListServerInit(int id,String name) {
		this.list_id = id;
		this.list_name = name;
	}
	public int getID () {
		return list_id;
	}
	
	public String getName () {
		return list_name;
	}
}
