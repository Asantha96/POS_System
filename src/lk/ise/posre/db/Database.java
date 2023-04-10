package lk.ise.posre.db;

import lk.ise.posre.entity.Customer;
import lk.ise.posre.entity.Item;
import lk.ise.posre.entity.Order;
import lk.ise.posre.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;




public class Database {
    public int getQtyP001() {
        return qtyP001;
    }

    public void setQtyP001(int qtyP001) {
        this.qtyP001 = qtyP001;
    }

    private int qtyP001 = 87;

    public static ArrayList<User> users = new ArrayList();
    public static ArrayList<Customer> customers = new ArrayList();
    public static ArrayList<Item> items = new ArrayList();
    public static ArrayList<Order> orders = new ArrayList();

    static {
        //users in the system



        //customers in the system





        //items in the system

        /*items.add(new Item("P001","Samba 5 kg",1800,87));
        items.add(new Item("P002","Sunlight soap",175,73));
        items.add(new Item("P003","Signal toothpaste",185,35));*/




    }



}
