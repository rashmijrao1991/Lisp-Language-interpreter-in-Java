import java.util.*;
import java.util.regex.Pattern;
public class Parser {

	String[] tokens=null;
	static int curidx=0;
	static List<SExp> symbolics=null;

	public Parser(String expn) throws Exception {
		String texpn="";
		this.tokens=expn.split(" ");
		this.curidx=0;
		this.symbolics=new ArrayList<SExp>();
		//need to add more symbolics
		List stdsym=new ArrayList();
		stdsym.addAll(Arrays.asList("NIL","CAR","CDR","PLUS","CONS","COND","QUOTE","EQ","ATOM","NULL","MINUS"));
		createSymObjects(stdsym);
		//System.out.println("Came into parser and created objects"+this.tokens[2]);
	}
	private void createSymObjects(List<String> stdsym) {
		for(String sym: stdsym){
			SExp temp=new SExp();
			temp.type=4;
			temp.name=sym;
			this.symbolics.add(temp);
		}
		
	}
	/**
	 * 	'(' :1
	 * 	')' :2
	 * 	'.':3
	 * 	'integral':5
	 * 	'symbolic':4
	 * 	
	 * @return
	 */
	public boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	    	//need to allow numeric along with alphabets
	        if(Character.isLetter(c)) {
	            return true;
	        }
	        else{
	        	return false;
	        }
	    }

	    return false;
	}
	public int ckNextToken(){
		//System.out.println(this.tokens[this.curidx].matches(".*\\d+.*"));
		//System.out.println(this.tokens[this.curidx]);
		if(this.curidx<this.tokens.length && this.curidx>=0){
			//need to check for + and -			 
			if(isAlpha(this.tokens[this.curidx])){
				return 4;
			}
			
			else if(this.tokens[this.curidx].charAt(0)=='('){
				return 1;				
			}
			else if(this.tokens[this.curidx].charAt(0)==')'){
				return 2;				
			}
			else if(this.tokens[this.curidx].charAt(0)=='.'){
				return 3;
			}
			else if (this.tokens[this.curidx].matches(".*\\d+.*")) {
			    // its an integer
				return(5);
			}
		}
		return -1;
	}
	private String getNextToken() {
		//System.out.println(this.tokens[this.curidx]);
		return(this.tokens[this.curidx]);
	}
	public void skipToken(){
		this.curidx=this.curidx+1;
	}
	
	
	public SExp getId(int type){
		SExp newatm=new SExp();
		if(type==4){ //type 4 is for symbolic and type 5 is for integer atoms
		for(SExp atoms:this.symbolics){
			if(atoms.name.equals(getNextToken())){
				
				return atoms;
			}
			
		}
		newatm.name=getNextToken();
		newatm.type=4;
		this.symbolics.add(newatm);
		}
		
		else if(type==5){
			if(getNextToken().charAt(0)=='+'){
				
				newatm.val=Integer.parseInt(getNextToken().substring(1));
			}
			else if(getNextToken().charAt(0)=='-'){
				newatm.val=-Integer.parseInt(getNextToken().substring(1));
			}
			else{
				newatm.val=Integer.parseInt(getNextToken());
			}
			newatm.type=5;			
			
		}
		//System.out.println(newatm.name);
		return newatm;
	}
	
	public SExp input() throws Exception {
		//System.out.println(ckNextToken());
		if(ckNextToken()==4||ckNextToken()==5){ //<atom>
			return getId(ckNextToken());
		}
		if(ckNextToken()==1){ // (
			skipToken();
			//System.out.println("At idx:"+this.curidx);
			if(ckNextToken()==2){ //() case 
				return input2();
			}
			else if(ckNextToken()==3){ // (<s-exp>
				throw new Exception("Misplaced . expression");
			}
			

			SExp left=input();
			skipToken();
			if(ckNextToken()==3){//(<s-exp>.
				//Stack<SExp> st=new Stack<SExp>();
				//st.push(left);
				skipToken();
				SExp right=input();
				skipToken();
				if(ckNextToken()!=2)
					throw new Exception("Unexpected "+getNextToken());
				return(conshelper(left, right));
								
			}
			else if(ckNextToken()==2){
				return(conshelper(left,input2()));
			}
			else return conshelper(left, input2()); //<s-exp> <rest>
		}
		else{
			throw new Exception("Unexpected symbol"+ckNextToken());
		}
		
	}
	
	public SExp input2() throws Exception {
		if(ckNextToken()==2){//)
			SExp nil=new SExp();
			nil.name="NIL";
			nil.type=4;
			//System.out.println("returning correctly");
			return nil;
		}
		SExp l=input();
		skipToken();
		SExp r=input2();
	    return conshelper(l,r);
		
	}
	public SExp conshelper(SExp left,SExp right){
		if(left!=null && left.name=="NIL"){
			//System.out.println("There's NIL somewhere in left");
		}
		if(right!=null && right.name=="NIL"){
			//System.out.println("There's NIL somewhere in right"+left.val);
		}
		SExp top=new SExp();
		top.left=left;
		top.right=right;
		return top;
	}

}
