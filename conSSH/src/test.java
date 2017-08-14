/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author x0270254
 */
public class test {
    public static void main(String[] args){
    int array[][]=new int[10][10]; 
for(int x=0;x<10;x++){
for(int i=0;i<10;i++){
array[x][i]=(x+1)*(i+1);
}
}
for(int x=0;x<10;x++){
for(int i=0;i<10;i++){
System.out.print(array[x][i]);
if(i==9){
    System.out.println("");
}
}
}


}
}
