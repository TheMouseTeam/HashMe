package com.barmpas.hashme;

import android.content.Context;

import java.util.Arrays;
import java.util.Random;

import static com.barmpas.hashme.EncryptionActivity.encryption_key;

/**
 * Hypster is the Hypster Algorithm that performs the encryption and decryption of the text.
 * @author Konstantinos Barmpas
 */
public class HypsterClass {

    private  int balance,clock,clock1,change_position,counter,key1,key2,key3,main_key,constant,given_code,user_code,rotation,decimal,hypster_array_index;

    private char id_code;

    private Random r;

    private int[] hypster_array;

    private  String print,final_text_encoded;

    private Context context;

    private String text,text_english,greek_text,int_string;

    //removes hastags from the main text.
    public String Validity_Of_Character_Checker(String user_text)
    {
        char[] chars = user_text.toCharArray();
        for (int i=0; i<chars.length; i++){
            if (chars[i]=='#'){
                chars[i]=' ';
            }else if ((int)chars[i]<32){
                chars[i]=' ';
            }else if ((int)chars[i]>126 && (int)chars[i]<902){
                chars[i]=' ';
            }else if ((int) chars[i]>974){
                chars[i]=' ';
            }
        }
        user_text = String.valueOf(chars);
        return user_text;
    }

