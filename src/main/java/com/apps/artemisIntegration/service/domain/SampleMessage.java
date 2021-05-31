package	com.apps.artemisIntegration.service.domain;

public class SampleMessage {
    public String   firstName;
    public String   lastName;

    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("firstName: ")
    	  .append(firstName)
    	  .append(", lastName: ")
    	  .append(lastName)
    	  ;
    	
    	return sb.toString();
    }
}