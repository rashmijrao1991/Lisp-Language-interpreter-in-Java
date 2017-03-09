
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Tokenizer {
public static void main(String[] args) throws Exception{
    String input = null;
    int i1=0;
    String sb="";
   
	while(true)
    {
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));		
		String name = reader1.readLine(); 
		for(int c = 0;c<name.length();c++){
			if(name.charAt(c)=='$' && c==0){
				tokenize(sb);
				sb="";
				if( c+1<name.length() && name.charAt(c+1)=='$'){
					System.out.println("Bye");
					System.exit(0);

				}
			}
			else
			sb=sb+name.charAt(c);
		}
				
    }
      
}
private static void tokenize(String sb) throws Exception{
	
    List<String> allexps=new ArrayList<String>();
    String currsexp="";
    int eof=0;
    for (int i=0;i<sb.length();i++){
        
    	if( sb.charAt(i)=='\n' || sb.charAt(i)=='\0' || sb.charAt(i)=='\t' || sb.charAt(i)==' ')
    	{
    		if(i>0 && currsexp.charAt(currsexp.length()-1)!=' ')
    			currsexp=currsexp+' ';
    	}  	
    	else{
    		currsexp=currsexp+sb.charAt(i);
    	}
    	
    }
    String expn="";
    for(int j=0;j<currsexp.length();j++){
    	if(currsexp.charAt(j)=='('){
    		if(j>0 && expn.charAt(expn.length()-1)!=' ')
    			expn=expn+' ';
    		expn=expn+'('+' ';
    	}
    	else if(currsexp.charAt(j)==')')
    	{
    		if(j>0 && expn.charAt(expn.length()-1)!=' ')
    			expn=expn+' '+')';
    		else
    			expn=expn+')';
    		
    	}
    	else if(currsexp.charAt(j)=='.'){
    		if(j>0 && expn.charAt(expn.length()-1)!=' ')
    			expn=expn+' '+'.'+' ';    		
    		else{
    			expn=expn+'.';
    		}
    	}
    	else{
    		if(j>0 && !(currsexp.charAt(j)==' ' && expn.charAt(expn.length()-1)==' '))
    		expn=expn+currsexp.charAt(j);
    	}
    }
    allexps.add(expn);
    for(String expn1 : allexps){
    	Parser p=new Parser(expn1);
    	SExp converted=p.input();
    	System.out.print(">> ");
    	Printout(converted);
    	//System.out.println(converted.left.right);
  
    }
}
private static void Printout(SExp converted) {
	// TODO Auto-generated method stub
	if(converted==null){
		
		return;
	}
	//System.out.println(converted.type);
	if(converted.left==null && converted.right==null){
		if(converted.type==4 && converted.name!="NIL")
		System.out.print(converted.name);
		else if(converted.type==4)
			System.out.print(converted.name);
		else if(converted.type==5)
			System.out.print(converted.val);
	}
	if(converted.type==0)
		System.out.print("(");
	Printout(converted.left);
	if(converted.type==0)
		System.out.print(".");
	Printout(converted.right);
	
	if(converted.type==0)
		System.out.print(")");
	
}



}
 
