package items;

public enum ItemID {


	NOTHING(0),INVISIBLECLOAK(1),MINIPLANE(2), CLOUD(3), TMP(4);
	private ItemID(int id){
	}

	public String toString(ItemID id){
		if(id == INVISIBLECLOAK){
			return "Invisible Cloak";
		}
		else if(id == MINIPLANE){
			return "Shrink Machine";
		}
		else if(id == CLOUD){
			return "Cloud";
		}
		else if(id == TMP) {
			return "TMP";
		}
		else 
			return "";
	}
}
