import com.as400samplecode.DBUtils;
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

        List<String> itemsStrings =  DBUtils.getAllItems();

        for(String item : itemsStrings){
            System.out.println(item);
        }


    }


    @Test
    public void testGetSingelItem() throws Exception {
        String requestItem="blaba";



    }


}
