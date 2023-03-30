/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author ADMIN
 */
public class thuattoan {
    public static void main(String[] args) {
        String x = "dinhvanhuy###day la dinh van duy";
        int so = x.indexOf("###");
        System.out.println(so);
        System.out.println(x.substring(0, 10));
        System.out.println(x.substring(13, x.length()));
    }
}
