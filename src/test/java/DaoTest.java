//import com.as400samplecode.dbUtils;
import com.as400samplecode.Item;
import org.junit.Test;

import java.util.List;

/**
 * Created by Lihi.Martin on 1/12/2017.
 */
public class DaoTest {


    @Test
    public void testInsert() throws Exception {
        String requestItem="blaba";
      //  dbUtils.insertIntoItems(requestItem);


    }


    @Test
    public void testGetAllItems() throws Exception {

  //      List<Item> itemsStrings =  dbUtils.getAllItems();

/*        for(Item item : itemsStrings){
            System.out.println(item.getPurchase_date());
            System.out.println(item.getStore());
            System.out.println(item.getTransaction_amount());
            System.out.println(item.getNumber_of_payments());
        }*/


    }


    @Test
    public void testGetSingelItem() throws Exception {
        String requestItem="blaba";



    }


}
