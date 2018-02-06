/**
* Trash.java
*
* This class is responsible for storing information about a single piece
* of Tim Hortons trash.
*/

public class Trash {
	private String imageFile;
	private String category;

	public Trash (String imageFile, String category) {
		this.imageFile = imageFile;
		this.category = category;
	}

	public String getImageFile () {
		return imageFile;
	}

	public String getCategory () {
		return category;
	}

	public void setImageFile (String file) {
		imageFile = file;
	}

	public void setCategory (String category) {
		this.category = category;
	}
}