package translator.com.domain;

import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserDict {
	@PrimaryKey
	private String userSecret;
	
	@Persistent
	private Set<String> words;
	
	public UserDict(String userSecret, Set<String> words) {
		this.userSecret = userSecret;
		this.words = words;
	}

	public String getUserSecret() {
		return userSecret;
	}

	public void setUserSecret(String userSecret) {
		this.userSecret = userSecret;
	}

	public Set<String> getWords() {
		return words;
	}

	public void setWords(Set<String> words) {
		this.words = words;
	}
}