    public void split_Text(String user_text) {
        text_english="g";
        greek_text="g";
        char[] chars = user_text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((int)chars[i]>=902) {
                hypster_array[hypster_array_index]=1;
                hypster_array_index++;
                greek_text=greek_text+(char)((int)chars[i]-848);
            }else{
                if ((int)chars[i]>=32 && (int)chars[i]<=126) {
                    hypster_array[hypster_array_index]=0;
                    hypster_array_index++;
                    text_english=text_english+(char)((int)chars[i]);
                }
            }
        }
        if (greek_text!="g"){
            greek_text = greek_text.substring(1);
        }
        if (text_english!="g"){
            text_english = text_english.substring(1);
        }
    }

    //There is a possibility that all of clock,main_key and constant are zero. Then there is no encryption so here works our Encode_checker function
    //which turns our clock to 1 then.
    public void Checker (){
        if ((clock==0 && constant==0) && (main_key==0)){
            clock=1;
            clock1=clock;
        }
    }

    //Encode is our main function for encoding process.
    public String Encode(String user_text)
    {
        clock= (r.nextInt(2-0)+0);
        clock1=clock;
        balance=0;
        change_position=0;
        char[] chars = user_text.toCharArray();
        Encode_Keys(user_text);
        Encode_Main_Key();
        Encode_Constant();
        balance=0;
        change_position=0;
        Checker();
        for (int i=0; i<chars.length; i++){
            if (change_position==0){
                if (i==0){
                    if (chars[i]!=126){ //
                        chars[0]=(char)((int)chars[0]+1);
                    }
                }else{ // Simple
                    if (chars[i]>=32 && chars[i]<=38){
                        chars[i]= (char) Omar_Simple_Algorithm(chars[i]);
                    }else{// No overflow.
                        balance=Encode_Balance_Finder(chars[i],i);
                        chars[i]= (char) (chars[i]+clock+main_key+constant+balance);
                    }
                }
            }else{ //After Overflow.
                chars[i]=(char)Omar_Algorithm(chars[i]);
            }
        }
        Encode_Given_Code();
        Encode_ID_Given113();
        user_text= String.valueOf(chars);
        Encode_Printing(user_text);
        return final_text_encoded;
    }

    //Encode_Balance_Finder finds our balance variable. Basically check for ASCII printable characters overflow.
    //If there is an overflow it takes it back to 125ASCII our encrypted (clock=0 for this action), keep the change_position,
    //and new encoding method starts. If there is not an overflow the balance is 0.
    public int Encode_Balance_Finder(char c, int i)
    {
        int balance,balance_decider;
        balance_decider=main_key+constant+c;
        if (balance_decider==128){
            balance=-3;
            change_position=i;
            clock1=clock;
            clock=0;
        }else if(balance_decider==127){
            balance=-2;
            change_position=i;
            clock1=clock;
            clock=0;
        }else if(balance_decider==126){
            balance=-1;
            change_position=i;
            clock1=clock;
            clock=0;
        }else{
            balance=0;
        }
        return balance;
    }

    public int Omar_Algorithm(int c) //changes all characters in root 79-Omar Table except 'S'.
    {
        if (c<79){
            c=c+48;
        }else{
            if(c>79){
                if (c!=83){
                    c=c-48;}
            }
        }
        return c;
    }

    public int Omar_Simple_Algorithm(int c) //only for characters [32,38] Omar-table root 35.
    {
        if (c<35){
            c=c+4;
        }else{
            if (c>35){
                c=c-4;
            }
        }
        return c;
    }

    public int Omar_Rotation(int c) //rotates the Omar-Table.
    {
        int middle;
        middle=79-rotation;
        if (c<middle){
            c=c+48;
        }else{
            if (c>middle){
                if (c>126-rotation){
                    c=c-47;
                }else if (c>=80){
                    if (c!=83 && c!=80){
                        c=c-48;
                    }
                }else{
                    c=c+47;
                }
            }
        }
        return c;
    }



    //Encode_Keys creates the keys1-3. key1 with our first character.
    //Creates probability a number between 0 and 100 which we use to create key3. and we uses the
    //size of our text to create key2 depending if its odd or even.
    public void Encode_Keys(String user_text)
    {
        char[] chars = user_text.toCharArray();
        key1=chars[0];
        int probability=key1%100;
        if (probability<33){
            key3=probability+33;}
        else{
            key3=probability;
        }
        counter=chars.length;
        if (counter%2==0 || counter==1){
            key2=chars[counter/2];
        }else{
            key2=chars[counter/2+1];
        }
    }

    //Encode_Main_Key function creates our main_key which is 1 or 0. O only in case key2>key3 and
    //(key2-key3)%100<50.
    public void Encode_Main_Key()
    {
        main_key=1;
        if (key2>key3){
            if ((key2-key3)%100<50){
                main_key=0;
            }
        }
    }

    //Encode_Constant function creates the constant variable which is 1 or 0. Uses key1's (meaning our first character's) ASCII Table position.
    //If it is in region 1 then constant 0, region 2 is 1 and region 3 is key1%2.
    public void Encode_Constant()
    {
        if (key1>=33 && key1<=63){
            constant=0;
        }else if (key1>=64 && key1<=95){
            constant = 1;
        }else{
            constant=key1%2;
        }
    }

    //Encode_Given_Code asks the user to give their password [3,10] chars. It creates id_code and user_code. and uses this as well
    //as the change_position to create the given_code variable that contains both of them and is implemented to the encrypted text.
    //It also adds space if the pass<10 and then random chars until 10 length.
    public void Encode_Given_Code()
    {
        String password = encryption_key;
        char[] chars = password.toCharArray();
        id_code=chars[0];
        user_code=chars[1]%10;
        password=password.substring(1);
        int length=password.length();
        int i=1;
        chars=password.toCharArray();
        final_text_encoded= String.valueOf(chars[0]);
        while(i<=length-1){
            final_text_encoded=final_text_encoded+chars[i];
            i++;
        }
        if (i!=9){
            final_text_encoded=final_text_encoded+' ';
            int j=i+1;
            for (i = j; i<9; i++){
                final_text_encoded=final_text_encoded+(char)(r.nextInt(60-50)+50);
            }
        }
        chars = final_text_encoded.toCharArray();
        for (i=0; i<chars.length; i++){
            chars[i]= (char) Omar_Algorithm(chars[i]);
        }
        final_text_encoded = String.valueOf(chars);
        given_code=change_position*10;
        given_code=given_code+user_code;
        given_code=given_code+113;
    }

    //Encode_ID_Given113 function creates the rotation used in Omar's algebra.
    //Then it encodes it. and creates the decimal which shows decimal which shows the highest power of 10 that has our encrypted given_code.
    //The printing process starts here.
    public void Encode_ID_Given113()
    {
        id_code=(char)((int)(id_code)+1);
        rotation=user_code+id_code%10;
        balance=Omar_Rotation(balance+39);
        id_code= (char) Omar_Rotation((int)id_code);
        int s=1;
        int i=0;
        while (given_code/s!=0){
            i++;
            s=1;
            for (int j=1; j<=i; j++){
                s=s*10;
            }
        }
        decimal=i-1;
        final_text_encoded=(char)(decimal+36)+final_text_encoded+'#'+(char)(rotation+36)+(char)(id_code)+(char)(balance);
    }

    //Encode_Printing. It continues the enryption. The printing continues here.
    public void Encode_Printing(String user_text)
    {
        int i=decimal;
        int t=i;
        for(int k=t; k>=0; k--){
            int s=1;
            for (int j=1; j<=i; j++){
                s=s*10;
            }
            int digit=given_code/s+36;
            digit=Omar_Rotation(digit);
            final_text_encoded=final_text_encoded+(char)digit;
            given_code=given_code%s;
            i--;
        }
        final_text_encoded=final_text_encoded+'#';
        clock1=Omar_Rotation(clock1+33);
        final_text_encoded=final_text_encoded+(char)clock1;
        main_key=Omar_Rotation(main_key+33);
        final_text_encoded=final_text_encoded+(char)main_key;
        constant=Omar_Rotation(constant+36);
        final_text_encoded=final_text_encoded+(char)constant;

        char[] chars = user_text.toCharArray();

        for (i=0; i<chars.length; i++){
            chars[i]= (char) Omar_Rotation(chars[i]);
        }
        user_text = String.valueOf(chars);

        final_text_encoded=final_text_encoded+user_text;
    }

    public String FromArrayToString(){
        String hypster_string="#";
        for (int i=0; i<hypster_array.length; i++){
            hypster_string=hypster_string+hypster_array[i]+"#";
        }
        hypster_string = hypster_string.substring(1);
        return hypster_string;
    }

    public Boolean Password_Checker(String p) //This function checks that our password doesn't contain spaces.
    {
        Boolean valid=true;
        char[] chars = p.toCharArray();
        for (int i=0; i<chars.length; i++){
            if (chars[i]==' '){
                valid=false;
                break;
            }
        }
        return valid;
    }

    public String Decode_Buffer(String temporal)
    {
        char c;
        int j=0;
        int k;
        Boolean done1=true;
        Boolean done2=false;
        Boolean done3=false;
        Boolean done4=false;
        given_code=0;
        char[] chars1 = temporal.toCharArray();
        char[] chars = Arrays.copyOf(chars1, chars1.length + 1);
        chars[chars.length - 1] = '#';
        String user_text="#";
        print="#";
        c=chars[0];
        while (c!='#'){ //termination check.
            decimal=(int)c-36;
            if (done1==true){ //checks first area of the text.
                k=0;
                j=1;
                c=chars[j];
                while (c!='#'){
                    k++;
                    print=print+(char)Omar_Algorithm(c);
                    j++;
                    c=chars[j];
                }
                if (k!=9){ //Hypster check.
                    Hypster();
                    done1=false;
                    break;
                }else{
                    done2=true;
                    done1=false;
                    j++;
                    c=chars[j];
                    print=print.substring(1);
                }
            }
            if (done2==true && c!='#'){ //checks second area of the text.
                int l=1;
                rotation=(int)c-36;
                if (rotation<0 || rotation>19){ //Hypster check.
                    Hypster();
                    done2=false;
                    break;
                }
                j++;
                c=chars[j];
                while (c!='#' && c!=' ' && done2==true){
                    if (l==1){
                        id_code=(char)Omar_Rotation(chars[j]);
                        id_code=(char)(id_code-1);
                        if (id_code=='#'){ //Omar's algorithm check.
                            id_code='S';
                        }
                    }else if (l==2){
                        balance=Omar_Rotation(chars[j]);
                        balance=balance-39;
                        if (balance<-3 || balance>0){ //Hypster check.
                            Hypster();
                            done2=false;
                            break;
                        }
                    }else{
                        int s=1;
                        //Finds the k power of 10 with this for.
                        for (k=decimal; k>0; k--)
                            s=s*10;
                        char digit=(char)Omar_Rotation(c);
                        digit= (char) ((digit)-36);
                        if ((int)(digit)<0 || (int)(digit)>=10){//Hypster check.
                            Hypster();
                            done2=false;
                            break;
                        }
                        given_code=given_code+s*digit;
                        decimal--;
                    }
                    j++;
                    l++;
                    c=chars[j];
                    if (decimal==-1){ //Hypster check.
                        if (c=='#'){
                            done3=true;
                            done2=false;
                            if (j+1<chars.length){
                                j++;
                                c=chars[j];}
                        }else{
                            Hypster();
                            done2=false;
                        }
                    }
                }
                if (c=='#' && done2==true && done3==false){ //Hypster check.
                    Hypster();
                    done2=false;
                    break;
                }
            }
            if (done3==true && c!='#'){ //checks the third area of text.
                k=1;
                while (c!='#' && c!=' '){
                    if (k==1){
                        clock=Omar_Rotation(c)-33;
                        if (clock<0 || clock>1){ //Hypster check.
                            Hypster();
                            done3=false;
                            break;
                        }
                    }else if (k==2){
                        main_key=Omar_Rotation(c)-33;
                        if (main_key<0 || main_key>1){ //Hypster check.
                            Hypster();
                            done3=false;
                            break;
                        }
                    }else if (k==3){
                        constant=Omar_Rotation(c)-36;
                        if (constant<0 || constant>1){ //Hypster check.
                            Hypster();
                            done3=false;
                            break;
                        }
                    }else{
                        done4=true; //creates the text for encryption in the forth area of text.
                        user_text=user_text+(char)(Omar_Rotation(c));
                    }
                    j++;
                    k++;
                    c=chars[j];
                    if (c=='#'){
                        if (done4==false){ //Hypster check.
                            Hypster();
                            done3=false;
                            break;
                        }
                    }
                }
            }
        }
        user_text=user_text.substring(1);
        return user_text;
    }

    public void Hypster(){
        //Hypster function prevents system failing
    }

    public void Decode_Given_Code()
    {
        given_code=given_code-113;
        user_code=given_code%10;
        given_code=given_code-user_code;
        change_position=given_code/10;
    }

    public String Decode(String user_text, String key)
    {
        Decode_Given_Code();
        String printable2;
        String password="#";
        printable2=(char)id_code+print; //creates password.
        char[] print1 = printable2.toCharArray();

        int i=0;
        while (i<print1.length){
            if (print1[i]==' '){
                break;
            }else{
                password=password+print1[i];
            }
            i++;
        }
        password=password.substring(1);

        String pass = key;

        if (pass.length()<3 || pass.length()>10 || Password_Checker(pass)!=true){
            pass="123";
        }

        char[] chars = pass.toCharArray();
        for (i=0; i<chars.length; i++){
            if (chars[i]=='#') chars[i]='S';
        }
        pass= String.valueOf(chars);
        chars = user_text.toCharArray();

        if (pass.equals(password)){
            Boolean switcher=true;
            for (i=0; i<chars.length; i++){
                if (i==0){
                    if (chars[0]!=126){
                        chars[0]=(char)(chars[0]-1);}
                }else{
                    if (switcher==true){
                        if (i==change_position){
                            chars[i]= (char) (chars[i]-main_key-constant-balance);
                            switcher=false;
                        }else{
                            if (chars[i]>=32 && chars[i]<=38){
                                chars[i]= (char) Omar_Simple_Algorithm(chars[i]);}else{
                                chars[i]= (char) (chars[i]-main_key-constant-clock);}}
                    }else{
                        chars[i]= (char) Omar_Algorithm(chars[i]);
                    }
                }
            }
            user_text= String.valueOf(chars);
            if (user_text.isEmpty()==false){
                return user_text;
            }else{ //all good but no text by mistake.
                user_text=("Sorry wrong password");
            }
        }else{
            user_text=("Sorry wrong password");
        }
        return user_text;
    }

    public String DecodeStart(String key){
        text_english=Decode_Buffer(text_english);

        text_english=Decode(text_english,key);

        greek_text=Decode_Buffer(greek_text);

        greek_text=Decode(greek_text,key);

        char[] english_chars=text_english.toCharArray();
        char[] greek_chars=greek_text.toCharArray();

        String[] int_parts = int_string.split("#");

        char[] chars = new char[int_parts.length];

        int greek_index=0;
        int english_index=0;
        String final_decoded_text;
        if (text_english.equals("Sorry wrong password") && greek_text.equals("Sorry wrong password")) {
            final_decoded_text = context.getResources().getString(R.string.type_key);
        }else {
            for (int i = 0; i < int_parts.length; i++) {
                if (Integer.parseInt(int_parts[i]) == 1) {
                    chars[i] = (char) ((int) greek_chars[greek_index] + 848);
                    greek_index++;
                } else {
                    chars[i] = english_chars[english_index];
                    english_index++;
                }
            }
            final_decoded_text = String.valueOf(chars);
        }
        return final_decoded_text;
    }

    public void EncodeStart(){
        r=new Random();
        text= Validity_Of_Character_Checker(text);
        hypster_array_index=0;
        hypster_array= new int[text.length()];
        split_Text(text);
        int_string = FromArrayToString();
        int_string = int_string.substring(0, int_string.length() - 1);

        if (!text_english.equals("g")){
            text_english=Encode(text_english);
        }

        if (!greek_text.equals("g")) {
            greek_text = Encode(greek_text);
        }
    }

    public String getEnglish(){
        return text_english;
    }

    public String getGreek(){
        return greek_text;
    }

    public String getIntString(){
        return int_string;
    }

    public HypsterClass(String text,String text_english,String greek_text,String int_string, Context context){
        this.text = text;
        this.text_english = text_english;
        this.greek_text = greek_text;
        this.int_string = int_string;
        this.context = context;
    }

}