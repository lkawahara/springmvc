package kawahara.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="USERS")
@SequenceGenerator(name="seq", initialValue=0, allocationSize=1000)
public class UserModel {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private long id;
	private String username;
	private int contentLength;
	private byte[] imgBytes;
	
	public UserModel(){
		contentLength = 0;
		imgBytes = new byte[0];
	}

	public UserModel(String name, InputStream is){
		this.setUsername(name);
		try {
		    byte[] b = new byte[1024];
		    int read = -1;
		    ByteArrayOutputStream os = new ByteArrayOutputStream();
		    while ( ( read = is.read(b) ) != -1 ) {
		    	os.write(b, 0, read);
		    }
		    imgBytes = os.toByteArray();
		    contentLength = imgBytes.length;
		} catch ( IOException e ) {
			
		} 
	}
	
	
	public long getId() {
		return this.id;
	}
	public byte[] getImgBytes(){
		return imgBytes;
	}
	public int getImgLength() {
		return contentLength;
	}
	public String getUsername(){
		return this.username;
	}
	
	
	public void setUsername(String username){
		this.username = username;
	}
	
	
	public void copyData(UserModel updateUser) {
		this.contentLength = updateUser.getImgLength();
		this.imgBytes = updateUser.getImgBytes();
		this.username = updateUser.getUsername();
	}
	
}