import com.as400samplecode.DBUtils;
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
        DBUtils.insertIntoItems(requestItem);


    }


    @Test
    public void testGetAllItems() throws Exception {

        List<Item> itemsStrings =  DBUtils.getAllItems();

        for(Item item : itemsStrings){
            System.out.println(item.getDate());
            System.out.println(item.getStore());
            System.out.println(item.getTransaction_amount());
            System.out.println(item.getNumber_of_payments());
        }


    }


    @Test
    public void testGetSingelItem() throws Exception {
        String requestItem="blaba";



    }


}
